///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface ILedFeatureScope extends IFeatureScope<Repository.ILedFeatureStateModel> {
        select(mode: number);
    }

    export class FeatureLedDirective extends BaseDirective<ILedFeatureScope> {
        static $name = "featureLed";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.led.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: ILedFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            scope.select = (newMode)=> {
                scope.feature.state.mode = newMode;
                scope.notifyStateChanged();
            };

        }

    }
}