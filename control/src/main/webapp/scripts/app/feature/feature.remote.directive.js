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
        var FeatureRemoteDirective = (function (_super) {
            __extends(FeatureRemoteDirective, _super);
            function FeatureRemoteDirective(deviceService) {
                _super.call(this);
                this.deviceService = deviceService;
                this.restrict = "EA";
                this.templateUrl = "scripts/app/feature/feature.remote.html";
            }
            FeatureRemoteDirective.prototype.onLink = function (scope, element, attributes) {
                scope.select = function (signalData) {
                    var irState = scope.feature.state;
                    irState.signal = {
                        format: "NEC",
                        bytes: [
                            { bits: 32, data: parseInt(signalData, 16) },
                            { bits: 0, data: -1 }
                        ]
                    };
                    scope.notifyStateChanged();
                };
            };
            FeatureRemoteDirective.$name = "featureRemote";
            FeatureRemoteDirective.$inject = [App.Repository.DeviceService.$name];
            return FeatureRemoteDirective;
        }(App.BaseDirective));
        Feature.FeatureRemoteDirective = FeatureRemoteDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.remote.directive.js.map