///<reference path="..\app.module.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.led.directive.ts"/>
module App.Feature {

    $module.directive(FeatureDirective.$name, [Repository.DeviceService.$name, NgSvc.timeout, (deviceService, timeout) => new FeatureDirective(deviceService, timeout)]);
    $module.directive(FeatureSwitchDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureSwitchDirective(deviceService)]);
    $module.directive(FeatureDimDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureDimDirective(deviceService)]);
    $module.directive(FeatureLedDirective.$name, [Repository.DeviceService.$name, (deviceService) => new FeatureLedDirective(deviceService)]);

}