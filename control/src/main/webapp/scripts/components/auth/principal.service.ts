///<reference path="..\common.ng.ts"/>
///<reference path="..\..\app\app.module.ts"/>
'use strict';
module App.Auth {

    export class Identity {
        roles: Array<string>;
    }

    export class Principal {
        static $name = "Principal";
        static $inject = [NgSvc.q, "Account", "Tracker"];

        private _identity: Identity;
        private _authenticated = false;

        constructor(private $q: ng.IQService, private account, private tracker) {
        }

        isIdentityResolved() {
            return angular.isDefined(this._identity);
        }

        isAuthenticated() {
            return this._authenticated;
        }

        isInRole(role: string) {
            if (!this._authenticated || !this._identity || !this._identity.roles) {
                return false;
            }

            return this._identity.roles.indexOf(role) !== -1;
        }

        isInAnyRole(roles: Array<string>) {
            if (!this._authenticated || !this._identity.roles) {
                return false;
            }

            for (var i = 0; i < roles.length; i++) {
                if (this.isInRole(roles[i])) {
                    return true;
                }
            }

            return false;
        }

        authenticate(identity: Identity) {
            this._identity = identity;
            this._authenticated = identity !== null;
        }

        identity(force) {
            var deferred = this.$q.defer();

            if (force === true) {
                this._identity = undefined;
            }

            // check and see if we have retrieved the identity data from the server.
            // if we have, reuse it by immediately resolving
            if (angular.isDefined(this._identity)) {
                deferred.resolve(this._identity);

                return deferred.promise;
            }

            // retrieve the identity data from the server, update the identity object, and then resolve.
            this.account.get().$promise
                .then((account1) => {
                    this._identity = account1.data;
                    this._authenticated = true;
                    deferred.resolve(this._identity);
                    this.tracker.connect();
                })
                .catch(() => {
                    this._identity = null;
                    this._authenticated = false;
                    deferred.resolve(this._identity);
                });
            return deferred.promise;
        }
    }

    $module.service(Principal.$name, Principal);
}