///<reference path="..\..\app\app.module.ts"/>
///<reference path="..\..\components\partition\partition.service.ts"/>
///<reference path="..\auth\principal.service.ts"/>
module App.Component {

    export class ActionBarCtrl {
        static $inject = ["$mdSidenav", NgSvc.state, Partition.PartitionService.$name, Auth.Principal.$name];

        rootPartition: Partition.Partition;

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private $state: ng.ui.IStateService,
                    private partitionService: Partition.PartitionService,
                    private principal: Auth.Principal) {

            if (!this.principal.isAuthenticated()) {

                //auth.authorize();
                $state.go("login");
            } else {
                partitionService.loadRoot().then(r => {
                    this.rootPartition = r.data;
                });
            }

            //var _this = this;
            //this.toggleSideNav = this.$mdUtil.debounce(() => {
            //    _this.getSideNav().toggle();
            //}, 200);
        }

        private getSideNav() {
            return this.$mdSidenav("left");
        }

        toggleSideNav() {
            this.getSideNav().toggle();
        }

        closeSideNav() {
            this.getSideNav().close();
        }

        navigateTo(state: string) {
            this.closeSideNav();
            this.$state.go(state);
        }
    }

}