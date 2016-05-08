///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.service.ts"/>
module App.Feature {

    interface ISwitchFeatureScope extends IFeatureScope<Repository.ISwitchFeatureStateModel> {
    }

    export class FeatureSwitchDirective implements ng.IDirective {
        static $name = "featureSwitch";
        static $inject = [
            Repository.DeviceService.$name,
            EventBus.$name
        ];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.switch.html";
        replace = true;

        constructor(private deviceService: Repository.DeviceService,
                    private eventBus: IEventBus) {
        }

        link = (scope: ISwitchFeatureScope, element: ng.IAugmentedJQuery, attributes: ng.IAttributes) => {
        };
    }
}
