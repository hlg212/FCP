import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  findOrgType: serviceUrl + "orgTreeType/find",
  findTree: serviceUrl + "orgTree/findTree",
  getById: serviceUrl + "orgTree/getById",
  save: serviceUrl + "orgTree/save",
  //exportPage: serviceUrl + "app/exportPage",
  exportPage: serviceUrl + "orgTree/exportPage",
  importSave: serviceUrl + "orgTree/import/upload/save"
}
export function findOrgTreeType(query) {
  return request({
    url: moduleUrl.findOrgType,
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
