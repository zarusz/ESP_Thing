///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureSensorTemperatureDirective = (function () {
            function FeatureSensorTemperatureDirective(deviceService, eventBus) {
                this.deviceService = deviceService;
                this.eventBus = eventBus;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.sensor.temperature.html";
                this.replace = true;
                this.link = function (scope, element, attributes) {
                };
            }
            FeatureSensorTemperatureDirective.$name = "featureSensorTemperature";
            FeatureSensorTemperatureDirective.$inject = [
                App.Repository.DeviceService.$name,
                App.EventBus.$name
            ];
            return FeatureSensorTemperatureDirective;
        }());
        Feature.FeatureSensorTemperatureDirective = FeatureSensorTemperatureDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.sensor.temperature.directive.js.map