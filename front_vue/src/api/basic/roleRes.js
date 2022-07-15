import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  getAppResTree: serviceUrl + "roleRes/getAppResTree",
  getAppRoleResTree: serviceUrl + "roleRes/getAppRoleResTree",
  getRoleResTree: serviceUrl + "roleRes/getRoleResTree",
  getRoleResIds: serviceUrl + "roleRes/getRoleResIds",
  saveRoleRes: serviceUrl + "roleRes/saveRoleRes"
}


export function getRoleResIds(roleId,resCategory) {
  return request({
    url: moduleUrl.getRoleResIds,
    method: 'get',
    params: { roleId,resCategory }
  })
}

export function getAppResTree(resCategory) {
  return request({
    url: moduleUrl.getAppResTree,
    method: 'get',
    params: { resCategory }
  })
}

export function getAppRoleResTree(roleId,resCategory) {
  return request({
    url: moduleUrl.getAppRoleResTree,
    method: 'get',
    params: { roleId,resCategory }
  })
}

export function getRoleResTree(roleId,resCategory) {
  return request({
    url: moduleUrl.getRoleResTree,
    method: 'get',
    params: { roleId,resCategory }
  })
}


export function saveRoleRes(data) {
  return request({
    url: moduleUrl.saveRoleRes,
    method: 'post',
    data
  })

}
