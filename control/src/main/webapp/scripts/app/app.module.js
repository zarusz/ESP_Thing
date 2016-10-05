/// <reference path="../components/common.module.ts" />
var App;
(function (App) {
    App.$module = angular.module("controlApp", [
        App.Common.$module.name,
        "LocalStorageModule",
        "tmh.dynamicLocale",
        "pascalprecht.translate",
        "ngAnimate",
        "ngMaterial",
        "ngMessages",
        "ngResource",
        "ngCookies",
        "ngCacheBuster",
        "ui.bootstrap",
        "ui.router",
        "infinite-scroll",
    ]);
})(App || (App = {}));
//# sourceMappingURL=app.module.js.map