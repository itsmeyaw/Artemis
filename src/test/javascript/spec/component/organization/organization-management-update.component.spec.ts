import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Observable, of } from 'rxjs';

import { OrganizationManagementUpdateComponent } from 'app/admin/organization-management/organization-management-update.component';
import { OrganizationManagementService } from 'app/admin/organization-management/organization-management.service';
import { Organization } from 'app/entities/organization.model';
import { ArtemisTestModule } from '../../test.module';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute } from '@angular/router';
import { MockSyncStorage } from '../../helpers/mocks/service/mock-sync-storage.service';
import { MockTranslateService } from '../../helpers/mocks/service/mock-translate.service';

describe('OrganizationManagementUpdateComponent', () => {
    let component: OrganizationManagementUpdateComponent;
    let fixture: ComponentFixture<OrganizationManagementUpdateComponent>;
    let organizationService: OrganizationManagementService;
    const organization1 = new Organization();
    organization1.id = 5;
    const parentRoute = ({
        data: of({ organization: organization1 }),
    } as any) as ActivatedRoute;
    const route = ({ parent: parentRoute } as any) as ActivatedRoute;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [ArtemisTestModule],
            declarations: [OrganizationManagementUpdateComponent],
            providers: [
                { provide: LocalStorageService, useClass: MockSyncStorage },
                { provide: SessionStorageService, useClass: MockSyncStorage },
                { provide: TranslateService, useClass: MockTranslateService },
                { provide: ActivatedRoute, useValue: route },
            ],
        })
            .overrideTemplate(OrganizationManagementUpdateComponent, '')
            .compileComponents();

        fixture = TestBed.createComponent(OrganizationManagementUpdateComponent);
        component = fixture.componentInstance;
        organizationService = TestBed.inject(OrganizationManagementService);
    });

    afterEach(async () => {
        jest.clearAllMocks();
    });

    describe('OnInit', () => {
        it('should create', () => {
            expect(component).toBeTruthy();
        });

        it('should initialize and load organization from route if any', fakeAsync(() => {
            organization1.name = 'orgOne';
            organization1.shortName = 'oO1';
            organization1.emailPattern = '.*1';

            spyOn(organizationService, 'getOrganizationById').and.returnValue(Observable.of(organization1));

            component.ngOnInit();

            expect(component.organization.id).toEqual(organization1.id);
        }));
    });

    describe('Save', () => {
        it('should update the current edited organization', fakeAsync(() => {
            organization1.name = 'updatedName';
            component.organization = organization1;
            spyOn(organizationService, 'update').and.returnValue(Observable.of(organization1));

            component.save();
            tick();

            expect(organizationService.update).toHaveBeenCalledWith(organization1);
            expect(component.isSaving).toEqual(false);
        }));

        it('should add the current created organization', fakeAsync(() => {
            const newOrganization = new Organization();
            newOrganization.name = 'newOrg';
            newOrganization.shortName = 'newO';
            newOrganization.emailPattern = '.*';

            component.organization = newOrganization;
            spyOn(organizationService, 'add').and.returnValue(Observable.of(newOrganization));

            component.save();
            tick();

            expect(organizationService.add).toHaveBeenCalledWith(newOrganization);
            expect(component.isSaving).toEqual(false);
        }));
    });
});