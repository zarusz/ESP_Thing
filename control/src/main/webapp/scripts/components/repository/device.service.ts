///<reference path="..\common.ng.ts"/>
///<reference path="..\common.eventbus.ts"/>
///<reference path="model.ts"/>
module App.Repository {

    export interface IFeatureStateModel {
        updated: string;
    }

    export interface IDimFeatureStateModel extends IFeatureStateModel {
        intensity: number;
    }

    export interface IRemoteFeatureStateModel extends IFeatureStateModel {
        value: number;
    }

    export interface ISwitchFeatureStateModel extends IFeatureStateModel {
        on: boolean;
    }

    export interface ISensorTemperatureFeatureStateModel extends IFeatureStateModel {
        temperature: number;
    }

    export interface ISensorHumidityFeatureStateModel extends IFeatureStateModel {
        humidity: number;
    }

    export interface IFeatureModel<T extends IFeatureStateModel> {
        id: number;
        feature: string;
        displayName: string;
        state: T;
    }

    export interface IDeviceModel extends IDeviceDescModel {
        partition: IPartitionDescModel;
        features: Array<IFeatureModel<IFeatureStateModel>>;
        lastOnline?: number;
    }

    export interface IFeatureStateChangedEventHandler {
        onFeatureStateChanged(e: FeatureStateChangedEvent);
    }

    export class FeatureStateChangedEvent implements IEvent<IFeatureStateChangedEventHandler> {

        constructor(public feature: IFeatureModel<IFeatureStateModel>) {
        }

        getId() {
            return "FeatureStateChangedEvent";
        }

        handle(handler: IFeatureStateChangedEventHandler) {
            handler.onFeatureStateChanged(this);
        }

        static event = new FeatureStateChangedEvent(null);
    }

    export class DeviceService {

        static $name = "DeviceService";
        static $inject = [NgSvc.http, NgSvc.cookies, NgSvc.q, NgSvc.localStorageService, EventBus.$name];

        private topicService: TopicService<IFeatureModel<IFeatureStateModel>, IFeatureStateChangedEventHandler, FeatureStateChangedEvent>;

        constructor(private http: ng.IHttpService,
                    private cookies: any,
                    private q: ng.IQService,
                    private localStorageService: angular.local.storage.ILocalStorageService,
                    private eventBus: IEventBus) {

            this.topicService = new TopicService(http, cookies, q, localStorageService, eventBus, (x: IFeatureModel<IFeatureStateModel>) => new FeatureStateChangedEvent(x));
            this.topicService.connect().then(() => {
                this.topicService.subscribe("/topic/feature-state");
            });
        }

        getHubAll() {
            return this.http.get<Array<IDeviceModel>>("/api/device/status");
        }

        getAllByPartitionId(partitionId: number) {
            return this.http.get<Array<IDeviceModel>>("/api/device", { params: { partitionId: partitionId } });
        }

        updateFeatureState(device: IDeviceModel, feature: IFeatureModel<IFeatureStateModel>) {
            var url = `api/device/${device.id}/feature/${feature.id}/state`;
            return this.http.post(url, feature.state);
        }

        getById(deviceId: number) {
            var url = `api/device/${deviceId}`;
            return this.http.get<IDeviceModel>(url).then(x => x.data);
        }
    }


    declare var Stomp: any;

    export class TopicService<T, THandler, TEvent extends IEvent<THandler>> {
        static $inject = [NgSvc.http, NgSvc.cookies, NgSvc.q, NgSvc.localStorageService];
        static $name = "TopicService";

        socket: SockJS;
        stompClient: any = null;
        subscriber = null;
        connected: ng.IDeferred<string>;
        alreadyConnectedOnce = false;

        constructor(private http: ng.IHttpService,
                    private cookies: any,
                    private q: ng.IQService,
                    private localStorageService: angular.local.storage.ILocalStorageService,
                    private eventBus: IEventBus,
                    private eventFactory: (payload: T) => TEvent) {

            this.connected = q.defer<string>();

            //building absolute path so that websocket doesnt fail when deploying with a context path
            var loc = window.location;
            var url = '//' + loc.host + loc.pathname + 'websocket/tracker';
            var token = <any> localStorageService.get("token");
            if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                url += "?access_token=" + token.access_token;
            }
            this.socket = new SockJS(url);
        }

        connect(): ng.IPromise<string> {
            this.stompClient = Stomp.over(this.socket);
            var headers = {};
            //headers['X-CSRF-TOKEN'] = this.cookies[this.http.defaults.xsrfCookieName];
            var token = <any> this.localStorageService.get("token");
            if (token) {
                headers["X-CSRF-TOKEN"] = token.access_token;
            }
            //headers["Authorization"] = "Bearer " + token.access_token;
            this.stompClient.connect(headers, (frame) => {
                this.connected.resolve("success");
                if (!this.alreadyConnectedOnce) {
                    this.alreadyConnectedOnce = true;
                }
            });
            return this.connected.promise;
        }

        disconnect() {
            if (this.stompClient != null) {
                this.stompClient.disconnect();
                this.stompClient = null;
            }
        }

        subscribe(topicName: string) {
            this.connected.promise.then(() => {
                this.subscriber = this.stompClient.subscribe(topicName, (data) => {
                    var payload = JSON.parse(data.body);
                    var event = this.eventFactory(payload);
                    this.eventBus.publish(event);
                });
            }, null, null);
        }

        unsubscribe() {
            if (this.subscriber != null) {
                this.subscriber.unsubscribe();
            }
        }
    }


}
