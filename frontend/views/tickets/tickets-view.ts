import '@vaadin/vaadin-button';
import '@vaadin/vaadin-date-picker';
import '@vaadin/vaadin-date-time-picker';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-grid/vaadin-grid-column';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-split-layout';
import '@vaadin/vaadin-text-field';
import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';

@customElement('tickets-view')
export class TicketsView extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
            <vaadin-split-layout>
                <div class="grid-wrapper">
                    <vaadin-grid id="grid"></vaadin-grid>
                </div>
                <div class="editor-layout">
                    <div class="editor">
                        <vaadin-form-layout>
                            <vaadin-text-field label="Subject" id="subject"></vaadin-text-field>
                            <label>Attachment</label>
                            <vaadin-upload accept="image/*" max-files="1" style="box-sizing: border-box" id="attachment"
                            ><img id="attachmentPreview" class="w-full"/></vaadin-upload>
                            <vaadin-text-field label="Content" id="content"></vaadin-text-field
                            >
                            <vaadin-text-field label="Created by" id="createdBy"></vaadin-text-field
                            >
                            <vaadin-text-field label="Updated by" id="updatedBy"></vaadin-text-field
                            >
                            <vaadin-date-time-picker label="Created at" id="createdAt"
                                                     step="1"></vaadin-date-time-picker
                            >
                            <vaadin-date-time-picker label="Updated at" id="updatedAt"
                                                     step="1"></vaadin-date-time-picker
                            >
                            <vaadin-text-field label="Status" id="status"></vaadin-text-field
                            >
                            <vaadin-checkbox id="isDeleted">Is deleted</vaadin-checkbox>
                        </vaadin-form-layout>
                    </div>
                    <vaadin-horizontal-layout class="button-layout">
                        <vaadin-button theme="primary" id="save">Save</vaadin-button>
                        <vaadin-button theme="tertiary" slot="" id="cancel">Cancel</vaadin-button>
                    </vaadin-horizontal-layout>
                </div>
            </vaadin-split-layout>`;
    }
}
