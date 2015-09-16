///<reference path="..\app.module.ts"/>
'use strict';
var App;
(function (App) {
    App.$module.config(function ($stateProvider) {
        $stateProvider.state("account", {
            abstract: true,
            parent: "site"
        });
    });
})(App || (App = {}));
//# sourceMappingURL=account.js.map