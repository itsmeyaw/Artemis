import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { JhiWebsocketService } from 'app/core/websocket/websocket.service';
import { StudentParticipation } from 'app/entities/participation/student-participation.model';
import { throttleTime, filter } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { SubmissionSyncPayload } from 'app/entities/submission-sync-payload.model';
import { AccountService } from 'app/core/auth/account.service';
import { User } from 'app/core/user/user.model';
import { Submission } from 'app/entities/submission.model';
import { Observable } from 'rxjs';
import { ExerciseType } from 'app/entities/exercise.model';

@Component({
    selector: 'jhi-team-submission-sync',
    template: '',
})
export class TeamSubmissionSyncComponent implements OnInit {
    // Sync settings
    readonly throttleTime = 2000; // ms

    @Input() exerciseType: ExerciseType;
    @Input() submission$: Observable<Submission>;
    @Input() participation: StudentParticipation;

    @Output() receiveSubmission = new EventEmitter<Submission>();

    currentUser: User;
    websocketTopic: string;

    constructor(private accountService: AccountService, private teamSubmissionWebsocketService: JhiWebsocketService, private jhiAlertService: JhiAlertService) {
        this.accountService.identity().then((user: User) => (this.currentUser = user));
    }

    /**
     * Life cycle hook to indicate component creation is done
     */
    ngOnInit(): void {
        this.websocketTopic = this.buildWebsocketTopic('');
        this.teamSubmissionWebsocketService.subscribe(this.websocketTopic);
        this.setupReceiver();
        this.setupSender();
    }

    /**
     * Receives updated submissions from other team members and emits them
     */
    private setupReceiver() {
        this.teamSubmissionWebsocketService
            .receive(this.websocketTopic)
            .pipe(filter(({ sender }: SubmissionSyncPayload) => !this.isSelf(sender)))
            .subscribe(
                ({ submission }: SubmissionSyncPayload) => {
                    this.receiveSubmission.emit(submission);
                },
                (error) => this.onError(error),
            );
    }

    /**
     * Subscribes to the submission$ stream and sends out updated submissions based on those own changes via websockets
     */
    private setupSender() {
        this.submission$.pipe(throttleTime(this.throttleTime, undefined, { leading: true, trailing: true })).subscribe(
            (submission: Submission) => {
                if (submission.participation) {
                    submission.participation.exercise = undefined;
                    submission.participation.submissions = [];
                }
                this.teamSubmissionWebsocketService.send(this.buildWebsocketTopic('/update'), submission);
            },
            (error) => this.onError(error),
        );
    }

    private isSelf(user: User) {
        return this.currentUser.login === user.login;
    }

    private buildWebsocketTopic(path = ''): string {
        return `/topic/participations/${this.participation.id}/team/${this.exerciseType}-submissions${path}`;
    }

    private onError(error: string) {
        this.jhiAlertService.error(error);
    }
}
