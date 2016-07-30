/// <reference path="../../typings/globals/angular/index.d.ts" />
/// <reference path="../../typings/globals/angular-ui-router/index.d.ts" />
/// <reference path="../../typings/globals/angular-local-storage/index.d.ts" />
/// <reference path="../../typings/globals/angular-material/index.d.ts" />
/// <reference path="../../typings/globals/underscore/index.d.ts" />
/// <reference path="../../typings/globals/jquery/index.d.ts" />
"use strict";
var App;
(function (App) {
    var NgSvc = (function () {
        function NgSvc() {
        }
        // Angular
        NgSvc.rootScope = "$rootScope";
        NgSvc.scope = "$scope";
        NgSvc.http = "$http";
        NgSvc.cookies = "$cookies";
        NgSvc.q = "$q";
        NgSvc.timeout = "$timeout";
        NgSvc.window = "$window";
        // Angular UI Router
        NgSvc.state = "$state";
        NgSvc.stateParams = "$stateParams";
        //
        NgSvc.localStorageService = "localStorageService";
        NgSvc.translate = "$translate";
        NgSvc.translatePartialLoader = "$translatePartialLoader";
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