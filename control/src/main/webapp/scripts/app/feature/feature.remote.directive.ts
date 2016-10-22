///<reference path="..\..\components\common.ng.ts"/>
///<reference path="..\..\components\repository\device.service.ts"/>
///<reference path="feature.directive.ts"/>
module App.Feature {

    interface IRemoteFeatureScope extends IFeatureScope {
        select(signalData: string);
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
            scope.select = (signalData: string) => {
                var irState = (<Model.IRFeatureStateDto> scope.feature.state);
                irState.signal = {
                    format: "NEC",
                    bytes: [
                        { bits: 32, data: parseInt(signalData, 16) },
                        { bits: 0, data: -1 }
                    ]
                };
                scope.notifyStateChanged();
            };
        }
    }
}
