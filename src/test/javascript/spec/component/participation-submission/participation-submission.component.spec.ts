import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { AccountService } from 'app/core/auth/account.service';
import * as chai from 'chai';
import * as sinonChai from 'sinon-chai';
import * as moment from 'moment';
import { restore, SinonStub, stub } from 'sinon';
import { ArtemisTestModule } from '../../test.module';
import { MockSyncStorage } from '../../helpers/mocks/service/mock-sync-storage.service';
import { MockComponent } from 'ng-mocks';
import { ArtemisSharedModule } from 'app/shared/shared.module';
import { MomentModule } from 'ngx-moment';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, of } from 'rxjs';
import { AssessmentDetailComponent } from 'app/assessment/assessment-detail/assessment-detail.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { MockAccountService } from '../../helpers/mocks/service/mock-account.service';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { ComplaintService } from 'app/complaints/complaint.service';
import { ParticipationSubmissionComponent } from 'app/exercises/shared/participation-submission/participation-submission.component';
import { SubmissionService } from 'app/exercises/shared/submission/submission.service';
import { MockComplaintService } from '../../helpers/mocks/service/mock-complaint.service';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { TranslateModule } from '@ngx-translate/core';
import { ComplaintsForTutorComponent } from 'app/complaints/complaints-for-tutor/complaints-for-tutor.component';
import { UpdatingResultComponent } from 'app/exercises/shared/result/updating-result.component';
import { ArtemisResultModule } from 'app/exercises/shared/result/result.module';
import { SubmissionExerciseType, SubmissionType } from 'app/entities/submission.model';
import { StudentParticipation } from 'app/entities/participation/student-participation.model';
import { TextSubmission } from 'app/entities/text-submission.model';
import { RouterTestingModule } from '@angular/router/testing';
import { ExerciseService } from 'app/exercises/shared/exercise/exercise.service';
import { Exercise, ExerciseType } from 'app/entities/exercise.model';
import { HttpResponse } from '@angular/common/http';
import { TemplateProgrammingExerciseParticipation } from 'app/entities/participation/template-programming-exercise-participation.model';
import { ProgrammingSubmission } from 'app/entities/programming-submission.model';
import { ProgrammingExerciseService } from 'app/exercises/programming/manage/services/programming-exercise.service';
import { ProgrammingExercise } from 'app/entities/programming-exercise.model';
import { SolutionProgrammingExerciseParticipation } from 'app/entities/participation/solution-programming-exercise-participation.model';
import { ProfileService } from 'app/shared/layouts/profiles/profile.service';
import { ProfileInfo } from 'app/shared/layouts/profiles/profile-info.model';
import { ParticipationService } from 'app/exercises/shared/participation/participation.service';
import { ProgrammingExerciseStudentParticipation } from 'app/entities/participation/programming-exercise-student-participation.model';
import { ParticipationType } from 'app/entities/participation/participation.model';

chai.use(sinonChai);
const expect = chai.expect;

describe('ParticipationSubmissionComponent', () => {
    let comp: ParticipationSubmissionComponent;
    let fixture: ComponentFixture<ParticipationSubmissionComponent>;
    let participationService: ParticipationService;
    let submissionService: SubmissionService;
    let exerciseService: ExerciseService;
    let programmingExerciseService: ProgrammingExerciseService;
    let profileService: ProfileService;
    let findAllSubmissionsOfParticipationStub: SinonStub;
    let debugElement: DebugElement;
    let router: Router;
    const route = { params: of({ participationId: 1, exerciseId: 42 }) };
    // Template for Bitbucket commit hash url
    const commitHashURLTemplate = 'https://bitbucket.ase.in.tum.de/projects/{projectKey}/repos/{repoSlug}/commits/{commitHash}';

    beforeEach(async () => {
        return TestBed.configureTestingModule({
            imports: [ArtemisTestModule, NgxDatatableModule, ArtemisResultModule, ArtemisSharedModule, TranslateModule.forRoot(), RouterTestingModule, MomentModule],
            declarations: [
                ParticipationSubmissionComponent,
                MockComponent(UpdatingResultComponent),
                MockComponent(AssessmentDetailComponent),
                MockComponent(ComplaintsForTutorComponent),
            ],
            providers: [
                JhiLanguageHelper,
                { provide: AccountService, useClass: MockAccountService },
                { provide: SessionStorageService, useClass: MockSyncStorage },
                { provide: LocalStorageService, useClass: MockSyncStorage },
                { provide: ComplaintService, useClass: MockComplaintService },
                { provide: ActivatedRoute, useValue: route },
            ],
        })
            .overrideModule(ArtemisTestModule, { set: { declarations: [], exports: [] } })
            .compileComponents()
            .then(() => {
                fixture = TestBed.createComponent(ParticipationSubmissionComponent);
                comp = fixture.componentInstance;
                comp.participationId = 1;
                debugElement = fixture.debugElement;
                router = debugElement.injector.get(Router);
                participationService = TestBed.inject(ParticipationService);
                submissionService = TestBed.inject(SubmissionService);
                exerciseService = fixture.debugElement.injector.get(ExerciseService);
                programmingExerciseService = fixture.debugElement.injector.get(ProgrammingExerciseService);
                profileService = fixture.debugElement.injector.get(ProfileService);
                findAllSubmissionsOfParticipationStub = stub(submissionService, 'findAllSubmissionsOfParticipation');
                // Set profile info
                const profileInfo = new ProfileInfo();
                profileInfo.commitHashURLTemplate = commitHashURLTemplate;
                stub(profileService, 'getProfileInfo').returns(new BehaviorSubject(profileInfo));
                fixture.ngZone!.run(() => router.initialNavigation());
            });
    });

    afterEach(() => {
        restore();
    });

    it('Should return empty commit url if participation has no repository url', () => {
        const exercise: ProgrammingExercise = {
            numberOfAssessmentsOfCorrectionRounds: [],
            secondCorrectionEnabled: false,
            studentAssignedTeamIdComputed: false,
            projectKey: 'project-key',
        };

        const participation: ProgrammingExerciseStudentParticipation = { id: 1, type: ParticipationType.PROGRAMMING, participantIdentifier: 'identifier' };
        const submission: ProgrammingSubmission = {
            submissionExerciseType: SubmissionExerciseType.PROGRAMMING,
            id: 3,
            submitted: true,
            type: SubmissionType.MANUAL,
            submissionDate: moment('2019-07-09T10:47:33.244Z'),
            commitHash: '123456789',
            participation,
        };
        comp.participation = participation;
        comp.exercise = exercise;
        expect(comp.getCommitUrl(submission)).to.be.empty;
    });

    it('Submissions are correctly loaded from server', fakeAsync(() => {
        // set all attributes for comp
        const participation = new StudentParticipation();
        participation.id = 1;
        stub(participationService, 'find').returns(of(new HttpResponse({ body: participation })));
        const submissions = [
            {
                submissionExerciseType: SubmissionExerciseType.TEXT,
                id: 2278,
                submitted: true,
                type: SubmissionType.MANUAL,
                submissionDate: moment('2019-07-09T10:47:33.244Z'),
                text: 'My TextSubmission',
                participation,
            },
        ] as TextSubmission[];
        const exercise = { type: ExerciseType.TEXT } as Exercise;
        stub(exerciseService, 'find').returns(of(new HttpResponse({ body: exercise })));
        findAllSubmissionsOfParticipationStub.returns(of({ body: submissions }));

        fixture.detectChanges();
        tick();

        expect(comp.isLoading).to.be.false;
        // check if findAllSubmissionsOfParticipationStub() is called and works
        expect(findAllSubmissionsOfParticipationStub).to.have.been.called;
        expect(comp.participation).to.be.deep.equal(participation);
        expect(comp.submissions).to.be.deep.equal(submissions);

        // check if delete button is available
        const deleteButton = debugElement.query(By.css('#deleteButton'));
        expect(deleteButton).to.exist;

        // check if the right amount of rows is visible
        const row = debugElement.query(By.css('#participationSubmissionTable'));
        expect(row.childNodes.length).to.equal(1);

        fixture.destroy();
        flush();
    }));

    it('Template Submission is correctly loaded', fakeAsync(() => {
        TestBed.get(ActivatedRoute).params = of({ participationId: 2, exerciseId: 42 });
        TestBed.get(ActivatedRoute).queryParams = of({ isTmpOrSolutionProgrParticipation: 'true' });
        const templateParticipation = new TemplateProgrammingExerciseParticipation();
        templateParticipation.id = 2;
        templateParticipation.submissions = [
            {
                submissionExerciseType: SubmissionExerciseType.PROGRAMMING,
                id: 3,
                submitted: true,
                type: SubmissionType.MANUAL,
                submissionDate: moment('2019-07-09T10:47:33.244Z'),
                commitHash: '123456789',
                participation: templateParticipation,
            },
        ] as ProgrammingSubmission[];
        const programmingExercise = { type: ExerciseType.PROGRAMMING, projectKey: 'SUBMISSION1', templateParticipation } as ProgrammingExercise;
        const findWithTemplateAndSolutionParticipationStub = stub(programmingExerciseService, 'findWithTemplateAndSolutionParticipation');
        findWithTemplateAndSolutionParticipationStub.returns(of(new HttpResponse({ body: programmingExercise })));

        fixture.detectChanges();
        tick();

        expect(comp.isLoading).to.be.false;
        expect(findWithTemplateAndSolutionParticipationStub).to.have.been.called;
        expect(comp.exercise).to.be.deep.equal(programmingExercise);
        expect(comp.participation).to.be.deep.equal(templateParticipation);
        expect(comp.submissions).to.be.deep.equal(templateParticipation.submissions);

        // Create correct url for commit hash
        const submission = templateParticipation.submissions[0] as ProgrammingSubmission;
        checkForCorrectCommitHashUrl(submission, programmingExercise, '-exercise');

        fixture.destroy();
        flush();
    }));

    it('Solution Submission is correctly loaded', fakeAsync(() => {
        TestBed.get(ActivatedRoute).params = of({ participationId: 3, exerciseId: 42 });
        TestBed.get(ActivatedRoute).queryParams = of({ isTmpOrSolutionProgrParticipation: 'true' });
        const solutionParticipation = new SolutionProgrammingExerciseParticipation();
        solutionParticipation.id = 3;
        solutionParticipation.submissions = [
            {
                submissionExerciseType: SubmissionExerciseType.PROGRAMMING,
                id: 4,
                submitted: true,
                type: SubmissionType.MANUAL,
                submissionDate: moment('2019-07-09T10:47:33.244Z'),
                commitHash: '123456789',
                participation: solutionParticipation,
            },
        ] as ProgrammingSubmission[];
        const programmingExercise = { type: ExerciseType.PROGRAMMING, projectKey: 'SUBMISSION1', solutionParticipation } as ProgrammingExercise;
        const findWithTemplateAndSolutionParticipationStub = stub(programmingExerciseService, 'findWithTemplateAndSolutionParticipation');
        findWithTemplateAndSolutionParticipationStub.returns(of(new HttpResponse({ body: programmingExercise })));

        fixture.detectChanges();
        tick();

        expect(comp.isLoading).to.be.false;
        expect(findWithTemplateAndSolutionParticipationStub).to.have.been.called;
        expect(comp.participation).to.be.deep.equal(solutionParticipation);
        expect(comp.submissions).to.be.deep.equal(solutionParticipation.submissions);

        // Create correct url for commit hash
        const submission = solutionParticipation.submissions[0] as ProgrammingSubmission;
        checkForCorrectCommitHashUrl(submission, programmingExercise, '-solution');

        fixture.destroy();
        flush();
    }));

    function checkForCorrectCommitHashUrl(submission: ProgrammingSubmission, programmingExercise: ProgrammingExercise, repoSlug: string) {
        const projectKey = programmingExercise.projectKey!.toLowerCase();
        const receivedCommitHashUrl = comp.getCommitUrl(submission);
        const commitHashUrl = commitHashURLTemplate
            .replace('{projectKey}', projectKey)
            .replace('{repoSlug}', projectKey + repoSlug)
            .replace('{commitHash}', submission.commitHash!);
        expect(receivedCommitHashUrl).to.equal(commitHashUrl);
    }
});
