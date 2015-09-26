///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    export class FeatureSwitchDirective extends BaseDirective<IFeatureScope> {
        static $name = "featureSwitch";

        restrict = "EA";
        templateUrl = "scripts/app/feature/feature.switch.html";

        onLink(scope: IFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {

        }

    }
}