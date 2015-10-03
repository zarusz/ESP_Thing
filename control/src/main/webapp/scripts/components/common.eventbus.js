"use strict";
var App;
(function (App) {
    var EventBus = (function () {
        function EventBus() {
            this.subscriptions = {};
        }
        EventBus.prototype.publish = function (event) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (eventHandlers) {
                eventHandlers.forEach(function (handler) { return event.handle(handler); });
            }
        };
        EventBus.prototype.subscribe = function (event, handler) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (!eventHandlers) {
                eventHandlers = new Array();
                this.subscriptions[eventId] = eventHandlers;
            }
            if (eventHandlers.indexOf(handler) === -1) {
                eventHandlers.push(handler);
            }
        };
        EventBus.prototype.unsubscribe = function (event, handler) {
            var eventId = event.getId();
            var eventHandlers = this.subscriptions[eventId];
            if (eventHandlers) {
                var i = eventHandlers.indexOf(handler);
                if (i !== -1) {
                    eventHandlers.splice(i);
                }
            }
        };
        EventBus.$name = "EventBus";
        EventBus.$inject = [];
        return EventBus;
    })();
    App.EventBus = EventBus;
})(App || (App = {}));
//# sourceMappingURL=common.eventbus.js.map