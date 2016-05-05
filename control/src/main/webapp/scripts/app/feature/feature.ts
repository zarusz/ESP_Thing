/// <reference path="../../components/common.ng.ts" />
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.remote.directive.ts"/>
///<reference path="feature.sensor.temperature.directive.ts"/>
///<reference path="feature.sensor.humidity.directive.ts"/>

module App.Feature {

    export var $module = angular.module("Feature", []);
    $module.directive(FeatureDirective.$name, [Repository.DeviceService.$name, NgSvc.timeout, (deviceService, timeout) => new FeatureDirective(deviceService, timeout)]);
    $module.directive(FeatureSwitchDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureSwitchDirective(deviceService)]);
    $module.directive(FeatureDimDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureDimDirective(deviceService)]);
    $module.directive(FeatureRemoteDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureRemoteDirective(deviceService)]);
    $module.directive(FeatureSensorTemperatureDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureSensorTemperatureDirective(deviceService)]);
    $module.directive(FeatureSensorHumidityDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureSensorHumidityDirective(deviceService)]);

}
