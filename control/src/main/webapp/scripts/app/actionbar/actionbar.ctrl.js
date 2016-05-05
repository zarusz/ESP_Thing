///<reference path="..\..\components\repository\repository.module.ts"/>
///<reference path="..\..\components\auth\principal.service.ts"/>
///<reference path="..\..\app\partition\partition.ts"/>
var App;
(function (App) {
    var ActionBarCtrl = (function () {
        function ActionBarCtrl($mdSidenav, $state, partitionService, principal) {
            var _this = this;
            this.$mdSidenav = $mdSidenav;
            this.$state = $state;
            this.partitionService = partitionService;
            this.principal = principal;
            this.principal.identity(true).then(function () {
                partitionService.loadRoot().then(function (r) {
                    _this.rootPartition = r.data;
                });
            });
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
        ActionBarCtrl.prototype.navigateToPartition = function (partition) {
            this.closeSideNav();
            var params = new App.Partition.PartitionParams(partition.id);
            this.$state.go("partition", params);
        };
        ActionBarCtrl.$inject = ["$mdSidenav", App.NgSvc.state, App.Repository.PartitionService.$name, App.Auth.Principal.$name];
        return ActionBarCtrl;
    }());
    App.ActionBarCtrl = ActionBarCtrl;
})(App || (App = {}));
//# sourceMappingURL=actionbar.ctrl.js.map