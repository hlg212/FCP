import request from '@/utils/request'
var serviceUrl = "dam/";
var moduleUrl ={
  findConfig: serviceUrl + "damConfig/find",
  findPage: serviceUrl + "damScopeCondition/findPage",
  getById: serviceUrl + "damScopeCondition/getById",
  save: serviceUrl + "damScopeCondition/save",
  update: serviceUrl + "damScopeCondition/updateAndReturn",
  //exportPage: serviceUrl + "app/exportPage",
  exportPage: serviceUrl + "damScopeCondition/exportPage",
  importSave: serviceUrl + "damScopeCondition/import/upload/save"
}

export function findConfig(query) {
  return request({
    url: moduleUrl.findConfig,
    method: 'get',
    params: query
  })
}
export function findPage(data) {
  return request({
    url: moduleUrl.findPage,
    method: 'post',
    data
  })
}

export function getById(id) {
  return request({
    url: moduleUrl.getById,
    method: 'get',
    params: { id }
  })
}


export function save(data) {
  return request({
    url: moduleUrl.save,
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: moduleUrl.update,
    method: 'post',
    data
  })
}

export function exportPage(data) {
  return request({
    url: moduleUrl.exportPage,
    method: 'post',
    // responseType: 'blob',
    // headers:{"Accept":"*/*"},
    data
  })
}

export function importSave(data) {
  return request({
    url: moduleUrl.importSave,
    method: 'post',
    
    data
  })
}
