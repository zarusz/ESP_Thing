///<reference path="..\common.ng.ts"/>
module App.Repository {

    export class PartitionModel {
        id: number;
        displayName: string;
        children: Array<PartitionModel>;
        parent: PartitionModel;
    }

    export class PartitionService {

        static $name = "PartitionService";
        static $inject = [NgSvc.http, NgSvc.$q];

        private root: PartitionModel;
        private rootLoading: ng.IPromise<any>;
        private partitionById: { [id: number]: PartitionModel } = {};

        constructor(private $http: ng.IHttpService,
                    private q: ng.IQService) {

            this.loadRoot();
        }

        loadRoot() {
            this.rootLoading = this.$http.get<PartitionModel>("/api/partition").success(p => {
                this.setRoot(p);
                this.rootLoading = null;
            });
            return this.rootLoading;
        }

        private setRoot(newRoot: PartitionModel) {
            this.root = newRoot;
            this.partitionById = {};
            this.processNode(newRoot, null);
        }

        private processNode(node: PartitionModel, parent: PartitionModel) {
            node.parent = parent;
            this.partitionById[node.id] = node;
            _.forEach(node.children, child => this.processNode(child, node));
        }

        getById(partitionId: number): ng.IPromise<PartitionModel> {
            var deferred = this.q.defer<PartitionModel>();
            if (this.rootLoading) {
                this.rootLoading.then(p => {
                    var partition = this.partitionById[partitionId];
                    deferred.resolve(partition);
                });
            } else {
                var partition = this.partitionById[partitionId];
                deferred.resolve(partition);
            }
            return deferred.promise;
        }
    }

}