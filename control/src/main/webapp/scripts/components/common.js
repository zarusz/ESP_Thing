var App;
(function (App) {
    var NgSvc = (function () {
        function NgSvc() {
        }
        NgSvc.rootScope = "$rootScope";
        NgSvc.scope = "$scope";
        NgSvc.http = "$http";
        NgSvc.state = "$state";
        NgSvc.$q = "$q";
        return NgSvc;
    })();
    App.NgSvc = NgSvc;
})(App || (App = {}));
//# sourceMappingURL=common.js.map