import { BaseEntity } from 'app/shared/model/base-entity';
import { Moment } from 'moment';
import { StudentParticipation } from 'app/entities/participation/student-participation.model';
import { AssessmentType } from 'app/entities/assessment-type.model';
import { TutorParticipation } from 'app/entities/participation/tutor-participation.model';
import { Course } from 'app/entities/course.model';
import { ExampleSubmission } from 'app/entities/example-submission.model';
import { Attachment } from 'app/entities/attachment.model';
import { StudentQuestion } from 'app/entities/student-question.model';
import { TeamAssignmentConfig } from 'app/entities/team-assignment-config.model';
import { ExerciseHint } from 'app/entities/exercise-hint.model';
import { GradingCriterion } from 'app/exercises/shared/structured-grading-criterion/grading-criterion.model';
import { Team } from 'app/entities/team.model';
import { DueDateStat } from 'app/course/dashboards/instructor-course-dashboard/due-date-stat.model';
import { ExerciseGroup } from 'app/entities/exercise-group.model';
import { LearningGoal } from 'app/entities/learningGoal.model';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

export enum DifficultyLevel {
    EASY = 'EASY',
    MEDIUM = 'MEDIUM',
    HARD = 'HARD',
}

export enum ExerciseMode {
    INDIVIDUAL = 'INDIVIDUAL',
    TEAM = 'TEAM',
}

// IMPORTANT NOTICE: The following strings have to be consistent with the ones defined in Exercise.java
export enum ExerciseType {
    PROGRAMMING = 'programming',
    MODELING = 'modeling',
    QUIZ = 'quiz',
    TEXT = 'text',
    FILE_UPLOAD = 'file-upload',
}

// IMPORTANT NOTICE: The following strings have to be consistent with the ones defined in Exercise.java
export enum IncludedInOverallScore {
    INCLUDED_COMPLETELY = 'INCLUDED_COMPLETELY',
    INCLUDED_AS_BONUS = 'INCLUDED_AS_BONUS',
    NOT_INCLUDED = 'NOT_INCLUDED',
}

export enum ParticipationStatus {
    QUIZ_UNINITIALIZED = 'quiz-uninitialized',
    QUIZ_ACTIVE = 'quiz-active',
    QUIZ_SUBMITTED = 'quiz-submitted',
    QUIZ_NOT_STARTED = 'quiz-not-started',
    QUIZ_NOT_PARTICIPATED = 'quiz-not-participated',
    QUIZ_FINISHED = 'quiz-finished',
    NO_TEAM_ASSIGNED = 'no-team-assigned',
    UNINITIALIZED = 'uninitialized',
    INITIALIZED = 'initialized',
    INACTIVE = 'inactive',
    EXERCISE_ACTIVE = 'exercise-active',
    EXERCISE_SUBMITTED = 'exercise-submitted',
    EXERCISE_MISSED = 'exercise-missed',
}

export interface ExerciseCategory {
    exerciseId: number;
    category: string;
    color: string;
}

export abstract class Exercise implements BaseEntity {
    public id?: number;
    public problemStatement?: string;
    public gradingInstructions?: string;
    public title?: string;
    public shortName?: string;
    public releaseDate?: Moment;
    public dueDate?: Moment;
    public assessmentDueDate?: Moment;
    public maxScore?: number;
    public bonusPoints?: number;
    public assessmentType?: AssessmentType;
    public difficulty?: DifficultyLevel;
    public mode?: ExerciseMode = ExerciseMode.INDIVIDUAL; // default value
    public includedInOverallScore?: IncludedInOverallScore = IncludedInOverallScore.INCLUDED_COMPLETELY; // default value
    public teamAssignmentConfig?: TeamAssignmentConfig;
    public categories?: string[];
    public type: ExerciseType;

    public teams?: Team[];
    public studentParticipations?: StudentParticipation[];
    public tutorParticipations?: TutorParticipation[];
    public course?: Course;
    public participationStatus?: ParticipationStatus;
    public exampleSubmissions?: ExampleSubmission[];
    public attachments?: Attachment[];
    public studentQuestions?: StudentQuestion[];
    public exerciseHints?: ExerciseHint[];
    public gradingCriteria?: GradingCriterion[];
    public exerciseGroup?: ExerciseGroup;
    public learningGoals?: LearningGoal[];

    // transient objects which might not be set
    public numberOfSubmissions?: DueDateStat;
    public totalNumberOfAssessments?: DueDateStat;
    public numberOfAssessmentsOfCorrectionRounds = [new DueDateStat()]; // Array with number of assessments for each correction round
    public numberOfComplaints?: number;
    public numberOfOpenComplaints?: number;
    public numberOfMoreFeedbackRequests?: number;
    public numberOfOpenMoreFeedbackRequests?: number;
    public studentAssignedTeamId?: number;
    public studentAssignedTeamIdComputed = false;

    // helper attributes
    public secondCorrectionEnabled = false;
    public isAtLeastTutor?: boolean;
    public isAtLeastInstructor?: boolean;
    public teamMode?: boolean;
    public assessmentDueDateError?: boolean;
    public dueDateError?: boolean;
    public loading?: boolean;
    public numberOfParticipationsWithRatedResult?: number;
    public numberOfSuccessfulParticipations?: number;
    public averagePoints?: number;
    public presentationScoreEnabled?: boolean;

    protected constructor(type: ExerciseType) {
        this.type = type;
        this.bonusPoints = 0; // default value
        this.isAtLeastTutor = false; // default value
        this.isAtLeastInstructor = false; // default value
        this.teamMode = false; // default value
        this.assessmentDueDateError = false;
        this.dueDateError = false;
        this.presentationScoreEnabled = false; // default value;
    }

    /**
     * Sanitize exercise attributes.
     * This method should be used before sending an exercise to the server.
     *
     * @param exercise
     */
    public static sanitize(exercise: Exercise): void {
        exercise.title = exercise.title?.trim();
    }
}

export function getIcon(exerciseType: ExerciseType): IconProp {
    switch (exerciseType) {
        case ExerciseType.PROGRAMMING:
            return 'keyboard';
        case ExerciseType.MODELING:
            return 'project-diagram';
        case ExerciseType.QUIZ:
            return 'check-double';
        case ExerciseType.TEXT:
            return 'font';
        case ExerciseType.FILE_UPLOAD:
            return 'file-upload';
    }
}

export function getIconTooltip(exerciseType: ExerciseType): string {
    switch (exerciseType) {
        case ExerciseType.PROGRAMMING:
            return 'artemisApp.exercise.isProgramming';
        case ExerciseType.MODELING:
            return 'artemisApp.exercise.isModeling';
        case ExerciseType.QUIZ:
            return 'artemisApp.exercise.isQuiz';
        case ExerciseType.TEXT:
            return 'artemisApp.exercise.isText';
        case ExerciseType.FILE_UPLOAD:
            return 'artemisApp.exercise.isFileUpload';
    }
}

/**
 * Get the course id for an exercise.
 * The course id is extracted from the course of the exercise if present, if not present (exam mode), it is extracted from the corresponding exam.
 * @param exercise the exercise for which the course id should be extracted
 */
export function getCourseId(exercise: Exercise): number | undefined {
    return getCourseFromExercise(exercise)?.id;
}

/**
 * Get the course for an exercise.
 * The course is extracted from the course of the exercise if present, if not present (exam mode), it is extracted from the corresponding exam.
 * @param exercise the exercise for which the course should be extracted
 */
export function getCourseFromExercise(exercise: Exercise): Course | undefined {
    return exercise.course || exercise.exerciseGroup?.exam?.course;
}
