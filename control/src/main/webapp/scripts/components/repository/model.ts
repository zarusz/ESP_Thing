///<reference path="..\common.ng.ts"/>
module App.Repository {

    export interface IDeviceIdModel {
        id: number;
    }

    export interface IPartitionIdModel {
        id: number;
    }

    export interface IPartitionDescModel extends IPartitionIdModel {
        displayName: string;
    }

    export interface IDeviceDescModel extends IDeviceIdModel {
        guid: string;
        displayName: string;
        displayIcon: string;
    }

}
