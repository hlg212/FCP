import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  findPage: serviceUrl + "user/findPage",
  saveUserRoles: serviceUrl + "userRole/saveUserRoles"
}


export function findPage(data) {
  return request({
    url: moduleUrl.findPage,
    method: 'post',
    data
  })
}


export function saveUserRoles(data) {
  return request({
    url: moduleUrl.saveUserRoles,
    method: 'post',
    data
  })
}