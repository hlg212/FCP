import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  findApps: serviceUrl + "app/find",
  findTree: serviceUrl + "res/findTree",
  getById: serviceUrl + "res/getById",
  save: serviceUrl + "res/save",
  update: serviceUrl + "res/updateAndReturn",
  deleteById: serviceUrl + "res/deleteById",
  //exportPage: serviceUrl + "app/exportPage",
  exportPage: serviceUrl + "res/exportPage",
  importSave: serviceUrl + "res/import/upload/save"
}
export function findApps(query) {
  return request({
    url: moduleUrl.findApps,
    method: 'get',
    params: query
  })
}
export function findTree(data) {
  return request({
    url: moduleUrl.findTree,
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
