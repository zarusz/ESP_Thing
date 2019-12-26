///<reference path="../common.module.ts"/>
///<reference path="partition.service.ts"/>
///<reference path="device.service.ts"/>
module App.Repository {

    export var $module = angular.module("RepositoryModule", [Common.$module.name, "LocalStorageModule"]);
    $module.service(PartitionService.$name, PartitionService);
    $module.service(DeviceService.$name, DeviceService);
}
