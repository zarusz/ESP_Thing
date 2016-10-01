///<reference path="..\..\..\components\repository\repository.module.ts"/>
///<reference path="device.states.ts"/>
module App.Admin.Devices {

    export class DeviceListCtrl {
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

        humanTime(d: number) {
            return moment(d).fromNow();
        }

        editDevice(device: Repository.IDeviceModel) {
            this.state.go(States.Edit, { deviceId:  device.id });
        }
    }
}
