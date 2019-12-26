///<reference path="..\common.ng.ts"/>
///<reference path="..\models.d.ts"/>
module App.Repository {

    export class PartitionDto implements Model.PartitionDescDto {
        id: number;
        displayName: string;
        displayPriority: number;
        children: Array<PartitionDto>;
        parent: PartitionDto;
    }

    export class PartitionService {

        static $name = "PartitionService";
        static $inject = [NgSvc.http, NgSvc.q];

        private root: PartitionDto;
        private rootLoading: ng.IPromise<PartitionDto>;
        private partitionById: { [id: number]: PartitionDto } = {};

        constructor(private http: ng.IHttpService,
                    private q: ng.IQService) {

            this.loadRoot();
        }

        loadRoot(): ng.IPromise<PartitionDto> {
            this.rootLoading = this.http.get<PartitionDto>("/api/partition").then(p => p.data);
            this.rootLoading.then(p => {
                this.setRoot(p);
                this.rootLoading = null;
            });
            return this.rootLoading;
        }

        private setRoot(newRoot: PartitionDto) {
            this.root = newRoot;
            this.partitionById = {};
            this.processNode(newRoot, null);
        }

        private processNode(node: PartitionDto, parent: PartitionDto) {
            node.parent = parent;
            this.partitionById[node.id] = node;
            _.forEach(node.children, child => this.processNode(child, node));
        }

        getById(partitionId: number): ng.IPromise<PartitionDto> {
            var deferred = this.q.defer<PartitionDto>();
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
