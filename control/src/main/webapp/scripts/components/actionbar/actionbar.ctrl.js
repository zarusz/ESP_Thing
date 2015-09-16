///<reference path="..\..\app\app.module.ts"/>
///<reference path="..\..\components\partition\partition.service.ts"/>
///<reference path="..\auth\principal.service.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var ActionBarCtrl = (function () {
            function ActionBarCtrl($mdSidenav, $state, partitionService, principal) {
                var _this = this;
                this.$mdSidenav = $mdSidenav;
                this.$state = $state;
                this.partitionService = partitionService;
                this.principal = principal;
                if (!this.principal.isAuthenticated()) {
                    //auth.authorize();
                    $state.go("login");
                }
                else {
                    partitionService.loadRoot().then(function (r) {
                        _this.rootPartition = r.data;
                    });
                }
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
            ActionBarCtrl.$inject = ["$mdSidenav", App.NgSvc.state, Component.Partition.PartitionService.$name, App.Auth.Principal.$name];
            return ActionBarCtrl;
        })();
        Component.ActionBarCtrl = ActionBarCtrl;
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map