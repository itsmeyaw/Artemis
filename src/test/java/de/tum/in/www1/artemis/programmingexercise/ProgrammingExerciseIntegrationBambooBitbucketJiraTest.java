package de.tum.in.www1.artemis.programmingexercise;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import de.tum.in.www1.artemis.AbstractSpringIntegrationBambooBitbucketJiraTest;
import de.tum.in.www1.artemis.domain.enumeration.ProgrammingLanguage;
import de.tum.in.www1.artemis.service.ProgrammingExerciseIntegrationServiceTest;

class ProgrammingExerciseIntegrationBambooBitbucketJiraTest extends AbstractSpringIntegrationBambooBitbucketJiraTest {

    @Autowired
    ProgrammingExerciseIntegrationServiceTest programmingExerciseIntegrationServiceTest;

    @BeforeEach
    void initTestCase() throws Exception {
        bitbucketRequestMockProvider.enableMockingOfRequests(true);
        bambooRequestMockProvider.enableMockingOfRequests(true);
        programmingExerciseIntegrationServiceTest.setup(this, versionControlService);
    }

    @AfterEach
    void tearDown() throws IOException {
        programmingExerciseIntegrationServiceTest.tearDown();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void textProgrammingExerciseIsReleased_IsReleasedAndHasResults() throws Exception {
        programmingExerciseIntegrationServiceTest.textProgrammingExerciseIsReleased_IsReleasedAndHasResults();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void textProgrammingExerciseIsReleased_IsNotReleasedAndHasResults() throws Exception {
        programmingExerciseIntegrationServiceTest.textProgrammingExerciseIsReleased_IsNotReleasedAndHasResults();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void checkIfProgrammingExerciseIsReleased_IsReleasedAndHasNoResults() throws Exception {
        programmingExerciseIntegrationServiceTest.checkIfProgrammingExerciseIsReleased_IsReleasedAndHasNoResults();
    }

    @Test
    @WithMockUser(username = "student1", roles = "USER")
    void textProgrammingExerciseIsReleased_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.textProgrammingExerciseIsReleased_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void textExportSubmissionsByParticipationIds() throws Exception {
        programmingExerciseIntegrationServiceTest.textExportSubmissionsByParticipationIds();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void textExportSubmissionsByParticipationIds_invalidParticipationId_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.textExportSubmissionsByParticipationIds_invalidParticipationId_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    void textExportSubmissionsByParticipationIds_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.textExportSubmissionsByParticipationIds_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void textExportSubmissionsByStudentLogins() throws Exception {
        programmingExerciseIntegrationServiceTest.textExportSubmissionsByStudentLogins();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testProgrammingExerciseDelete() throws Exception {
        programmingExerciseIntegrationServiceTest.testProgrammingExerciseDelete();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testProgrammingExerciseDelete_invalidId_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.testProgrammingExerciseDelete_invalidId_notFound();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    void testProgrammingExerciseDelete_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.testProgrammingExerciseDelete_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGetProgrammingExercise() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExercise();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGetProgrammingExerciseWithStructuredGradingInstruction() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExerciseWithStructuredGradingInstruction();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    void testGetProgrammingExercise_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExercise_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGetProgrammingExerciseWithSetupParticipations() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExerciseWithSetupParticipations();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    void testGetProgrammingExerciseWithJustTemplateAndSolutionParticipation() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExerciseWithJustTemplateAndSolutionParticipation();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    void testGetProgrammingExerciseWithSetupParticipations_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExerciseWithSetupParticipations_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGetProgrammingExerciseWithSetupParticipations_invalidId_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExerciseWithSetupParticipations_invalidId_notFound();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGetProgrammingExercisesForCourse() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExercisesForCourse();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    void testGetProgrammingExercisesForCourse_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.testGetProgrammingExercisesForCourse_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    void testGenerateStructureOracle() throws Exception {
        programmingExerciseIntegrationServiceTest.testGenerateStructureOracle();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_invalidTemplateBuildPlan_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_invalidTemplateBuildPlan_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_idIsNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_idIsNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_staticCodeAnalysisMustNotChange_falseToTrue_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_staticCodeAnalysisMustNotChange_falseToTrue_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_staticCodeAnalysisMustNotChange_trueToFalse_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_staticCodeAnalysisMustNotChange_trueToFalse_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_invalidTemplateVcs_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_invalidTemplateVcs_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_invalidSolutionBuildPlan_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_invalidSolutionBuildPlan_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProgrammingExercise_invalidSolutionRepository_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProgrammingExercise_invalidSolutionRepository_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void updateTimeline_intructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTimeline_intructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void updateTimeline_invalidId_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTimeline_invalidId_notFound();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateTimeline_ok() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTimeline_ok();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void updateProblemStatement_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProblemStatement_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateProblemStatement_invalidId_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.updateProblemStatement_invalidId_notFound();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_exerciseIsNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_exerciseIsNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_idIsNotNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_idIsNotNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_titleNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_titleNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_titleContainsBadCharacter_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_titleContainsBadCharacter_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_invalidShortName_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_invalidShortName_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_invalidCourseShortName_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_invalidCourseShortName_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_sameShortNameInCourse_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_sameShortNameInCourse_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_shortNameContainsBadCharacters_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_shortNameContainsBadCharacters_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_noProgrammingLanguageSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_noProgrammingLanguageSet_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_packageNameContainsBadCharacters_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_packageNameContainsBadCharacters_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_packageNameContainsKeyword_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_packageNameContainsKeyword_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_packageNameElementBeginsWithDigit_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_packageNameElementBeginsWithDigit_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_packageNameIsNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_packageNameIsNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_maxScoreIsNull_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_maxScoreIsNull_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_noParticipationModeSelected_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_noParticipationModeSelected_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_staticCodeAnalysisMustBeSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_staticCodeAnalysisMustBeSet_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_staticCodeAnalysisAndSequential_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_staticCodeAnalysisAndSequential_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_unsupportedProgrammingLanguageForStaticCodeAnalysis_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_unsupportedProgrammingLanguageForStaticCodeAnalysis_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_noStaticCodeAnalysisButMaxPenalty_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_noStaticCodeAnalysisButMaxPenalty_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_maxStaticCodePenaltyNegative_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_maxStaticCodePenaltyNegative_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_vcsProjectWithSameKeyAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_vcsProjectWithSameKeyAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_bambooProjectWithSameKeyAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_bambooProjectWithSameKeyAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_vcsProjectWithSameTitleAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_vcsProjectWithSameTitleAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_bambooProjectWithSameTitleAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_bambooProjectWithSameTitleAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_projectTypeMissing_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_projectTypeMissing_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_projectTypeNotExpected_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_projectTypeNotExpected_badRequest();
    }

    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    @ParameterizedTest
    // It should fail for all ProgrammingExercises except Haskell
    @EnumSource(value = ProgrammingLanguage.class, names = { "HASKELL" }, mode = EnumSource.Mode.EXCLUDE)
    public void createProgrammingExercise_checkoutSolutionRepositoryProgrammingLanguageNotSupported_badRequest(ProgrammingLanguage programmingLanguage) throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_checkoutSolutionRepositoryProgrammingLanguageNotSupported_badRequest(programmingLanguage);
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_invalidMaxScore_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_invalidMaxScore_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_includedAsBonus_invalidBonusPoints_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_includedAsBonus_invalidBonusPoints_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void createProgrammingExercise_notIncluded_invalidBonusPoints_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.createProgrammingExercise_notIncluded_invalidBonusPoints_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_sourceExerciseIdNegative_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_sourceExerciseIdNegative_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void importProgrammingExerciseMaxScoreNullBadRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExerciseMaxScoreNullBadRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_noParticipationModeSelected_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_noParticipationModeSelected_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_noProgrammingLanguage_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_noProgrammingLanguage_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_instructorNotInCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_instructorNotInCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_templateIdDoesNotExist_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_templateIdDoesNotExist_notFound();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_sameShortNameInCourse_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_sameShortNameInCourse_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_sameTitleInCourse_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_sameTitleInCourse_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_staticCodeAnalysisMustBeSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_staticCodeAnalysisMustBeSet_badRequest();
    }

    @ParameterizedTest
    @CsvSource({ "false, false", "true, false", "false, true", })
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_scaChanged_badRequest(boolean recreateBuildPlan, boolean updateTemplate) throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_scaChanged_badRequest(recreateBuildPlan, updateTemplate);
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_eitherCourseOrExerciseGroupSet_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_vcsProjectWithSameKeyAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_vcsProjectWithSameKeyAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_bambooProjectWithSameKeyAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_bambooProjectWithSameKeyAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_vcsProjectWithSameTitleAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_vcsProjectWithSameTitleAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void importProgrammingExercise_bambooProjectWithSameTitleAlreadyExists_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.importProgrammingExercise_bambooProjectWithSameTitleAlreadyExists_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void exportSubmissionsByStudentLogins_notInstructorForExercise_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.exportSubmissionsByStudentLogins_notInstructorForExercise_forbidden();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    public void exportSubmissionsByStudentLogins_exportAllAsTutor_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.exportSubmissionsByStudentLogins_exportAllAsTutor_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void generateStructureOracleForExercise_exerciseDoesNotExist_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.generateStructureOracleForExercise_exerciseDoesNotExist_badRequest();
    }

    @Test
    @WithMockUser(username = "instructoralt1", roles = "INSTRUCTOR")
    public void generateStructureOracleForExercise_userIsNotAdminInCourse_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.generateStructureOracleForExercise_userIsNotAdminInCourse_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void generateStructureOracleForExercise_invalidPackageName_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.generateStructureOracleForExercise_invalidPackageName_badRequest();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    public void hasAtLeastOneStudentResult_exerciseDoesNotExist_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.hasAtLeastOneStudentResult_exerciseDoesNotExist_notFound();
    }

    @Test
    @WithMockUser(username = "tutoralt1", roles = "TA")
    public void hasAtLeastOneStudentResult_isNotTeachingAssistant_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.hasAtLeastOneStudentResult_isNotTeachingAssistant_forbidden();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    public void getTestCases_asTutor() throws Exception {
        programmingExerciseIntegrationServiceTest.getTestCases_asTutor();
    }

    @Test
    @WithMockUser(username = "student1", roles = "STUDENT")
    public void getTestCases_asStudent_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.getTestCases_asStudent_forbidden();
    }

    @Test
    @WithMockUser(username = "other-teaching-assistant1", roles = "TA")
    public void getTestCases_tutorInOtherCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.getTestCases_tutorInOtherCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateTestCases_asInstrutor() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTestCases_asInstrutor();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateTestCases_nonExistingExercise_notFound() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTestCases_nonExistingExercise_notFound();
    }

    @Test
    @WithMockUser(username = "other-instructor1", roles = "INSTRUCTOR")
    public void updateTestCases_instructorInWrongCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTestCases_instructorInWrongCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void updateTestCases_testCaseWeightSmallerThanZero_badRequest() throws Exception {
        programmingExerciseIntegrationServiceTest.updateTestCases_testCaseWeightSmallerThanZero_badRequest();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void resetTestCaseWeights_asInstructor() throws Exception {
        programmingExerciseIntegrationServiceTest.resetTestCaseWeights_asInstructor();
    }

    @Test
    @WithMockUser(username = "other-instructor1", roles = "INSTRUCTOR")
    public void resetTestCaseWeights_instructorInWrongCourse_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.resetTestCaseWeights_instructorInWrongCourse_forbidden();
    }

    @Test
    @WithMockUser(username = "student1", roles = "USER")
    public void lockAllRepositories_asStudent_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.lockAllRepositories_asStudent_forbidden();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    public void lockAllRepositories_asTutor_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.lockAllRepositories_asTutor_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void lockAllRepositories() throws Exception {
        programmingExerciseIntegrationServiceTest.lockAllRepositories();
    }

    @Test
    @WithMockUser(username = "student1", roles = "USER")
    public void unlockAllRepositories_asStudent_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.unlockAllRepositories_asStudent_forbidden();
    }

    @Test
    @WithMockUser(username = "tutor1", roles = "TA")
    public void unlockAllRepositories_asTutor_forbidden() throws Exception {
        programmingExerciseIntegrationServiceTest.unlockAllRepositories_asTutor_forbidden();
    }

    @Test
    @WithMockUser(username = "instructor1", roles = "INSTRUCTOR")
    public void unlockAllRepositories() throws Exception {
        programmingExerciseIntegrationServiceTest.unlockAllRepositories();
    }
}