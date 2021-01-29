import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { Exercise, ExerciseType } from 'app/entities/exercise.model';
import { Course } from 'app/entities/course.model';
import { CourseExerciseStatisticsDTO } from 'app/exercises/shared/exercise/exercise-statistics-dto.model';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

export enum ExerciseRowType {
    FUTURE = 'future',
    CURRENT = 'current',
    ASSESSING = 'assessment',
    PAST = 'past',
}

@Component({
    selector: 'jhi-course-management-exercise-row',
    templateUrl: './course-management-exercise-row.component.html',
    styleUrls: ['course-management-exercise-row.scss'],
})
export class CourseManagementExerciseRowComponent implements OnInit, OnChanges {
    @Input() course: Course;
    @Input() exercise: Exercise;
    @Input() statistic: CourseExerciseStatisticsDTO;
    @Input() rowType: ExerciseRowType;

    // Expose enums to the template
    exerciseType = ExerciseType;
    exerciseRowType = ExerciseRowType;

    hasLeftoverAssessments = false;
    isTeamExercise: boolean;
    displayTitle: string;
    averageScoreNumerator: number;
    noOfAssessmentsDoneInPercent: number;

    // TODO:
    JSON = JSON;

    getIcon(type: ExerciseType | undefined): IconProp {
        switch (type) {
            case ExerciseType.PROGRAMMING:
                return 'keyboard';
            case ExerciseType.MODELING:
                return 'project-diagram';
            case ExerciseType.QUIZ:
                return 'check-double';
            case ExerciseType.TEXT:
                return 'font';
            default:
            case ExerciseType.FILE_UPLOAD:
                return 'file-upload';
        }
    }

    getIconTooltip(type: ExerciseType | undefined): string {
        switch (type) {
            case ExerciseType.PROGRAMMING:
                return 'artemisApp.exercise.isProgramming';
            case ExerciseType.MODELING:
                return 'artemisApp.exercise.isModeling';
            case ExerciseType.QUIZ:
                return 'artemisApp.exercise.isQuiz';
            case ExerciseType.TEXT:
                return 'artemisApp.exercise.isText';
            default:
            case ExerciseType.FILE_UPLOAD:
                return 'artemisApp.exercise.isFileUpload';
        }
    }

    constructor() {}

    ngOnInit() {
        this.displayTitle = this.exercise.title ?? '';
        this.isTeamExercise = this.exercise.teamMode ?? false;
    }

    ngOnChanges() {
        if (!this.statistic) {
            return;
        }
        this.noOfAssessmentsDoneInPercent =
            this.statistic.noOfRatedAssessments && this.statistic.noOfRatedAssessments >= 0 && this.statistic.noOfSubmissionsInTime && this.statistic.noOfSubmissionsInTime > 0
                ? Math.round(((this.statistic.noOfRatedAssessments / this.statistic.noOfSubmissionsInTime) * 1000) / 10)
                : 0;
        this.averageScoreNumerator = Math.round((this.statistic.averageScoreInPercent! * this.statistic.exerciseMaxPoints!) / 100);
    }
}
