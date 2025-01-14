import { Component, Input } from '@angular/core';
import { Submission } from 'app/entities/submission.model';
import { TextSubmission } from 'app/entities/text-submission.model';

@Component({
    selector: 'jhi-language-table-cell',
    templateUrl: './language-table-cell.component.html',
})
export class LanguageTableCellComponent {
    textSubmission: TextSubmission;

    @Input()
    set submission(submission: Submission) {
        this.textSubmission = submission as TextSubmission;
    }
}
