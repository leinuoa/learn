// 定义 CRUD 对应的抽象方法
export abstract class TsAjax {
    abstract _post(url: string, data?:string): any;
    abstract _put(url: string, data?:string): any;
    abstract _delete(url: string, data?:string): any;
    abstract _get(url: string, data?:string): any;
}