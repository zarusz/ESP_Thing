///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface ISensorTemperatureFeatureScope extends IFeatureScope<Repository.ISensorTemperatureFeatureStateModel> {

    }

    export class FeatureSensorTemperatureDirective extends BaseDirective<ISensorTemperatureFeatureScope> {
        static $name = "featureSensorTemperature";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.sensor.temperature.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: ISensorTemperatureFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            //scope.$watch((s: ISensorTemperatureFeatureScope) => s.feature.state.on, () => scope.notifyStateChanged());
        }

    }
}
