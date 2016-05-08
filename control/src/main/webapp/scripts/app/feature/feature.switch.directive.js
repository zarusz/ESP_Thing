///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.service.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureSwitchDirective = (function () {
            function FeatureSwitchDirective(deviceService, eventBus) {
                this.deviceService = deviceService;
                this.eventBus = eventBus;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.switch.html";
                this.replace = true;
                this.link = function (scope, element, attributes) {
                };
            }
            FeatureSwitchDirective.$name = "featureSwitch";
            FeatureSwitchDirective.$inject = [
                App.Repository.DeviceService.$name,
                App.EventBus.$name
            ];
            return FeatureSwitchDirective;
        }());
        Feature.FeatureSwitchDirective = FeatureSwitchDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.switch.directive.js.map