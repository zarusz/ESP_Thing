/// <reference path="../../typings/angularjs/angular.d.ts" />
/// <reference path="../../typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="../../typings/angular-material/angular-material.d.ts" />
"use strict";

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
        "infinite-scroll"]);

}
