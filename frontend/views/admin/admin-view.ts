import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/split-layout';
import './master-content';
import './detail-content';

@customElement('admin-view')
export class AdminView extends LitElement {

    protected createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
            <vaadin-split-layout style="max-height: 350px;" orientation="vertical">
                <master-content>
                    
                </master-content>
                <detail-content>
                    
                </detail-content>
            </vaadin-split-layout>
        `;
    }
}
