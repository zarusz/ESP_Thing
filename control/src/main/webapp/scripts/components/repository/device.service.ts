///<reference path="..\common.ng.ts"/>
module App.Repository {

    export interface IFeatureStateModel {
        updated: string;
    }

    export interface IDimFeatureStateModel extends IFeatureStateModel {
        intensity: number;
    }

    export interface IRemoteFeatureStateModel extends IFeatureStateModel {
        value: number;
    }

    export interface ISwitchFeatureStateModel extends IFeatureStateModel {
        on: boolean;
    }

    export interface ISensorTemperatureFeatureStateModel extends IFeatureStateModel {
        temperature: number;
    }

    export interface ISensorHumidityFeatureStateModel extends IFeatureStateModel {
        humidity: number;
    }

    export interface IFeatureModel<T extends IFeatureStateModel> {
        id: number;
        feature: string;
        displayName: string;
        state: T;
    }

    export interface IDeviceModel {
        id: number;
        displayName: string;
        features: Array<IFeatureModel<IFeatureStateModel>>;
    }

    export class DeviceService {

        static $name = "DeviceService";
        static $inject = [NgSvc.http, NgSvc.q];

        constructor(private $http: ng.IHttpService,
                    private q: ng.IQService) {
        }

        getAllByPartitionId(partitionId: number) {
            return this.$http.get<Array<IDeviceModel>>("/api/device", { params: { partitionId: partitionId } });
        }

        updateFeatureState(device: IDeviceModel, feature: IFeatureModel<IFeatureStateModel>) {
            var url = `api/device/${device.id}/feature/${feature.id}/state`;
            return this.$http.post(url, feature.state);
        }
    }

}
