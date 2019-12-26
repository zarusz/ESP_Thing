///<reference path="..\app.module.ts"/>
'use strict';

module App {

    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("account", {
                    abstract: true,
                    parent: "site"
                });
        });

}
