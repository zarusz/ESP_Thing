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
        var FeatureSensorTemperatureDirective = (function (_super) {
            __extends(FeatureSensorTemperatureDirective, _super);
            function FeatureSensorTemperatureDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.sensor.temperature.html";
            }
            FeatureSensorTemperatureDirective.prototype.onLink = function (scope, element, attributes) {
                //scope.$watch((s: ISensorTemperatureFeatureScope) => s.feature.state.on, () => scope.notifyStateChanged());
            };
            FeatureSensorTemperatureDirective.$name = "featureSensorTemperature";
            FeatureSensorTemperatureDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureSensorTemperatureDirective;
        }(App.BaseDirective));
        Feature.FeatureSensorTemperatureDirective = FeatureSensorTemperatureDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.sensor.temperature.directive.js.map