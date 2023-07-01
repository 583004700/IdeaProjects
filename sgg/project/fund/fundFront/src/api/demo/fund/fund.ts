import {defHttp} from '/@/utils/http/axios';
import {DemoParams, DemoListGetResultModel} from '../model/tableModel';

export const demoListApi = (params: DemoParams) =>{
  params["gzdate"] = params["gzdate"].substr(0,10);
  return defHttp.get<DemoListGetResultModel>({
    url: '/lastNRise',
    params,
    timeout: -1,
    headers: {
      // @ts-ignore
      ignoreCancelToken: true,
    },
  });
}

export const updateGsData = (params: DemoParams) =>{
  return defHttp.get<DemoListGetResultModel>({
    url: '/insertBatch',
    params,
    timeout: -1,
    headers: {
      // @ts-ignore
      ignoreCancelToken: true,
    },
  });
}
