import * as chai from 'chai';
import * as sinonChai from 'sinon-chai';
import { ExerciseUpdateWarningService } from 'app/exercises/shared/exercise-update-warning/exercise-update-warning.service';
import { getTestBed } from '@angular/core/testing';
import { GradingInstruction } from 'app/exercises/shared/structured-grading-criterion/grading-instruction.model';
import { GradingCriterion } from 'app/exercises/shared/structured-grading-criterion/grading-criterion.model';
import { Exercise } from 'app/entities/exercise.model';

chai.use(sinonChai);
const expect = chai.expect;

describe('Exercise Update Warning Service', () => {
    let updateWarningService: ExerciseUpdateWarningService;

    const gradingInstruction = { id: 1, credits: 1, gradingScale: 'scale', instructionDescription: 'description', feedback: 'feedback', usageCount: 0 } as GradingInstruction;
    const gradingInstructionCreditsChanged = {
        id: 1,
        credits: 3,
        gradingScale: 'scale',
        instructionDescription: 'description',
        feedback: 'feedback',
        usageCount: 0,
    } as GradingInstruction;
    const gradingCriterion = { id: 1, title: 'testCriteria', structuredGradingInstructions: [gradingInstruction] } as GradingCriterion;
    const gradingCriterionCreditsChanged = { id: 1, title: 'testCriteria', structuredGradingInstructions: [gradingInstructionCreditsChanged] } as GradingCriterion;
    const gradingCriterionWithoutInstruction = { id: 1, title: 'testCriteria' } as GradingCriterion;
    const exercise = { id: 1 } as Exercise;
    const backupExercise = { id: 1 } as Exercise;

    beforeEach(() => {
        const injector = getTestBed();
        updateWarningService = injector.get(ExerciseUpdateWarningService);

        updateWarningService.instructionDeleted = false;
        updateWarningService.scoringChanged = false;
    });

    it('should set instructionDeleted as true', () => {
        exercise.gradingCriteria = [gradingCriterionWithoutInstruction];
        backupExercise.gradingCriteria = [gradingCriterion];
        updateWarningService.loadExercise(exercise, backupExercise);
        expect(updateWarningService.instructionDeleted).to.equal(true);
    });

    it('should set scoringChanged as true', () => {
        exercise.gradingCriteria = [gradingCriterionCreditsChanged];
        backupExercise.gradingCriteria = [gradingCriterion];
        updateWarningService.loadExercise(exercise, backupExercise);
        expect(updateWarningService.scoringChanged).to.equal(true);
    });
});
