import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/date-picker';
import '@vaadin/form-layout';
import {FormLayoutResponsiveStep} from '@vaadin/form-layout';
import '@vaadin/text-field';
import '@vaadin/time-picker';

@customElement('student-view')
export class StudentView extends LitElement {
    protected createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    private responsiveSteps: FormLayoutResponsiveStep[] = [
        {minWidth: 0, columns: 1},
        {minWidth: '20em', columns: 3},
    ];

    render() {
        return html`
            <vaadin-form-layout .responsiveSteps="${this.responsiveSteps}">
                <vaadin-text-field id="title" label="Title" colspan="3"></vaadin-text-field>
                <vaadin-date-picker id="date" label="Date"></vaadin-date-picker>
                <vaadin-time-picker id="from" label="From"></vaadin-time-picker>
                <vaadin-time-picker id="to" label="To"></vaadin-time-picker>
            </vaadin-form-layout>
        `;
    }
}
