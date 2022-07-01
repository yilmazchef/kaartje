import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/date-picker';
import '@vaadin/vaadin-button';
import '@vaadin/form-layout';
import '@vaadin/text-field';

@customElement('student-profile-view')
export class StudentProfileView extends LitElement {

    protected createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
            <vaadin-form-layout>
                <vaadin-text-field id="firstName" label="First Name"></vaadin-text-field>
                <vaadin-text-field id="lastName" label="Last Name"></vaadin-text-field>
                <vaadin-text-field id="email" label="Email"></vaadin-text-field>
                <vaadin-text-field id="phone" label="Phone"></vaadin-text-field>
                <vaadin-button id="submit">Submit</vaadin-button>
            </vaadin-form-layout>
        `;
    }
}
