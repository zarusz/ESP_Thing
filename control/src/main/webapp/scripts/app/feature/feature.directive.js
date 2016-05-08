///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
var App;
(function (App) {
    var Feature;
    (function (Feature) {
        var FeatureDirective = (function () {
            function FeatureDirective(deviceService, timeout, eventBus) {
                var _this = this;
                this.deviceService = deviceService;
                this.timeout = timeout;
                this.eventBus = eventBus;
                this.restrict = "E";
                this.transclude = true;
                this.scope = {};
                this.templateUrl = "scripts/app/feature/feature.html";
                this.replace = true;
                this.link = function (scope, element, attributes) {
                    scope.$parent.$watch(attributes.feature, function (newValue) {
                        scope.feature = newValue;
                    });
                    scope.$parent.$watch(attributes.device, function (newValue) {
                        scope.device = newValue;
                    });
                    scope.notifyStateChanged = function () {
                        if (scope.timeoutHandle) {
                            _this.timeout.cancel(scope.timeoutHandle);
                        }
                        scope.timeoutHandle = _this.timeout(function () {
                            scope.timeoutHandle = null;
                            _this.deviceService.updateFeatureState(scope.device, scope.feature);
                        }, 300);
                    };
                    var featureStateUpdated = {
                        onFeatureStateChanged: function (e) {
                            if (scope.feature.id === e.feature.id) {
                                console.info("Received feature state change update", e.feature);
                                scope.feature.state = e.feature.state;
                                scope.$applyAsync();
                            }
                        }
                    };
                    _this.eventBus.subscribe(App.Repository.FeatureStateChangedEvent.event, featureStateUpdated);
                    scope.$on(App.NgEvent.destroy, function () { return _this.eventBus.unsubscribe(App.Repository.FeatureStateChangedEvent.event, featureStateUpdated); });
                };
            }
            FeatureDirective.$name = "feature";
            return FeatureDirective;
        }());
        Feature.FeatureDirective = FeatureDirective;
    })(Feature = App.Feature || (App.Feature = {}));
})(App || (App = {}));
//# sourceMappingURL=feature.directive.js.map