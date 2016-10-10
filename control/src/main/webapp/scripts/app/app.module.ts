/// <reference path="../components/common.module.ts" />
module App {

    export var $module = angular.module("controlApp", [
        Common.$module.name,
        "LocalStorageModule",
        "tmh.dynamicLocale",
        "pascalprecht.translate",
        "ngAnimate",
        "ngMaterial",
        "ngMessages",
        "ngResource",
        "ngCookies",
        "ngCacheBuster",
        "ui.bootstrap", // for modal dialogs
        "ui.router"
    ]);

}
