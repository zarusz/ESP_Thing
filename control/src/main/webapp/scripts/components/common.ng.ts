/// <reference path="../../typings/angularjs/angular.d.ts"/>
/// <reference path="../../typings/angular-ui-router/angular-ui-router.d.ts" />
/// <reference path="../../typings/angular-material/angular-material.d.ts" />
/// <reference path="../../typings/underscore/underscore.d.ts" />
module App {

    export class NgSvc {
        // Angular

        static rootScope = "$rootScope";
        static scope = "$scope";
        static http = "$http";
        static cookies = "$cookies";
        static q = "$q";
        static timeout = "$timeout";
        static window = "$window";

        // Angular UI Router

        static state = "$state";
        static stateParams = "$stateParams";

        //

        static localStorageService = "localStorageService";
        static translate = "$translate";
        static translatePartialLoader = "$translatePartialLoader";
    }

    export class NgEvent {
        static destroy = "$destroy";
    }

    export class BaseDirective<TScope extends ng.IScope> implements ng.IDirective {

        linkScope: TScope = null;

        constructor() {
        }

        link = (scope: TScope, element: ng.IAugmentedJQuery, attributes: ng.IAttributes) => {
            this.linkScope = scope;
            this.linkScope.$on(NgEvent.destroy, () => this.destroy());
            this.onLink(scope, element, attributes);
        }

        destroy() {
        }

        onLink(scope: TScope, element: ng.IAugmentedJQuery, attributes: ng.IAttributes) {

        }
    }

    export class NgUtils {

        /*
        static directive(module: ng.IModule, name: string, serviceConstructor: Function): ng.IModule {
            var depNames = _.clone(serviceConstructor.$inject);
            var createFunc = (...args: any[]) => new serviceConstructor(args);
            depNames.push();


            module.directive(name, depNames)     ;

            return module;
        }
        */

    }
}
