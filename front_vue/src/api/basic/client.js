import request from '@/utils/request'
//var serviceUrl = "basic";
//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "basic/";
var moduleUrl ={
  findPage: serviceUrl + "client/findPage",
  getById: serviceUrl + "client/getById",
  save: serviceUrl + "client/save",
  update: serviceUrl + "client/updateAndReturn",
  deleteById: serviceUrl + "client/deleteById",
  exportPage: serviceUrl + "client/exportPage",
  importSave: serviceUrl + "client/import/upload/save",
  getRoles: serviceUrl + "clientRole/getRoles",
  getResTree: serviceUrl + "clientRole/getResTree",
  saveRoles: serviceUrl + "clientRole/saveRoles",

  getAllRoles: serviceUrl + "role/find"
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

export function getRoles(clientId) {
  return request({
    url: moduleUrl.getRoles,
    method: 'get',
    params: { clientId }
  })
}

export function getResTree(clientId,resCategory) {
  return request({
    url: moduleUrl.getResTree,
    method: 'get',
    params: { clientId,resCategory}
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
