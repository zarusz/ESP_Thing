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
            NgSvc.stateParams,
            NgSvc.mdToast
        ];
        static $nameAs = "vm";

        device: Repository.IDeviceUpdateModel;
        partitions: Array<Repository.IPartitionDescModel> = [];
        displayIcons: Array<string> = [
            "fa-cube",
            "fa-cubes",
            "fa-compass",
            "fa-envelope",
            "fa-folder",
            "fa-flask",
            "fa-flag",
            "fa-flash",
            "fa-fire"
        ];

        constructor(private deviceService: Repository.DeviceService,
                    private partitionService: Repository.PartitionService,
                    private state: ng.ui.IStateService,
                    private stateParams: DeviceEditStateParams,
                    private mdToast: ng.material.IToastService) {

            this.partitionService.loadRoot().then(x => this.setPartitions(x));

            this.deviceService.getById(stateParams.deviceId).then(device => {
                this.setDevice(device);
            });
        }

        private setDevice(d: Repository.IDeviceModel) {
            this.device = {
                id: d.id,
                displayName: d.displayName,
                displayIcon: d.displayIcon,
                partition: d.partition ? {id: d.partition.id} : null
            };
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

        save(form: angular.IFormController) {
            if (form.$invalid) {
                return;
            }
            this.deviceService.update(this.device).then(() => {
                this.mdToast.show(this.mdToast.simple().textContent("Saved").hideDelay(3000));
            });
        }
    }
}
