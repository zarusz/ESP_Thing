///<reference path="..\..\app.module.ts"/>
///<reference path="..\..\..\components\common.ng.ts"/>
///<reference path="..\..\..\components\auth\auth.service.ts"/>
"use strict";
module App {

    export class LoginCtrl {

        static $inject = [NgSvc.rootScope, NgSvc.state, Auth.Authenticator.$name];

        user: any = {};
        rememberMe: boolean = true;
        authenticationError: boolean = false;

        constructor(private rootScope, private state, private auth: Auth.Authenticator) {
        }

        login(form, event) {
            event.preventDefault();
            this.auth.login({
                username: form.username,
                password: form.password,
                rememberMe: form.rememberMe
            }).then(() => {
                this.authenticationError = false;
                if (this.rootScope.previousStateName === "register") {
                    this.state.go("home");
                } else {
                    this.rootScope.back();
                }
            }).catch(() => {
                this.authenticationError = true;
            });
        }
    }

}
