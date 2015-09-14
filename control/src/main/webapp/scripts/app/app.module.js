/// <reference path="../../typings/angularjs/angular.d.ts" />
/// <reference path="../../typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="../../typings/angular-material/angular-material.d.ts" />
"use strict";
var App;
(function (App) {
    App.$module = angular.module("controlApp", [
        "LocalStorageModule",
        "tmh.dynamicLocale",
        "pascalprecht.translate",
        "ngAnimate",
        "ngMaterial",
        "ui.bootstrap",
        "ngResource",
        "ui.router",
        "ngCookies",
        "ngCacheBuster",
        "infinite-scroll"
    ]);
})(App || (App = {}));
//# sourceMappingURL=app.module.js.map