///<reference path="..\common.ng.ts"/>
module App.Repository {

    export interface IFeatureModel {
        id: number;
        feature: string;
        displayName: string;
    }

    export interface IDeviceModel {
        id: number;
        displayName: string;
        features: Array<IFeatureModel>;
    }

    export class DeviceService {

        static $name = "DeviceService";
        static $inject = [NgSvc.http, NgSvc.$q];

        constructor(private $http: ng.IHttpService,
                    private q: ng.IQService) {
        }

        getAllByPartitionId(partitionId: number) {
            return this.$http.get<Array<IDeviceModel>>("/api/device", { params: { partitionId: partitionId } });
        }
    }

}