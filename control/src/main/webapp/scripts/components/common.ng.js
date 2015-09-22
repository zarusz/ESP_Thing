/// <reference path="../../typings/angularjs/angular.d.ts"/>
/// <reference path="../../typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="../../typings/angular-material/angular-material.d.ts" />
/// <reference path="../../typings/underscore/underscore.d.ts" />
var App;
(function (App) {
    var NgSvc = (function () {
        function NgSvc() {
        }
        // Angular
        NgSvc.rootScope = "$rootScope";
        NgSvc.scope = "$scope";
        NgSvc.http = "$http";
        NgSvc.$q = "$q";
        // Angular UI Router
        NgSvc.state = "$state";
        NgSvc.stateParams = "$stateParams";
        return NgSvc;
    })();
    App.NgSvc = NgSvc;
})(App || (App = {}));
//# sourceMappingURL=common.ng.js.map