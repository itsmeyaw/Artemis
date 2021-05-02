import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from 'app/app.constants';
import { Graphs, SpanType, StatisticsView } from 'app/entities/statistics.model';
import { CourseManagementStatisticsDTO } from 'app/course/manage/course-management-statistics-dto';
import { ExerciseStatisticsDTO } from 'app/exercises/text/manage/statistics/exercise-statistics-dto';

@Injectable({ providedIn: 'root' })
export class StatisticsService {
    private resourceUrl = SERVER_API_URL + 'api/management/statistics/';

    constructor(private http: HttpClient) {}

    /**
     * Sends a GET request to retrieve the data for a graph based on the graphType in the last *span* days and the given period
     */
    getChartData(span: SpanType, periodIndex: number, graphType: Graphs): Observable<number[]> {
        const params = new HttpParams()
            .set('span', '' + span)
            .set('periodIndex', '' + periodIndex)
            .set('graphType', '' + graphType);
        return this.http.get<number[]>(`${this.resourceUrl}data`, { params });
    }

    /**
     * Sends a GET request to retrieve the data for a graph based on the graphType in the last *span* days, the given period and the courseId
     */
    getChartDataForContent(span: SpanType, periodIndex: number, graphType: Graphs, view: StatisticsView, courseId: number): Observable<number[]> {
        const params = new HttpParams()
            .set('span', '' + span)
            .set('periodIndex', '' + periodIndex)
            .set('graphType', '' + graphType)
            .set('view', '' + view)
            .set('courseId', '' + courseId);
        return this.http.get<number[]>(`${this.resourceUrl}data-for-content`, { params });
    }

    /**
     * Sends a GET request to retrieve data needed for the course statistics
     */
    getCourseStatistics(courseId: number): Observable<CourseManagementStatisticsDTO> {
        const params = new HttpParams().set('courseId', '' + courseId);
        return this.http.get<CourseManagementStatisticsDTO>(`${this.resourceUrl}course-statistics`, { params });
    }

    /**
     * Sends a GET request to retrieve data needed for the exercise statistics
     */
    getExerciseStatistics(exerciseId: number): Observable<ExerciseStatisticsDTO> {
        const params = new HttpParams().set('exerciseId', '' + exerciseId);
        return this.http.get<ExerciseStatisticsDTO>(`${this.resourceUrl}exercise-statistics`, { params });
    }
}
