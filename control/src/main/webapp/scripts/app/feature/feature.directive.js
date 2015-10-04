var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureDirective = (function (_super) {
            __extends(FeatureDirective, _super);
            function FeatureDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
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
                    _this.deviceService.updateFeatureState(scope.device, scope.feature);
                };
            };
            FeatureDirective.$name = "feature";
            return FeatureDirective;
        })(App.BaseDirective);
        Feature.FeatureDirective = FeatureDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.directive.js.map