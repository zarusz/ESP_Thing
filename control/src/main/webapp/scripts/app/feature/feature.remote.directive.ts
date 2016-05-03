///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface IRemoteFeatureScope extends IFeatureScope<Repository.IRemoteFeatureStateModel> {
        select(mode: string);
    }

    export class FeatureRemoteDirective extends BaseDirective<IRemoteFeatureScope> {
        static $name = "featureRemote";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.remote.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: IRemoteFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            scope.select = (newValue: string) => {
                var numValue = parseInt(newValue, 16);
                scope.feature.state.value = numValue;
                scope.notifyStateChanged();
            };

        }

    }
}
