///<reference path="..\..\app\app.module.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var ActionBarCtrl = (function () {
            function ActionBarCtrl($mdSidenav, $mdUtil, $state) {
                this.$mdSidenav = $mdSidenav;
                this.$mdUtil = $mdUtil;
                this.$state = $state;
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
            ActionBarCtrl.prototype.navigateTo = function (state) {
                this.closeSideNav();
                this.$state.go(state);
            };
            ActionBarCtrl.$inject = ["$mdSidenav", "$mdUtil", "$state"];
            return ActionBarCtrl;
        })();
        Component.ActionBarCtrl = ActionBarCtrl;
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map