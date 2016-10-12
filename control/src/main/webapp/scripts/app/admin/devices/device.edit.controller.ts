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

        device: Model.DeviceUpdateDto;
        partitions: Array<Model.PartitionDescDto> = [];
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

        private setDevice(d: Model.DeviceDto) {
            this.device = {
                displayName: d.displayName,
                displayIcon: d.displayIcon,
                partition: d.partition ? {id: d.partition.id} : null
            };
        }

        private setPartitions(p: Repository.PartitionDto) {
            var list = new Array<Repository.PartitionDto>();
            this.flattenPartition(list, p);
            this.partitions = list;
        }

        private flattenPartition(list: Repository.PartitionDto[], p: Repository.PartitionDto) {
            list.push(p);
            _.forEach(p.children, child => this.flattenPartition(list, child));
        }

        save(form: angular.IFormController) {
            if (form.$invalid) {
                return;
            }
            this.deviceService.update(this.stateParams.deviceId, this.device).then(() => {
                this.mdToast.show(this.mdToast.simple().textContent("Saved").hideDelay(3000));
            });
        }
    }
}
