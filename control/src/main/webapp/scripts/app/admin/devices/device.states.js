var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var States = (function () {
                function States() {
                }
                States.List = "admin_device_list";
                States.Edit = "admin_device_edit";
                States.Upgrade = "admin_device_upgrade";
                return States;
            }());
            Devices.States = States;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=device.states.js.map