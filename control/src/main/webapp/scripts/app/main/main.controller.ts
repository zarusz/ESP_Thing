///<reference path="..\app.module.ts"/>
'use strict';

module App {

    export class MainCtrl {
        static $inject = ["$scope", "Principal"];

        constructor($scope: any, principal) {
            principal.identity().then((account) => {
                $scope.account = account;
                $scope.isAuthenticated = principal.isAuthenticated;
            });
        }
    }

}