///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    import ISensorTemperatureFeatureStateModel = App.Repository.ISensorTemperatureFeatureStateModel;
    export class FeatureSensorTemperatureDirective implements ng.IDirective {
        static $name = "featureSensorTemperature";
        static $inject = [
            Repository.DeviceService.$name,
            EventBus.$name
        ];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.sensor.temperature.html";
        replace = true;

        constructor(private deviceService: Repository.DeviceService,
                    private eventBus: IEventBus) {
        }

        link = (scope: IFeatureScope<Repository.ISensorTemperatureFeatureStateModel>, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) => {
        };
    }
}
