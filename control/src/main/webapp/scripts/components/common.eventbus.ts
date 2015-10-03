"use strict";
module App {

    export interface IEvent<TEventHandler> {
        getId(): string;
        handle(handler: TEventHandler);
    }

    export interface IEventBus {
        publish<TEventHandler>(event: IEvent<TEventHandler>);
        subscribe<TEventHandler>(event: IEvent<TEventHandler>, handler: TEventHandler);
        unsubscribe<TEventHandler>(event: IEvent<TEventHandler>, handler: TEventHandler);
    }

    export class EventBus implements IEventBus {
        static $name = "EventBus";
        static $inject = [];

        private subscriptions: { [eventId: string]: Array<Object> } = {};

        publish<TEventHandler>(event: IEvent<TEventHandler>) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (eventHandlers) {
                eventHandlers.forEach((handler: TEventHandler) => event.handle(handler));
            }
        }

        subscribe<TEventHandler>(event: App.IEvent<TEventHandler>, handler: TEventHandler) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (!eventHandlers) {
                eventHandlers = new Array<Object>();
                this.subscriptions[eventId] = eventHandlers;
            }

            if (eventHandlers.indexOf(handler) === -1) {
                eventHandlers.push(handler);
            }
        }

        unsubscribe<TEventHandler>(event: App.IEvent<TEventHandler>, handler: TEventHandler) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (eventHandlers) {
                var i = eventHandlers.indexOf(handler);
                if (i !== -1) {
                    eventHandlers.splice(i);
                }
            }
        }

    }
}
