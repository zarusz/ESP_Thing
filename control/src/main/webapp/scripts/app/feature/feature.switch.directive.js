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
        var FeatureSwitchDirective = (function (_super) {
            __extends(FeatureSwitchDirective, _super);
            function FeatureSwitchDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.switch.html";
            }
            FeatureSwitchDirective.prototype.onLink = function (scope, element, attributes) {
                scope.$watch(function (s) { return s.feature.state.on; }, function () { return scope.notifyStateChanged(); });
            };
            FeatureSwitchDirective.$name = "featureSwitch";
            FeatureSwitchDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureSwitchDirective;
        })(App.BaseDirective);
        Feature.FeatureSwitchDirective = FeatureSwitchDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.switch.directive.js.map