///<reference path="..\..\app\app.module.ts"/>
module App.Component {

    export class ActionBarCtrl {
        static $inject = ["$mdSidenav", "$mdUtil"];

        constructor(private $mdSidenav: ng.material.ISidenavService,
                    private $mdUtil) {

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
    }

}