///<reference path="..\app.module.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.led.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        App.$module.directive(Feature.FeatureDirective.$name, [App.Repository.DeviceService.$name, function (deviceService) { return new Feature.FeatureDirective(deviceService); }]);
        App.$module.directive(Feature.FeatureSwitchDirective.$name, [App.Repository.DeviceService.$name, function (deviceService) { return new Feature.FeatureSwitchDirective(deviceService); }]);
        App.$module.directive(Feature.FeatureDimDirective.$name, [App.Repository.DeviceService.$name, function (deviceService) { return new Feature.FeatureDimDirective(deviceService); }]);
        App.$module.directive(Feature.FeatureLedDirective.$name, [App.Repository.DeviceService.$name, function (deviceService) { return new Feature.FeatureLedDirective(deviceService); }]);
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.js.map