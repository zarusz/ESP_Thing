'use strict';

angular.module('controlApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


