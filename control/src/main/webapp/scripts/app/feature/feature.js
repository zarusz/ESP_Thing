///<reference path="..\app.module.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.led.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        App.$module.directive(Feature.FeatureDirective.$name, [function () { return new Feature.FeatureDirective(); }]);
        App.$module.directive(Feature.FeatureSwitchDirective.$name, [function () { return new Feature.FeatureSwitchDirective(); }]);
        App.$module.directive(Feature.FeatureDimDirective.$name, [function () { return new Feature.FeatureDimDirective(); }]);
        App.$module.directive(Feature.FeatureLedDirective.$name, [function () { return new Feature.FeatureLedDirective(); }]);
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.js.map