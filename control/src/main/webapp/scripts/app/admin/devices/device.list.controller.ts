///<reference path="..\..\..\components\repository\repository.module.ts"/>
///<reference path="device.states.ts"/>
module App.Admin.Devices {

    export class DeviceListCtrl {
        static $inject = [
            Repository.DeviceService.$name,
            NgSvc.state,
            NgSvc.stateParams
        ];

        devices: Array<Model.DeviceDto>;

        constructor(private deviceService: Repository.DeviceService,
                    private state: ng.ui.IStateService,
                    private stateParams: any) {

            this.deviceService.getHubAll().then(devices => {
                this.setDevices(devices);
            });
        }

        private setDevices(d: Array<Model.DeviceDto>) {
            this.devices = d;
        }

        humanTime(d: number) {
            return moment(d).fromNow();
        }

        editDevice(device: Model.DeviceDto) {
            this.state.go(States.Edit, {deviceId: device.id});
        }

        upgradeDevice(device: Model.DeviceDto) {
            this.state.go(States.Upgrade, {deviceId: device.id});
        }
    }
}
