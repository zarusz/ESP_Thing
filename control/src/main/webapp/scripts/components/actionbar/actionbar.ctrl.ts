///<reference path="..\..\app\app.module.ts"/>
module App.Component {

    export class ActionBarCtrl {
        static $inject = ["$mdSidenav", "$mdUtil", "$state"];

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private $mdUtil,
                    private $state: ng.ui.IStateService) {

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