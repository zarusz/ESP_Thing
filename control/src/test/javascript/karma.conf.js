// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '../../',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'main/webapp/bower_components/modernizr/modernizr.js',
            'main/webapp/bower_components/jquery/dist/jquery.js',
            'main/webapp/bower_components/angular/angular.js',
            'main/webapp/bower_components/angular-animate/angular-animate.js',
            'main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'main/webapp/bower_components/angular-cache-buster/angular-cache-buster.js',
            'main/webapp/bower_components/angular-cookies/angular-cookies.js',
            'main/webapp/bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js',
            'main/webapp/bower_components/angular-local-storage/dist/angular-local-storage.js',
            'main/webapp/bower_components/angular-aria/angular-aria.js',
            'main/webapp/bower_components/angular-material/angular-material.js',
            'main/webapp/bower_components/angular-resource/angular-resource.js',
            'main/webapp/bower_components/angular-sanitize/angular-sanitize.js',
            'main/webapp/bower_components/angular-translate/angular-translate.js',
            'main/webapp/bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js',
            'main/webapp/bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.js',
            'main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            'main/webapp/bower_components/bootstrap/dist/js/bootstrap.js',
            'main/webapp/bower_components/json3/lib/json3.js',
            'main/webapp/bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
            'main/webapp/bower_components/sockjs-client/dist/sockjs.js',
            'main/webapp/bower_components/stomp-websocket/lib/stomp.min.js',
            'main/webapp/bower_components/underscore/underscore.js',
            'main/webapp/bower_components/angular-mocks/angular-mocks.js',
            // endbower
            'main/webapp/scripts/components/common.ng.js',
            'main/webapp/scripts/components/common.eventbus.js',
            'main/webapp/scripts/components/common.module.js',

            'main/webapp/scripts/app/app.module.js',
            'main/webapp/scripts/app/app.constants.js',

            'main/webapp/scripts/components/repository/partition.service.js',
            'main/webapp/scripts/components/repository/device.service.js',
            'main/webapp/scripts/components/repository/repository.module.js',

            'main/webapp/scripts/components/auth/auth.service.js',
            'main/webapp/scripts/components/auth/principal.service.js',
            'main/webapp/scripts/components/auth/authority.directive.js',
            'main/webapp/scripts/components/auth/services/account.service.js',
            'main/webapp/scripts/components/auth/services/activate.service.js',
            'main/webapp/scripts/components/auth/services/password.service.js',
            'main/webapp/scripts/components/auth/services/register.service.js',

            'main/webapp/scripts/components/sidenav/sidenav.ctrl.js',
            'main/webapp/scripts/components/form/form.directive.js',
            'main/webapp/scripts/components/form/pager.directive.js',
            'main/webapp/scripts/components/form/pagination.directive.js',
            'main/webapp/scripts/components/admin/audits.service.js',
            'main/webapp/scripts/components/admin/logs.service.js',
            'main/webapp/scripts/components/admin/configuration.service.js',
            'main/webapp/scripts/components/admin/monitoring.service.js',
            'main/webapp/scripts/components/navbar/navbar.directive.js',
            'main/webapp/scripts/components/navbar/navbar.controller.js',
            'main/webapp/scripts/components/user/user.service.js',
            'main/webapp/scripts/components/util/truncate.filter.js',
            'main/webapp/scripts/components/util/base64.service.js',
            'main/webapp/scripts/components/util/parse-links.service.js',
            'main/webapp/scripts/components/util/dateutil.service.js',

            'main/webapp/scripts/components/language/language.service.js',
            'main/webapp/scripts/components/language/language.controller.js',
            'main/webapp/scripts/components/auth/provider/auth.oauth2.service.js',
            'main/webapp/scripts/components/tracker/tracker.service.js',

            'main/webapp/scripts/app/account/account.js',
            'main/webapp/scripts/app/account/activate/activate.js',
            'main/webapp/scripts/app/account/activate/activate.controller.js',
            'main/webapp/scripts/app/account/login/login.controller.js',
            'main/webapp/scripts/app/account/login/login.js',
            'main/webapp/scripts/app/account/logout/logout.controller.js',
            'main/webapp/scripts/app/account/logout/logout.js',
            'main/webapp/scripts/app/account/password/password.js',
            'main/webapp/scripts/app/account/password/password.controller.js',
            'main/webapp/scripts/app/account/password/password.directive.js',
            'main/webapp/scripts/app/account/register/register.js',
            'main/webapp/scripts/app/account/register/register.controller.js',
            'main/webapp/scripts/app/account/settings/settings.js',
            'main/webapp/scripts/app/account/settings/settings.controller.js',
            'main/webapp/scripts/app/account/reset/finish/reset.finish.controller.js',
            'main/webapp/scripts/app/account/reset/finish/reset.finish.js',
            'main/webapp/scripts/app/account/reset/request/reset.request.controller.js',
            'main/webapp/scripts/app/account/reset/request/reset.request.js',
            'main/webapp/scripts/app/admin/admin.js',
            'main/webapp/scripts/app/admin/audits/audits.js',
            'main/webapp/scripts/app/admin/audits/audits.controller.js',
            'main/webapp/scripts/app/admin/configuration/configuration.js',
            'main/webapp/scripts/app/admin/configuration/configuration.controller.js',
            'main/webapp/scripts/app/admin/docs/docs.js',
            'main/webapp/scripts/app/admin/health/health.js',
            'main/webapp/scripts/app/admin/health/health.controller.js',
            'main/webapp/scripts/app/admin/logs/logs.js',
            'main/webapp/scripts/app/admin/logs/logs.controller.js',
            'main/webapp/scripts/app/admin/metrics/metrics.js',
            'main/webapp/scripts/app/admin/metrics/metrics.controller.js',
            'main/webapp/scripts/app/entities/entity.js',
            'main/webapp/scripts/app/error/error.js',

            'main/webapp/scripts/app/home/home.controller.js',
            'main/webapp/scripts/app/home/home.js',
            'main/webapp/scripts/app/place/place.controller.js',
            'main/webapp/scripts/app/place/place.js',

            'main/webapp/scripts/app/feature/feature.directive.js',
            'main/webapp/scripts/app/feature/feature.switch.directive.js',
            'main/webapp/scripts/app/feature/feature.dim.directive.js',
            'main/webapp/scripts/app/feature/feature.remote.directive.js',
            'main/webapp/scripts/app/feature/feature.sensor.temperature.directive.js',
            'main/webapp/scripts/app/feature/feature.sensor.humidity.directive.js',
            'main/webapp/scripts/app/feature/feature.js',

            'main/webapp/scripts/app/partition/partition.controller.js',
            'main/webapp/scripts/app/partition/partition.js',

            'main/webapp/scripts/app/actionbar/actionbar.ctrl.js',

            'main/webapp/scripts/app/admin/tracker/tracker.js',
            'main/webapp/scripts/app/admin/tracker/tracker.controller.js',

            'main/webapp/scripts/app/app.js',

            'test/javascript/**/!(karma.conf).js'
        ],


        // list of files / patterns to exclude
        exclude: [],

        // web server port
        port: 9876,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false
    });
};
