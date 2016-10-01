///<reference path="..\common.ng.ts"/>
module App.Repository {

    export interface IPartitionDescModel {
        id: number;
        displayName: string;
    }

    export interface IDeviceDescModel {
        id: number;
        guid: string;
        displayName: string;
    }

}
