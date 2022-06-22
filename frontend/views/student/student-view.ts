import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/date-picker';
import '@vaadin/vaadin-button';
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
                <vaadin-text-field id="subject" label="Subject" colspan="3"></vaadin-text-field>
                <vaadin-date-picker id="date" label="Date"></vaadin-date-picker>
                <vaadin-text-field id="from" label="From"></vaadin-text-field>
                <vaadin-text-field id="to" label="To"></vaadin-text-field>
                <vaadin-text-area id="message" label="Message"></vaadin-text-area>
                <vaadin-button id="submit">Submit</vaadin-button>
            </vaadin-form-layout>
        `;
    }
}
