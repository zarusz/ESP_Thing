var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureDirective = (function (_super) {
            __extends(FeatureDirective, _super);
            function FeatureDirective(deviceService, timeout) {
                _super.call(this);
                this.deviceService = deviceService;
                this.timeout = timeout;
                this.restrict = "E";
                this.transclude = true;
                this.scope = {};
                this.templateUrl = "scripts/app/feature/feature.html";
            }
            FeatureDirective.prototype.onLink = function (scope, element, attributes) {
                var _this = this;
                scope.$parent.$watch(attributes.feature, function (newValue) {
                    scope.feature = newValue;
                });
                scope.$parent.$watch(attributes.device, function (newValue) {
                    scope.device = newValue;
                });
                scope.notifyStateChanged = function () {
                    if (scope.timeoutHandle) {
                        _this.timeout.cancel(scope.timeoutHandle);
                    }
                    scope.timeoutHandle = _this.timeout(function () {
                        scope.timeoutHandle = null;
                        _this.deviceService.updateFeatureState(scope.device, scope.feature);
                    }, 300);
                };
            };
            FeatureDirective.$name = "feature";
            return FeatureDirective;
        }(App.BaseDirective));
        Feature.FeatureDirective = FeatureDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.directive.js.map