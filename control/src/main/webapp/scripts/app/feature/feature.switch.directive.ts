///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface ISwitchFeatureScope extends IFeatureScope<Repository.ISwitchFeatureStateModel> {

    }

    export class FeatureSwitchDirective extends BaseDirective<ISwitchFeatureScope> {
        static $name = "featureSwitch";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.switch.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: ISwitchFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            scope.$watch(
                (s: ISwitchFeatureScope) => s.feature.state.on,
                (newValue: boolean, oldValue?: boolean) => {
                    if (oldValue != newValue)
                        scope.notifyStateChanged();
                }
            );
        }

    }
}
