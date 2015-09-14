///<reference path="..\..\app\app.module.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var ActionBarCtrl = (function () {
            function ActionBarCtrl($mdSidenav, $mdUtil) {
                this.$mdSidenav = $mdSidenav;
                this.$mdUtil = $mdUtil;
                //var _this = this;
                //this.toggleSideNav = this.$mdUtil.debounce(() => {
                //    _this.getSideNav().toggle();
                //}, 200);
            }
            ActionBarCtrl.prototype.getSideNav = function () {
                return this.$mdSidenav("left");
            };
            ActionBarCtrl.prototype.toggleSideNav = function () {
                this.getSideNav().toggle();
            };
            ActionBarCtrl.prototype.closeSideNav = function () {
                this.getSideNav().close();
            };
            ActionBarCtrl.$inject = ["$mdSidenav", "$mdUtil"];
            return ActionBarCtrl;
        })();
        Component.ActionBarCtrl = ActionBarCtrl;
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map