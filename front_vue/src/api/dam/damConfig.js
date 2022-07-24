import request from '@/utils/request'
var serviceUrl = "dam/";
var basicUrl = "basic/"
var moduleUrl ={
  findApps: basicUrl + "app/find",
  findPage: serviceUrl + "damConfig/findPage",
  getById: serviceUrl + "damConfig/getById",
  save: serviceUrl + "damConfig/save",
  update: serviceUrl + "damConfig/updateAndReturn",
  deleteById: serviceUrl + "damConfig/deleteById",
  exportPage: serviceUrl + "damConfig/exportPage",
  importSave: serviceUrl + "damConfig/import/upload/save"
}
export function findApps(query) {
  return request({
    url: moduleUrl.findApps,
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

export function deleteById(ids) {
  return request({
    url: moduleUrl.deleteById,
    method: 'post',
    params: { ids }
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
