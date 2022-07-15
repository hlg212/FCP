import request from '@/utils/request'
//var serviceUrl = "curdie";
var serviceUrl = "http://localhost:8080/curdie/";
var curdieUserUrl ={
  findPage: serviceUrl + "curdieUser/findPage",
  getById: serviceUrl + "curdieUser/getById",
  save: serviceUrl + "curdieUser/save",
  update: serviceUrl + "curdieUser/updateAndReturn",
  deleteById: serviceUrl + "curdieUser/deleteById",
  //exportPage: serviceUrl + "curdieUser/exportPage",
  exportPage: serviceUrl + "curdieUser/exportPage",
  importSave: serviceUrl + "curdieUser/import/upload/save"
}
// export function findPage(query) {
//   return request({
//     url: curdieUserUrl.findPage,
//     method: 'get',
//     params: query
//   })
// }
export function findPage(data) {
  return request({
    url: curdieUserUrl.findPage,
    method: 'post',
    data
  })
}

export function getById(id) {
  return request({
    url: curdieUserUrl.getById,
    method: 'get',
    params: { id }
  })
}


export function save(data) {
  return request({
    url: curdieUserUrl.save,
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: curdieUserUrl.update,
    method: 'post',
    data
  })
}

export function deleteById(ids) {
  return request({
    url: curdieUserUrl.deleteById,
    method: 'post',
    params: { ids }
  })
}

export function exportPage(data) {
  return request({
    url: curdieUserUrl.exportPage,
    method: 'post',
    // responseType: 'blob',
    // headers:{"Accept":"*/*"},
    data
  })
}

export function importSave(data) {
  return request({
    url: curdieUserUrl.importSave,
    method: 'post',
    
    data
  })
}
