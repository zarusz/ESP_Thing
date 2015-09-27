///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    export class FeatureLedDirective extends BaseDirective<IFeatureScope> {
        static $name = "featureLed";

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.led.html";

        onLink(scope: IFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {

        }

    }
}