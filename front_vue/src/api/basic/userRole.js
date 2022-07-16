import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  getRoles: serviceUrl + "userRole/getRoles",
  getResTree: serviceUrl + "userRole/getResTree",
  saveRoles: serviceUrl + "userRole/saveRoles",

  getAllRoles: serviceUrl + "role/find"
}

export function getRoles(userId) {
  return request({
    url: moduleUrl.getRoles,
    method: 'get',
    params: { userId }
  })
}

export function getResTree(userId,roleType,resCategory) {
  return request({
    url: moduleUrl.getResTree,
    method: 'get',
    params: { userId,roleType,resCategory}
  })
}

export function saveRoles(data) {
  return request({
    url: moduleUrl.saveRoles,
    method: 'post',
    data
  })
}

export function getAllRoles(data) {
  return request({
    url: moduleUrl.getAllRoles,
    method: 'post',
    data
  })
}