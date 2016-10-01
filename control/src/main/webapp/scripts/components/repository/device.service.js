///<reference path="..\common.ng.ts"/>
///<reference path="..\common.eventbus.ts"/>
///<reference path="model.ts"/>
var App;
(function (App) {
    var Repository;
    (function (Repository) {
        var FeatureStateChangedEvent = (function () {
            function FeatureStateChangedEvent(feature) {
                this.feature = feature;
            }
            FeatureStateChangedEvent.prototype.getId = function () {
                return "FeatureStateChangedEvent";
            };
            FeatureStateChangedEvent.prototype.handle = function (handler) {
                handler.onFeatureStateChanged(this);
            };
            FeatureStateChangedEvent.event = new FeatureStateChangedEvent(null);
            return FeatureStateChangedEvent;
        }());
        Repository.FeatureStateChangedEvent = FeatureStateChangedEvent;
        var DeviceService = (function () {
            function DeviceService(http, cookies, q, localStorageService, eventBus) {
                var _this = this;
                this.http = http;
                this.cookies = cookies;
                this.q = q;
                this.localStorageService = localStorageService;
                this.eventBus = eventBus;
                this.topicService = new TopicService(http, cookies, q, localStorageService, eventBus, function (x) { return new FeatureStateChangedEvent(x); });
                this.topicService.connect().then(function () {
                    _this.topicService.subscribe("/topic/feature-state");
                });
            }
            DeviceService.prototype.getHubAll = function () {
                return this.http.get("/api/device/status");
            };
            DeviceService.prototype.getAllByPartitionId = function (partitionId) {
                return this.http.get("/api/device", { params: { partitionId: partitionId } });
            };
            DeviceService.prototype.updateFeatureState = function (device, feature) {
                var url = "api/device/" + device.id + "/feature/" + feature.id + "/state";
                return this.http.post(url, feature.state);
            };
            DeviceService.$name = "DeviceService";
            DeviceService.$inject = [App.NgSvc.http, App.NgSvc.cookies, App.NgSvc.q, App.NgSvc.localStorageService, App.EventBus.$name];
            return DeviceService;
        }());
        Repository.DeviceService = DeviceService;
        var TopicService = (function () {
            function TopicService(http, cookies, q, localStorageService, eventBus, eventFactory) {
                this.http = http;
                this.cookies = cookies;
                this.q = q;
                this.localStorageService = localStorageService;
                this.eventBus = eventBus;
                this.eventFactory = eventFactory;
                this.stompClient = null;
                this.subscriber = null;
                this.alreadyConnectedOnce = false;
                this.connected = q.defer();
                //building absolute path so that websocket doesnt fail when deploying with a context path
                var loc = window.location;
                var url = '//' + loc.host + loc.pathname + 'websocket/tracker';
                var token = localStorageService.get("token");
                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    url += "?access_token=" + token.access_token;
                }
                this.socket = new SockJS(url);
            }
            TopicService.prototype.connect = function () {
                var _this = this;
                this.stompClient = Stomp.over(this.socket);
                var headers = {};
                //headers['X-CSRF-TOKEN'] = this.cookies[this.http.defaults.xsrfCookieName];
                var token = this.localStorageService.get("token");
                if (token) {
                    headers["X-CSRF-TOKEN"] = token.access_token;
                }
                //headers["Authorization"] = "Bearer " + token.access_token;
                this.stompClient.connect(headers, function (frame) {
                    _this.connected.resolve("success");
                    if (!_this.alreadyConnectedOnce) {
                        _this.alreadyConnectedOnce = true;
                    }
                });
                return this.connected.promise;
            };
            TopicService.prototype.disconnect = function () {
                if (this.stompClient != null) {
                    this.stompClient.disconnect();
                    this.stompClient = null;
                }
            };
            TopicService.prototype.subscribe = function (topicName) {
                var _this = this;
                this.connected.promise.then(function () {
                    _this.subscriber = _this.stompClient.subscribe(topicName, function (data) {
                        var payload = JSON.parse(data.body);
                        var event = _this.eventFactory(payload);
                        _this.eventBus.publish(event);
                    });
                }, null, null);
            };
            TopicService.prototype.unsubscribe = function () {
                if (this.subscriber != null) {
                    this.subscriber.unsubscribe();
                }
            };
            TopicService.$inject = [App.NgSvc.http, App.NgSvc.cookies, App.NgSvc.q, App.NgSvc.localStorageService];
            TopicService.$name = "TopicService";
            return TopicService;
        }());
        Repository.TopicService = TopicService;
    })(Repository = App.Repository || (App.Repository = {}));
})(App || (App = {}));
//# sourceMappingURL=device.service.js.map