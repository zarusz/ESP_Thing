///<reference path="..\..\app\app.module.ts"/>
///<reference path="..\..\components\repository\repository.module.ts"/>
///<reference path="..\auth\principal.service.ts"/>
///<reference path="..\..\app\partition\partition.ts"/>
module App.Component {

    export class ActionBarCtrl {
        static $inject = ["$mdSidenav", NgSvc.state, Repository.PartitionService.$name, Auth.Principal.$name];

        rootPartition: Repository.PartitionModel;

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private $state: ng.ui.IStateService,
                    private partitionService: Repository.PartitionService,
                    private principal: Auth.Principal) {

            if (!this.principal.isAuthenticated()) {

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

        navigateToPartition(partition: Repository.PartitionModel) {
            this.closeSideNav();
            var params = new PartitionParams(partition.id);
            this.$state.go("partition", params);
        }
    }

}