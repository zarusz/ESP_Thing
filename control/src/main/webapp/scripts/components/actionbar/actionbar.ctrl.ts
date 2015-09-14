///<reference path="..\..\app\app.module.ts"/>
module App.Component {

    export class ActionBarCtrl {
        static $name = "ActionBarCtrl";
        static $inject = ["$scope", "$mdSidenav", "$mdUtil", "$log"];

        constructor(private $scope,
                    private $mdSidenav:ng.material.ISidenavService,
                    private $mdUtil,
                    private $log:ng.ILogService) {

            $scope.toggleLeft = this.buildToggler('left');
        }

        private buildToggler(navID:string) {
            var debounceFn = this.$mdUtil.debounce(() => {
                this.$mdSidenav(navID)
                    .toggle()
                    .then(function () {
                        this.$log.debug("toggle " + navID + " is done");
                    });
            }, 200);
            return debounceFn;
        }
    }

    $module.controller(ActionBarCtrl.$name, ActionBarCtrl);
}