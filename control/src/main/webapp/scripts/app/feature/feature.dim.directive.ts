///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    export class FeatureDimDirective extends BaseDirective<IFeatureScope> {
        static $name = "featureDim";

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.dim.html";

        onLink(scope: IFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {

        }

    }
}