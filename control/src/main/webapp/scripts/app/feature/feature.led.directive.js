var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
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
            function FeatureLedDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.led.html";
            }
            FeatureLedDirective.prototype.onLink = function (scope, element, attributes) {
                scope.select = function (newMode) {
                    scope.feature.state.mode = newMode;
                    scope.notifyStateChanged();
                };
            };
            FeatureLedDirective.$name = "featureLed";
            FeatureLedDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureLedDirective;
        }(App.BaseDirective));
        Feature.FeatureLedDirective = FeatureLedDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.led.directive.js.map