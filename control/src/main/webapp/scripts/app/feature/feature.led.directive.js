var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureLedDirective = (function (_super) {
            __extends(FeatureLedDirective, _super);
            function FeatureLedDirective() {
                _super.apply(this, arguments);
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.led.html";
            }
            FeatureLedDirective.prototype.onLink = function (scope, element, attributes) {
            };
            FeatureLedDirective.$name = "featureLed";
            return FeatureLedDirective;
        })(App.BaseDirective);
        Feature.FeatureLedDirective = FeatureLedDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.led.directive.js.map