import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/date-picker';
import '@vaadin/vaadin-button';
import '@vaadin/form-layout';
import {FormLayoutResponsiveStep} from '@vaadin/form-layout';
import '@vaadin/text-field';
import '@vaadin/time-picker';

@customElement('student-profile-view')
export class StudentProfileView extends LitElement {

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
                <vaadin-text-field id="firstName" label="First Name"></vaadin-text-field>
                <vaadin-text-field id="lastName" label="Last Name"></vaadin-text-field>
                <vaadin-text-field id="email" label="Email"></vaadin-text-field>
                <vaadin-text-field id="phone" label="Phone"></vaadin-text-field>
                <vaadin-button id="submit">Submit</vaadin-button>
            </vaadin-form-layout>
        `;
    }
}
