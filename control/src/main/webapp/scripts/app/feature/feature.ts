/// <reference path="../../components/common.module.ts" />
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.remote.directive.ts"/>
///<reference path="feature.sensor.temperature.directive.ts"/>
///<reference path="feature.sensor.humidity.directive.ts"/>
///<reference path="feature.service.ts"/>

module App.Feature {

    export var $module = angular.module("Feature", [
        Common.$module.name
    ]);

    $module.directive(FeatureDirective.$name, [
        Repository.DeviceService.$name, NgSvc.timeout, EventBus.$name,
        (deviceService, timeout, eventBus) => new FeatureDirective(deviceService, timeout, eventBus)
    ]);

    $module.directive(FeatureSwitchDirective.$name, [
        Repository.DeviceService.$name, EventBus.$name,
        (deviceService, eventBus) => new FeatureSwitchDirective(deviceService, eventBus)
    ]);

    $module.directive(FeatureRemoteDirective.$name, [
        Repository.DeviceService.$name,
        (deviceService) => new FeatureRemoteDirective(deviceService)
    ]);

    $module.directive(FeatureSensorTemperatureDirective.$name, [
        Repository.DeviceService.$name, EventBus.$name,
        (deviceService, eventBus) => new FeatureSensorTemperatureDirective(deviceService, eventBus)
    ]);

    $module.directive(FeatureSensorHumidityDirective.$name, [
        Repository.DeviceService.$name,
        (deviceService) => new FeatureSensorHumidityDirective(deviceService)
    ]);
}
