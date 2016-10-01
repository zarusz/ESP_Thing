///<reference path="..\..\..\components\repository\repository.module.ts"/>
module App.Admin.Devices {

    export class DevicesCtrl {
        static $inject = [
            Repository.DeviceService.$name,
            NgSvc.state,
            NgSvc.stateParams
        ];
        static $nameAs = "vm";

        devices: Array<Repository.IDeviceModel>;

        constructor(private deviceService: Repository.DeviceService,
                    private state: ng.ui.IStateService,
                    private stateParams: any) {

            this.deviceService.getHubAll().then(d => d.data).then(devices => {
                this.setDevices(devices);
            });
        }

        private setDevices(d: Array<Repository.IDeviceModel>) {
            this.devices = d;
        }
    }
}
