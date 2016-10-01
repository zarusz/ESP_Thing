///<reference path="..\..\..\components\repository\repository.module.ts"/>
module App.Admin.Devices {

    export interface DeviceEditStateParams {
        deviceId: number;
    }

    export class DeviceEditCtrl {
        static $inject = [
            Repository.DeviceService.$name,
            Repository.PartitionService.$name,
            NgSvc.state,
            NgSvc.stateParams
        ];
        static $nameAs = "vm";

        device: Repository.IDeviceModel;
        partitions: Array<Repository.IPartitionDescModel> = [];

        constructor(private deviceService: Repository.DeviceService,
                    private partitionService: Repository.PartitionService,
                    private state: ng.ui.IStateService,
                    private stateParams: DeviceEditStateParams) {

            this.partitionService.loadRoot().then(x => this.setPartitions(x));

            this.deviceService.getById(stateParams.deviceId).then(device => {
                this.setDevice(device);
            });
        }

        private setDevice(d: Repository.IDeviceModel) {
            this.device = d;
        }

        private setPartitions(p: Repository.PartitionModel) {
            var list = new Array<Repository.PartitionModel>();
            this.flattenPartition(list, p);
            this.partitions = list;
        }

        private flattenPartition(list: Repository.PartitionModel[], p: Repository.PartitionModel) {
            list.push(p);
            _.forEach(p.children, child => this.flattenPartition(list, child));
        }
    }
}
