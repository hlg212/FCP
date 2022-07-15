import request from '@/utils/request'
var serviceUrl = "system/";
var moduleUrl ={
  login: serviceUrl + "login/sso",
  getInfo: serviceUrl + "auth/getUserInfo",
  logout: serviceUrl + "logout/ajax",
  getAppPermissions: serviceUrl + "auth/getAppPermissions",
  getMenuTree: serviceUrl + "auth/getMenuTree",
  getApps: serviceUrl + "auth/getApps"
}

export function login(data) {
  return request({
    url: moduleUrl.login,
    method: 'post',
    data
  })
}

export function getInfo(appCode) {
  return request({
    url: moduleUrl.getInfo,
    method: 'get',
    params: {appCode}
  })
}

export function logout() {
  return request({
    url: moduleUrl.logout,
    method: 'post'
  })
}


export function getAppPermissions(appCode) {
  return request({
    url: moduleUrl.getAppPermissions,
    method: 'get',
    params: {appCode}
  })
}


export function getMenuTree(appCode) {
  return request({
    url: moduleUrl.getMenuTree,
    method: 'get',
    params: {appCode}
  })
}


export function getApps() {
  return request({
    url: moduleUrl.getApps,
    method: 'post'
  })
}
