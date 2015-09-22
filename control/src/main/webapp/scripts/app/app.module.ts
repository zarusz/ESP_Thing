/// <reference path="../components/common.ng.ts" />
/// <reference path="../components/repository/repository.module.ts" />
module App {

    export var $module = angular.module("controlApp", [
        "LocalStorageModule",
        "tmh.dynamicLocale",
        "pascalprecht.translate",
        "ngAnimate",
        "ngMaterial",
        "ngResource",
        "ngCookies",
        "ngCacheBuster",
        "ui.bootstrap", // for modal dialogs
        "ui.router",
        "infinite-scroll",
        Repository.$module.name
    ]);

}
