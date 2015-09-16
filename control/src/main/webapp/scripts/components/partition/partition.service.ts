///<reference path="..\..\app\app.module.ts"/>
///<reference path="..\common.ts"/>
module App.Component.Partition {

    export class Device {
        id: number;
        displayName: string;
    }

    export class Partition {
        id: number;
        displayName: string;
        children: Array<Partition>;
        devices: Array<Device>;
    }

    export class PartitionService {

        static $name = "PartitionService";
        static $inject = [NgSvc.http];

        constructor(private $http: ng.IHttpService) {

        }

        loadRoot() {
            return this.$http.get<Partition>("/api/partition");
        }

    }

    $module.service(PartitionService.$name, PartitionService);

}