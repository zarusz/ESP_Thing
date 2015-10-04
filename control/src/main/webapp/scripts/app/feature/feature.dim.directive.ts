///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface IDimFeatureScope extends IFeatureScope<Repository.IDimFeatureStateModel> {

    }

    export class FeatureDimDirective extends BaseDirective<IDimFeatureScope> {
        static $name = "featureDim";
        static $inject = [Repository.DeviceService.$name];

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.dim.html";

        constructor(private deviceService: Repository.DeviceService) {
            super();
        }

        onLink(scope: IDimFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            scope.$watch((s: IDimFeatureScope) => s.feature.state.intensity, () => scope.notifyStateChanged());
        }

    }
}