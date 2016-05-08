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
        var FeatureSensorHumidityDirective = (function (_super) {
            __extends(FeatureSensorHumidityDirective, _super);
            function FeatureSensorHumidityDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.sensor.humidity.html";
                this.replace = true;
            }
            FeatureSensorHumidityDirective.prototype.onLink = function (scope, element, attributes) {
                //scope.$watch((s: ISensorTemperatureFeatureScope) => s.feature.state.on, () => scope.notifyStateChanged());
            };
            FeatureSensorHumidityDirective.$name = "featureSensorHumidity";
            FeatureSensorHumidityDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureSensorHumidityDirective;
        }(App.BaseDirective));
        Feature.FeatureSensorHumidityDirective = FeatureSensorHumidityDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.sensor.humidity.directive.js.map