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
        NgSvc.q = "$q";
        NgSvc.timeout = "$timeout";
        // Angular UI Router
        NgSvc.state = "$state";
        NgSvc.stateParams = "$stateParams";
        return NgSvc;
    }());
    App.NgSvc = NgSvc;
    var NgEvent = (function () {
        function NgEvent() {
        }
        NgEvent.destroy = "$destroy";
        return NgEvent;
    }());
    App.NgEvent = NgEvent;
    var BaseDirective = (function () {
        function BaseDirective() {
            var _this = this;
            this.linkScope = null;
            this.link = function (scope, element, attributes) {
                _this.linkScope = scope;
                _this.linkScope.$on(NgEvent.destroy, function () { return _this.destroy(); });
                _this.onLink(scope, element, attributes);
            };
        }
        BaseDirective.prototype.destroy = function () {
        };
        BaseDirective.prototype.onLink = function (scope, element, attributes) {
        };
        return BaseDirective;
    }());
    App.BaseDirective = BaseDirective;
    var NgUtils = (function () {
        function NgUtils() {
        }
        return NgUtils;
    }());
    App.NgUtils = NgUtils;
})(App || (App = {}));
//# sourceMappingURL=common.ng.js.map