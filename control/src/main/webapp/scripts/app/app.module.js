/// <reference path="../components/common.ng.ts" />
/// <reference path="../components/repository/repository.module.ts" />
var App;
(function (App) {
    App.$module = angular.module("controlApp", [
        "LocalStorageModule",
        "tmh.dynamicLocale",
        "pascalprecht.translate",
        "ngAnimate",
        "ngMaterial",
        "ngResource",
        "ngCookies",
        "ngCacheBuster",
        "ui.bootstrap",
        "ui.router",
        "infinite-scroll",
        App.Repository.$module.name
    ]);
})(App || (App = {}));
//# sourceMappingURL=app.module.js.map