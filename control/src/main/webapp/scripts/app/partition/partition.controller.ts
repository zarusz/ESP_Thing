///<reference path="..\..\components\repository\repository.module.ts"/>
module App {

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

        partition: Repository.PartitionModel;
        crumbs: Array<Repository.PartitionModel>;
        devices: Array<Repository.IDeviceModel>;

        constructor(private partitionService: Repository.PartitionService,
                    private deviceService: Repository.DeviceService,
                    private state: ng.ui.IStateService,
                    private stateParams: PartitionParams) {

            this.partitionService.getById(stateParams.partitionId).then(p => {
                this.setPartition(p);
            });
        }

        private setPartition(p: Repository.PartitionModel) {
            this.partition = p;
            this.enumCrumbs();

            this.devices = [];
            this.deviceService.getAllByPartitionId(p.id).success(devices => {
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