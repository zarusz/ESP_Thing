'use strict';

module App.Home {

    export class HomeCtrl {
        static $inject = ["$scope", "Principal"];

        constructor($scope: any, principal) {
            principal.identity().then((account) => {
                $scope.account = account;
                $scope.isAuthenticated = principal.isAuthenticated;
            });
        }
    }

}
