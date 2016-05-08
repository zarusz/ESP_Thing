///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
module App.Feature {

    export interface IFeatureScope<T extends Repository.IFeatureStateModel> extends ng.IScope {
        device: Repository.IDeviceModel;
        feature: Repository.IFeatureModel<T>;
        timeoutHandle: ng.IPromise<void>;
        notifyStateChanged();
    }

    export interface IFeatureAttributes extends ng.IAttributes {
        feature: string;
        device: string;
    }

    export class FeatureDirective implements ng.IDirective {
        static $name = "feature";

        restrict = "E";
        transclude = true;
        scope = {};
        templateUrl = "scripts/app/feature/feature.html";
        replace = true;

        constructor(private deviceService: Repository.DeviceService,
                    private timeout: ng.ITimeoutService,
                    private eventBus: IEventBus) {
        }

        link = (scope: IFeatureScope<Repository.IFeatureStateModel>, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) => {
            scope.$parent.$watch(attributes.feature, (newValue: Repository.IFeatureModel<Repository.IFeatureStateModel>) => {
                scope.feature = newValue;
            });
            scope.$parent.$watch(attributes.device, (newValue: Repository.IDeviceModel) => {
                scope.device = newValue;
            });

            scope.notifyStateChanged = () => {
                if (scope.timeoutHandle) {
                    this.timeout.cancel(scope.timeoutHandle);
                }

                scope.timeoutHandle = this.timeout(() => {
                    scope.timeoutHandle = null;
                    this.deviceService.updateFeatureState(scope.device, scope.feature);
                }, 300);
            };

            var featureStateUpdated: Repository.IFeatureStateChangedEventHandler = {
                onFeatureStateChanged(e: Repository.FeatureStateChangedEvent) {
                    if (scope.feature.id === e.feature.id) {
                        console.info("Received feature state change update", e.feature);
                        scope.feature.state = e.feature.state;
                        scope.$applyAsync();
                    }
                }
            };

            this.eventBus.subscribe(Repository.FeatureStateChangedEvent.event, featureStateUpdated);
            scope.$on(NgEvent.destroy, () => this.eventBus.unsubscribe(Repository.FeatureStateChangedEvent.event, featureStateUpdated));
        };

    }




}
