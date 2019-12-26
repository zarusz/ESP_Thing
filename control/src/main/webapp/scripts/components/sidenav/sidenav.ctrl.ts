///<reference path="..\..\app\app.module.ts"/>
module App.Component {

    export class SideNavCtrl {
        static $name = "SideNavCtrl";
        static $inject = [NgSvc.scope, NgSvc.timeout, "$mdSidenav", "$log"];

        constructor($scope, $timeout: ng.ITimeoutService, $mdSidenav: ng.material.ISidenavService, $log: ng.ILogService) {
            $scope.close = function () {
                $mdSidenav('left').close()
                    .then(() => {
                        $log.debug("close LEFT is done");
                    });
            };
        }
    }

    //$module.controller(SideNavCtrl.$name, SideNavCtrl);

}
