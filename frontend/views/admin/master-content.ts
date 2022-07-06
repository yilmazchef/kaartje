import {html, LitElement} from 'lit';
import {customElement} from 'lit/decorators.js';
import '@vaadin/vertical-layout';

@customElement('master-content')
export class MasterContent extends LitElement {

    protected createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
           <vaadin-vertical-layout>
               MASTER CONTENT
           </vaadin-vertical-layout>
        `;
    }
}
