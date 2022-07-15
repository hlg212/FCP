import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  getResTree: serviceUrl + "userRes/getResTree"
}


export function getResTree(userId,roleType) {
  return request({
    url: moduleUrl.getResTree,
    method: 'get',
    params: { userId,roleType }
  })
}