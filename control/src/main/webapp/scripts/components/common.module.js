/// <reference path="common.ng.ts"/>
/// <reference path="common.eventbus.ts"/>
"use strict";
var App;
(function (App) {
    var Common;
    (function (Common) {
        Common.$module = angular.module("Common", []);
        Common.$module.service(App.EventBus.$name, App.EventBus);
    })(Common = App.Common || (App.Common = {}));
})(App || (App = {}));
//# sourceMappingURL=common.module.js.map