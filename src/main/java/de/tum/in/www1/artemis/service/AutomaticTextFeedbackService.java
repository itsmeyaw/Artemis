package de.tum.in.www1.artemis.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.*;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tum.in.www1.artemis.domain.*;
import de.tum.in.www1.artemis.domain.enumeration.FeedbackType;
import de.tum.in.www1.artemis.repository.FeedbackRepository;
import de.tum.in.www1.artemis.repository.TextBlockRepository;

@Service
@Profile("athene")
public class AutomaticTextFeedbackService {

    private final FeedbackRepository feedbackRepository;

    private static final double DISTANCE_THRESHOLD = 1;

    private final TextBlockRepository textBlockRepository;

    public AutomaticTextFeedbackService(FeedbackRepository feedbackRepository, TextBlockRepository textBlockRepository) {
        this.feedbackRepository = feedbackRepository;
        this.textBlockRepository = textBlockRepository;
    }

    /**
     * Suggest Feedback for a Submission based on its cluster.
     * For each TextBlock of the submission, this method finds already existing Feedback elements in the same cluster and chooses the one with the minimum distance.
     * Otherwise, an empty Feedback Element is created for simplicity.
     * Feedbacks are stored inline with the provided Result object.
     *
     * @param result Result for the Submission
     */
    @Transactional(readOnly = true)
    public void suggestFeedback(@NotNull Result result) {
        final TextSubmission textSubmission = (TextSubmission) result.getSubmission();
        final var blocks = textBlockRepository.findAllWithEagerClusterBySubmissionId(textSubmission.getId());
        textSubmission.setBlocks(blocks);

        final List<Feedback> suggestedFeedback = blocks.stream().map(block -> {
            final TextCluster cluster = block.getCluster();

            // if TextBlock is part of a cluster, we try to find an existing Feedback Element
            if (cluster != null) {
                // Find all Feedbacks for other Blocks in Cluster.
                final List<TextBlock> allBlocksInCluster = cluster.getBlocks().parallelStream().filter(elem -> !elem.equals(block)).collect(toList());
                final Map<String, Feedback> feedbackForTextExerciseInCluster = feedbackRepository.getFeedbackForTextExerciseInCluster(cluster);

                if (feedbackForTextExerciseInCluster.size() != 0) {
                    final Optional<TextBlock> mostSimilarBlockInClusterWithFeedback = allBlocksInCluster.parallelStream()

                            // Filter all other blocks in the cluster for those with Feedback
                            .filter(element -> feedbackForTextExerciseInCluster.containsKey(element.getId()))

                            // Find the closest block
                            .min(comparing(element -> cluster.distanceBetweenBlocks(block, element)));

                    if (mostSimilarBlockInClusterWithFeedback.isPresent()
                            && cluster.distanceBetweenBlocks(block, mostSimilarBlockInClusterWithFeedback.get()) < DISTANCE_THRESHOLD) {
                        final Feedback similarFeedback = feedbackForTextExerciseInCluster.get(mostSimilarBlockInClusterWithFeedback.get().getId());
                        return new Feedback().reference(block.getId()).credits(similarFeedback.getCredits()).detailText(similarFeedback.getDetailText())
                                .type(FeedbackType.AUTOMATIC);
                    }
                }
            }

            return null;
        }).filter(Objects::nonNull).collect(toList());

        result.setFeedbacks(suggestedFeedback);
    }

    /**
     * Sets number of potential automatic Feedback's for each block belonging to the `Result`'s submission.
     * This number determines how many other submissions would be affected if the user were to submit a certain block feedback.
     * For each TextBlock of the submission, this method finds if it is referred to as minimum-distance-block by any other blocks.
     * The number of times it is referred to by other blocks as minimum is counted and saved onto the blocks
     * `numberOfAffectedSubmissions` field.
     * The updated
     *
     * @param result Result for the Submission acting as a reference for the text submission to be searched.
     */
    @Transactional()
    public void setNumberOfPotentialFeedbacks(@NotNull Result result) {
        final TextSubmission textSubmission = (TextSubmission) result.getSubmission();
        final var blocks = textBlockRepository.findAllWithEagerClusterBySubmissionId(textSubmission.getId());
        textSubmission.setBlocks(blocks);

        // iterate over blocks of the referenced submission
        blocks.forEach(block -> {
            // If affected submissions number is already calculated then skip
            if (block.getNumberOfAffectedSubmissions() > 0) {
                return;
            }
            final TextCluster cluster = block.getCluster();
            // if TextBlock is part of a cluster, we find how many other submissions of that cluster it will affect
            if (cluster != null) {
                // Find all blocks in the defined cluster
                final List<TextBlock> allBlocksInCluster = cluster.getBlocks().parallelStream().collect(toList());
                // We filter out the 'block' itself to avoid self-comparison
                var allClusterBlocksToCheck = allBlocksInCluster.parallelStream().filter(elem -> !elem.equals(block)).toList();

                int numberOfAffectedSubmissions = 0;
                // Iterate over the other cluster blocks - excluding the main 'block'
                for (TextBlock clusterBlockRef : allClusterBlocksToCheck) {
                    // For current cluster block, find which is it's minimal block - the block with the minimum cluster distance
                    final Optional<TextBlock> minimalBlock = allBlocksInCluster.parallelStream().filter(elem -> !elem.equals(clusterBlockRef))
                            .min(comparing(element -> cluster.distanceBetweenBlocks(element, clusterBlockRef)));

                    if (minimalBlock.isPresent()) {
                        final double distanceWithMainBlock = cluster.distanceBetweenBlocks(minimalBlock.get(), block);
                        final double distanceWithRefBlock = cluster.distanceBetweenBlocks(clusterBlockRef, block);

                        if (minimalBlock.get().equals(block) && distanceWithMainBlock < DISTANCE_THRESHOLD) {
                            numberOfAffectedSubmissions++;
                        }
                        // In cases where there are multiple identical blocks, there exist multiple minimum distanced blocks
                        // Handle the case by handling them as interchangeable. The compared value is there to circumvent some
                        // weird distance numbers - TODO discuss it
                        else if (distanceWithMainBlock - distanceWithRefBlock <= 1.0E-10) {
                            numberOfAffectedSubmissions++;
                        }
                    }
                }
                block.setNumberOfAffectedSubmissions(numberOfAffectedSubmissions);
            }
        });
    }

}
