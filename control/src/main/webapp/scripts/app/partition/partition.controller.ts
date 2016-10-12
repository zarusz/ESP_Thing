///<reference path="..\..\components\repository\repository.module.ts"/>
module App.Partition {

    export class PartitionParams {
        constructor(public partitionId: number) {
        }
    }

    export class PartitionCtrl {
        static $inject = [
            Repository.PartitionService.$name,
            Repository.DeviceService.$name,
            NgSvc.state,
            NgSvc.stateParams
        ];
        static $nameAs = "p";

        partition: Repository.PartitionDto;
        crumbs: Array<Repository.PartitionDto>;
        devices: Array<Model.DeviceDto>;

        constructor(private partitionService: Repository.PartitionService,
                    private deviceService: Repository.DeviceService,
                    private state: ng.ui.IStateService,
                    private stateParams: PartitionParams) {

            this.partitionService.getById(stateParams.partitionId).then(p => {
                this.setPartition(p);
            });
        }

        private setPartition(p: Repository.PartitionDto) {
            this.partition = p;
            this.enumCrumbs();

            this.devices = [];
            this.deviceService.getAllByPartitionId(p.id).then(devices => {
                this.devices = devices;
            });

            //this.enumDevices(p);
        }

        private enumCrumbs() {
            var crumbs = [];
            for (var c = this.partition; c != null; c = c.parent) {
                crumbs.push(c);
            }
            this.crumbs = crumbs.reverse();
        }

        //private enumDevices(partition: Repository.PartitionModel) {
        //    _.forEach(partition.devices, dev => this.devices.push(dev));
        //    _.forEach(partition.children, child => this.enumDevices(child));
        //}
    }

}
