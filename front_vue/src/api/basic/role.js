import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  findPage: serviceUrl + "role/findPage",
  getById: serviceUrl + "role/getById",
  save: serviceUrl + "role/save",
  update: serviceUrl + "role/updateAndReturn",
  deleteById: serviceUrl + "role/deleteById",
  //exportPage: serviceUrl + "app/exportPage",
  exportPage: serviceUrl + "role/exportPage",
  importSave: serviceUrl + "role/import/upload/save"
}
// export function findPage(query) {
//   return request({
//     url: moduleUrl.findPage,
//     method: 'get',
//     params: query
//   })
// }
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
