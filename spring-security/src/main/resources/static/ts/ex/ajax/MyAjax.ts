import {TsAjax} from "./TsAjax";

// 定义 ajax 请求所需的参数
interface AjaxConfig {
    type: string;
    url: string;
    data?: string;
    dataType: string;
}

// 封装原生的ajax
export class MyAjax extends TsAjax{

    private doAjax(config: AjaxConfig): any{
        let result: any = "";
        let xhr = new XMLHttpRequest();
        if(null == xhr){
            xhr = new XMLHttpRequest("Microsoft.XMLHTTP");
        }
        if(null != xhr){
            xhr.open(config.type, config.url, true);
            xhr.send(config.data);
            xhr.onreadystatechange = function () {
                console.log(xhr.statusText);
                let data = xhr.responseText;
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (config.dataType === 'json') {
                        result = JSON.parse(data);
                    } else {
                        result = data;
                    }
                } else {
                    result = data; // error
                }
            }
        }else {
            result = "Your browser does not support XMLHTTP.";
        }
        console.log(result);
        return result;
    }

    _delete(url: string, data?: string): any {
        return this.doAjax({
            type:'delete',
            data:data,
            url:url, //api
            dataType:'json'
        });
    }

    _get(url: string, data?: string): any {
        return this.doAjax({
            type:'get',
            data:data,
            url:url, //api
            dataType:'json'
        });
    }

    _post(url: string, data?: string): any {
        return this.doAjax({
            type:'post',
            data:data,
            url:url, //api
            dataType:'json'
        });
    }

    _put(url: string, data?: string): any {
        return this.doAjax({
            type:'put',
            data:data,
            url:url, //api
            dataType:'json'
        });
    }

}