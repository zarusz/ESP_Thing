'use strict';

angular.module('controlApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
