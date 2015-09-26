///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
module App.Feature {

    export interface IFeatureScope extends ng.IScope {
        feature: Repository.IFeatureModel;
    }

    export interface IFeatureAttributes extends ng.IAttributes {
        feature: string;
    }

    export class FeatureDirective extends BaseDirective<IFeatureScope> {
        static $name = "feature";

        restrict = "EA";
        transclude = true;
        scope = {
            //feature: "="
        };
        templateUrl = "scripts/app/feature/feature.html";

        onLink(scope: IFeatureScope, element: ng.IAugmentedJQuery, attributes: IFeatureAttributes) {
            scope.$parent.$watch(attributes.feature, (newValue: Repository.IFeatureModel) => {
                scope.feature = newValue;

            });


        }

    }




}