/// <reference path="../../components/common.module.ts" />
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.remote.directive.ts"/>
///<reference path="feature.sensor.temperature.directive.ts"/>
///<reference path="feature.sensor.humidity.directive.ts"/>
///<reference path="feature.service.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        Feature.$module = angular.module("Feature", [
            App.Common.$module.name
        ]);
        Feature.$module.directive(Feature.FeatureDirective.$name, [
            App.Repository.DeviceService.$name, App.NgSvc.timeout, App.EventBus.$name,
            function (deviceService, timeout, eventBus) { return new Feature.FeatureDirective(deviceService, timeout, eventBus); }
        ]);
        Feature.$module.directive(Feature.FeatureSwitchDirective.$name, [
            App.Repository.DeviceService.$name, App.EventBus.$name,
            function (deviceService, eventBus) { return new Feature.FeatureSwitchDirective(deviceService, eventBus); }
        ]);
        Feature.$module.directive(Feature.FeatureDimDirective.$name, [
            App.Repository.DeviceService.$name,
            function (deviceService) { return new Feature.FeatureDimDirective(deviceService); }
        ]);
        Feature.$module.directive(Feature.FeatureRemoteDirective.$name, [
            App.Repository.DeviceService.$name,
            function (deviceService) { return new Feature.FeatureRemoteDirective(deviceService); }
        ]);
        Feature.$module.directive(Feature.FeatureSensorTemperatureDirective.$name, [
            App.Repository.DeviceService.$name, App.EventBus.$name,
            function (deviceService, eventBus) { return new Feature.FeatureSensorTemperatureDirective(deviceService, eventBus); }
        ]);
        Feature.$module.directive(Feature.FeatureSensorHumidityDirective.$name, [
            App.Repository.DeviceService.$name,
            function (deviceService) { return new Feature.FeatureSensorHumidityDirective(deviceService); }
        ]);
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.js.map