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
        var FeatureDimDirective = (function (_super) {
            __extends(FeatureDimDirective, _super);
            function FeatureDimDirective() {
                _super.apply(this, arguments);
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.dim.html";
            }
            FeatureDimDirective.prototype.onLink = function (scope, element, attributes) {
            };
            FeatureDimDirective.$name = "featureDim";
            return FeatureDimDirective;
        })(App.BaseDirective);
        Feature.FeatureDimDirective = FeatureDimDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.dim.directive.js.map