import type { ClientOptions } from 'rsocket-websocket-client'
import type { ConnectorConfig } from 'rsocket-core'

import { WebsocketClientTransport } from 'rsocket-websocket-client'
import { encodeRoute, WellKnownMimeType } from 'rsocket-composite-metadata'
import { RSocketConnector } from 'rsocket-core'
import { WebSocket as wb } from 'ws'

type Options = {
    host: string | URL;
    port: string | number;
}

const createClient = async (options: Options) => {

    const transportOptions: ClientOptions = {
        url: `ws://${options.host}:${options.port}/rsocket`,
        // In non-browser environments we need to provide a
        // factory method which can return an instance of a
        // websocket object. Browsers however, have this
        // functionality built-in.
        wsCreator: (url: string) => new wb(url) as unknown as WebSocket
    };

    const setupOptions: ConnectorConfig['setup'] = {
        keepAlive: 1000000,
        lifetime: 100000,
        dataMimeType: WellKnownMimeType.APPLICATION_JSON.string,
        metadataMimeType: WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.string,
    };

    const transport = new WebsocketClientTransport(transportOptions);
    const client = new RSocketConnector({ setup: setupOptions, transport });

    return await client.connect();
}

const run = async () => {
    const rsocket = await createClient({
        host: '0.0.0.0',
        port: 8000,
    });

    // fire and forget

    rsocket.fireAndForget({
        data: null, 
        metadata: encodeRoute('createTicket')
    }, {
        onComplete: () => {
            console.log("fire and forget complete!");
        },
        onError: (e) => {
            console.log("fire and forget error: ", e);
        }
    });

    // request - response 

    rsocket.requestResponse({
        data: Buffer.from("1"), 
        metadata: encodeRoute('ticketById')
    }, {
        onComplete: () => {
            console.log("request - response complete");
        },
        onError: (e) => {
            console.log("request - response error: ", e);
        },
        onExtension: () => {
            console.log("request - response onExtension");
        },
        onNext: (p) => {
            console.log("Recieved from request - response: ", p.data?.toString());
        },
    });

    // channel 

    const channel = rsocket.requestChannel({
        data: Buffer.from("1"), 
        metadata: encodeRoute('pingpong')
    }, 10, false, {
        onComplete: () => {
            console.log("Channel complete");
        },
        onError: (e) => {
            console.log("Channel error: ", e);
        },
        onExtension: () => {
            console.log("Channel onExtension");
        },
        onNext: (p) => {
            console.log("Recieved from channel: ", p.data?.toString());
        },
        cancel: () => {
            console.log("Channel cancel");
        },
        request: () => {
            console.log("Channel request");
        }
    });

    for(let i = 2; i < 10; i++) {
        channel.onNext({data: Buffer.from(i.toString())}, false);
    }

    channel.onNext({data: null}, true);

    // request stream

    rsocket.requestStream({ 
        data: null,
        metadata: encodeRoute('allTickets')
    }, 10, {
        onComplete: () => {
            console.log("Request stream complete.");
        },
        onError: (e) => {
            console.log("Request stream error: ", e);
        },
        onExtension: () => {
            console.log("Request stream onExtension");
        },
        onNext: (p) => {
            console.log("Recieved from request stream: ", p.data?.toString());
        },
    });

}

run();
