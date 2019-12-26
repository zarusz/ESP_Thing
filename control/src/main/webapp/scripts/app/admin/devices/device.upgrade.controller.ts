///<reference path="..\..\..\components\repository\repository.module.ts"/>
module App.Admin.Devices {

    export interface DeviceUpgradeStateParams {
        deviceId: number;
    }

    export class DeviceUpgradeCtrl {
        static $inject = [
            Repository.DeviceService.$name,
            Repository.PartitionService.$name,
            NgSvc.state,
            NgSvc.stateParams,
            NgSvc.mdToast
        ];
        static $nameAs = "vm";

        device: Model.DeviceUpgradeDto;

        constructor(private deviceService: Repository.DeviceService,
                    private partitionService: Repository.PartitionService,
                    private state: ng.ui.IStateService,
                    private stateParams: DeviceUpgradeStateParams,
                    private mdToast: ng.material.IToastService) {

            this.deviceService.getById(stateParams.deviceId).then(device => {
                this.setDevice(device);
            });
        }

        private setDevice(d: Model.DeviceDto) {
            this.device = {
                firmwareUrl: null
            };
        }


        upgrade(form: angular.IFormController) {
            if (form.$invalid) {
                return;
            }
            this.deviceService.upgrade(this.stateParams.deviceId, this.device).then(() => {
                this.mdToast.show(this.mdToast.simple().textContent("Upgrade submitted!"));
            });
        }
    }
}
