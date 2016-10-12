///<reference path="..\..\components\repository\repository.module.ts"/>
///<reference path="..\..\components\auth\principal.service.ts"/>
///<reference path="..\..\components\auth\auth.service.ts"/>
///<reference path="..\..\app\partition\partition.ts"/>
///<reference path="..\..\app\admin\devices\devices.ts"/>
var App;
(function (App) {
    var ActionBarCtrl = (function () {
        function ActionBarCtrl($mdSidenav, state, scope, partitionService, principal, auth, window, eventBus) {
            var _this = this;
            this.$mdSidenav = $mdSidenav;
            this.state = state;
            this.scope = scope;
            this.partitionService = partitionService;
            this.principal = principal;
            this.auth = auth;
            this.window = window;
            this.eventBus = eventBus;
            this.loadData();
            eventBus.subscribe(App.Auth.PrincipalChangedEvent.event, this);
            scope.$on(App.NgEvent.destroy, function () { return eventBus.unsubscribe(App.Auth.PrincipalChangedEvent.event, _this); });
        }
        ActionBarCtrl.prototype.loadData = function () {
            var _this = this;
            this.partitionService.loadRoot().then(function (r) {
                _this.rootPartition = r;
            });
            /*
                        if (this.principal.isAuthenticated()) {
                            this.principal.identity(false).then(() => {
                            });
                        }
             */
        };
        ActionBarCtrl.prototype.onPrincipalChanged = function (e) {
            this.loadData();
        };
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
            this.state.go(state);
        };
        ActionBarCtrl.prototype.navigateToPartition = function (partition) {
            this.closeSideNav();
            var params = new App.Partition.PartitionParams(partition.id);
            this.state.go(App.Partition.States.Partition, params);
        };
        ActionBarCtrl.prototype.reload = function () {
            this.window.location.reload(true);
        };
        ActionBarCtrl.prototype.goAllDevices = function () {
            this.state.go(App.Admin.Devices.States.List);
        };
        ActionBarCtrl.prototype.logout = function () {
            this.closeSideNav();
            this.auth.logout();
            this.state.reload();
            //this.state.go("home");
        };
        ActionBarCtrl.prototype.isAuthenticated = function () {
            return this.principal.isAuthenticated();
        };
        ActionBarCtrl.$inject = ["$mdSidenav", App.NgSvc.state, App.NgSvc.scope, App.Repository.PartitionService.$name, App.Auth.Principal.$name, App.Auth.Authenticator.$name, App.NgSvc.window, App.EventBus.$name];
        ActionBarCtrl.$as = "actionBar";
        return ActionBarCtrl;
    }());
    App.ActionBarCtrl = ActionBarCtrl;
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map