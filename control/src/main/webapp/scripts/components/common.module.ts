/// <reference path="common.ng.ts"/>
/// <reference path="common.eventbus.ts"/>
"use strict";
module App.Common {

    export var $module = angular.module("Common", []);
    $module.service(EventBus.$name, EventBus);

}
