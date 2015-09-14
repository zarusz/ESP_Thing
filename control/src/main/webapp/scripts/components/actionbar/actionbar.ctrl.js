///<reference path="..\..\app\app.module.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var ActionBarCtrl = (function () {
            function ActionBarCtrl($scope, $mdSidenav, $mdUtil, $log) {
                this.$scope = $scope;
                this.$mdSidenav = $mdSidenav;
                this.$mdUtil = $mdUtil;
                this.$log = $log;
                $scope.toggleLeft = this.buildToggler('left');
            }
            ActionBarCtrl.prototype.buildToggler = function (navID) {
                var _this = this;
                var debounceFn = this.$mdUtil.debounce(function () {
                    _this.$mdSidenav(navID).toggle().then(function () {
                        this.$log.debug("toggle " + navID + " is done");
                    });
                }, 200);
                return debounceFn;
            };
            ActionBarCtrl.$name = "ActionBarCtrl";
            ActionBarCtrl.$inject = ["$scope", "$mdSidenav", "$mdUtil", "$log"];
            return ActionBarCtrl;
        })();
        Component.ActionBarCtrl = ActionBarCtrl;
        App.$module.controller(ActionBarCtrl.$name, ActionBarCtrl);
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map