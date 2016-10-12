///<reference path="..\..\components\repository\repository.module.ts"/>
///<reference path="..\..\components\auth\principal.service.ts"/>
///<reference path="..\..\components\auth\auth.service.ts"/>
///<reference path="..\..\app\partition\partition.ts"/>
///<reference path="..\..\app\admin\devices\devices.ts"/>
module App {

    export class ActionBarCtrl implements Auth.IPrincipalChangedEventHandler {
        static $inject = ["$mdSidenav", NgSvc.state, NgSvc.scope, Repository.PartitionService.$name, Auth.Principal.$name, Auth.Authenticator.$name, NgSvc.window, EventBus.$name];
        static $as = "actionBar";

        rootPartition: Repository.PartitionDto;

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private state: ng.ui.IStateService,
                    private scope: ng.IScope,
                    private partitionService: Repository.PartitionService,
                    private principal: Auth.Principal,
                    private auth: Auth.Authenticator,
                    private window: ng.IWindowService,
                    private eventBus: IEventBus) {

            this.loadData();

            eventBus.subscribe(Auth.PrincipalChangedEvent.event, this);
            scope.$on(NgEvent.destroy, () => eventBus.unsubscribe(Auth.PrincipalChangedEvent.event, this));
        }

        private loadData() {
            this.partitionService.loadRoot().then(r => {
                this.rootPartition = r;
            });
/*
            if (this.principal.isAuthenticated()) {
                this.principal.identity(false).then(() => {
                });
            }
 */
        }

        onPrincipalChanged(e: Auth.PrincipalChangedEvent) {
            this.loadData();
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
            this.state.go(state);
        }

        navigateToPartition(partition: Repository.PartitionDto) {
            this.closeSideNav();
            var params = new Partition.PartitionParams(partition.id);
            this.state.go(Partition.States.Partition, params);
        }

        reload() {
            this.window.location.reload(true);
        }

        goAllDevices() {
            this.state.go(App.Admin.Devices.States.List);
        }

        logout() {
            this.closeSideNav();
            this.auth.logout();
            this.state.reload();
            //this.state.go("home");
        }

        isAuthenticated() {
            return this.principal.isAuthenticated();
        }
    }

}
