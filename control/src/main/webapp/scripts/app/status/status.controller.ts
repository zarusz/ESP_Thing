///<reference path="..\..\components\repository\repository.module.ts"/>
module App.Status {

    export class StatusCtrl {
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

            this.deviceService.getHubAll().then(d => {
                this.setDevices(d.data);
            });
        }

        private setDevices(d: Array<Repository.IDeviceModel>) {
            this.devices = d;
        }

        friendlyDate(date: string) {
            return date;
        }
    }
}
