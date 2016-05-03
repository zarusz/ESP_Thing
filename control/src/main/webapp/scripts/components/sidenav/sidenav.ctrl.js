///<reference path="..\..\app\app.module.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var SideNavCtrl = (function () {
            function SideNavCtrl($scope, $timeout, $mdSidenav, $log) {
                $scope.close = function () {
                    $mdSidenav('left').close()
                        .then(function () {
                        $log.debug("close LEFT is done");
                    });
                };
            }
            SideNavCtrl.$name = "SideNavCtrl";
            SideNavCtrl.$inject = ["$scope", "$timeout", "$mdSidenav", "$log"];
            return SideNavCtrl;
        }());
        Component.SideNavCtrl = SideNavCtrl;
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=sidenav.ctrl.js.map