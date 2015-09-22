/// <reference path="../../typings/angularjs/angular.d.ts"/>
/// <reference path="../../typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="../../typings/angular-material/angular-material.d.ts" />
/// <reference path="../../typings/underscore/underscore.d.ts" />
module App {

    export class NgSvc {
        // Angular

        static rootScope = "$rootScope";
        static scope = "$scope";
        static http = "$http";
        static $q = "$q";

        // Angular UI Router

        static state = "$state";
        static stateParams = "$stateParams";
    }
}