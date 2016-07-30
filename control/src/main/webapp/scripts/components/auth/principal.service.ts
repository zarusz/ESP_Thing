///<reference path="..\common.ng.ts"/>
///<reference path="..\..\app\app.module.ts"/>
'use strict';
module App.Auth {

    export class Identity {
        roles: Array<string>;
    }

    export interface IPrincipalChangedEventHandler {
        onPrincipalChanged(e: PrincipalChangedEvent);
    }

    export class PrincipalChangedEvent implements IEvent<IPrincipalChangedEventHandler> {
        getId() {
            return "PrincipalChangedEvent";
        }

        handle(handler: IPrincipalChangedEventHandler) {
            handler.onPrincipalChanged(this);
        }

        constructor(public principal: Principal) {
        }

        static event = new PrincipalChangedEvent(null);
    }

    export class Principal {
        static $name = "Principal";
        static $inject = [NgSvc.q, "Account", "Tracker", EventBus.$name];

        private _identity: Identity;
        private _authenticated = false;

        constructor(private $q: ng.IQService, private account, private tracker, private eventBus: IEventBus) {
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
            this.eventBus.publish(new PrincipalChangedEvent(this));
        }

        identity(force = false) {
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
                    this.authenticate(account1.data);
                    deferred.resolve(this._identity);
                    this.tracker.connect();
                })
                .catch(() => {
                    this.authenticate(null);
                    deferred.resolve(this._identity);
                });
            return deferred.promise;
        }
    }

    $module.service(Principal.$name, Principal);
}
