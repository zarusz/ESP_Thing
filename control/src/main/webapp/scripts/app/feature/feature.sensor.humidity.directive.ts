///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface ISensorHumidityFeatureScope extends IFeatureScope<Repository.ISensorHumidityFeatureStateModel> {

    }

    export class FeatureSensorHumidityDirective extends BaseDirective<ISensorHumidityFeatureScope> {
        static $name = "featureSensorHumidity";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.sensor.humidity.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: ISensorHumidityFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            //scope.$watch((s: ISensorTemperatureFeatureScope) => s.feature.state.on, () => scope.notifyStateChanged());
        }

    }
}
