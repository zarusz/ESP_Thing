var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureDimDirective = (function (_super) {
            __extends(FeatureDimDirective, _super);
            function FeatureDimDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.dim.html";
            }
            FeatureDimDirective.prototype.onLink = function (scope, element, attributes) {
                scope.$watch(function (s) { return s.feature.state.intensity; }, function () { return scope.notifyStateChanged(); });
            };
            FeatureDimDirective.$name = "featureDim";
            FeatureDimDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureDimDirective;
        }(App.BaseDirective));
        Feature.FeatureDimDirective = FeatureDimDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.dim.directive.js.map