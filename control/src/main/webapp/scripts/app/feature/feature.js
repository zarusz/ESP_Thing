///<reference path="..\app.module.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        App.$module.directive(Feature.FeatureDirective.$name, [function () { return new Feature.FeatureDirective(); }]);
        App.$module.directive(Feature.FeatureSwitchDirective.$name, [function () { return new Feature.FeatureSwitchDirective(); }]);
        App.$module.directive(Feature.FeatureDimDirective.$name, [function () { return new Feature.FeatureDimDirective(); }]);
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.js.map