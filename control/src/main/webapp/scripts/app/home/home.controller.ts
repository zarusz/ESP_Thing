/// <reference path="../../components/common.ng.ts" />
/// <reference path="../../components/auth/auth.service.ts" />
"use strict";

module App.Home {

    export class HomeCtrl {
        static $inject = [NgSvc.scope, App.Auth.Principal.$name];

        constructor(scope: any, principal: App.Auth.Principal) {
            principal.identity().then((account) => {
                scope.account = account;
                scope.isAuthenticated = principal.isAuthenticated;
            });
        }
    }

}
