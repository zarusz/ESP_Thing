///<reference path="..\..\components\repository\repository.module.ts"/>
///<reference path="..\..\components\auth\principal.service.ts"/>
///<reference path="..\..\components\auth\auth.service.ts"/>
///<reference path="..\..\app\partition\partition.ts"/>
module App {

    export class ActionBarCtrl implements Auth.IPrincipalChangedEventHandler {
        static $inject = ["$mdSidenav", NgSvc.state, NgSvc.scope, Repository.PartitionService.$name, Auth.Principal.$name, Auth.Authenticator.$name, NgSvc.window, EventBus.$name];

        rootPartition: Repository.PartitionModel;

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private state: ng.ui.IStateService,
                    private scope: ng.IScope,
                    private partitionService: Repository.PartitionService,
                    private principal: Auth.Principal,
                    private auth: Auth.Authenticator,
                    private window: ng.IWindowService,
                    private eventBus: IEventBus) {

            this.loadData();
            //var _this = this;
            //this.toggleSideNav = this.$mdUtil.debounce(() => {
            //    _this.getSideNav().toggle();
            //}, 200);

            eventBus.subscribe(Auth.PrincipalChangedEvent.event, this);
            scope.$on(NgEvent.destroy, () => eventBus.unsubscribe(Auth.PrincipalChangedEvent.event, this));
        }

        private loadData() {
            if (this.principal.isAuthenticated()) {
                this.principal.identity(false).then(() => {
                    this.partitionService.loadRoot().then(r => {
                        this.rootPartition = r.data;
                    });
                });
            }
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

        navigateToPartition(partition: Repository.PartitionModel) {
            this.closeSideNav();
            var params = new Partition.PartitionParams(partition.id);
            this.state.go("partition", params);
        }

        reload() {
            this.window.location.reload(true);
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
