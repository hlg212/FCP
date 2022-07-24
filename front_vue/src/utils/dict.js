import request from '@/utils/request'

//var serviceUrl = "http://localhost:8096/basic/";
var serviceUrl = "/basic/";
var moduleUrl ={
  loadDicts: serviceUrl + "api/dict/getAllDicts"
}
const appCodes = ["basic","system","admin","dam"];
const dictKeyPrefix = "dictKey_";

const DictHelper = {
  getDict: function(appCode,parentDictCode) {
  
    if (appCode && parentDictCode) {
      var json = sessionStorage.getItem(dictKeyPrefix + appCode);
      try {
        return JSON.parse(json)[parentDictCode]
      } catch (err) {
        throw new Error(err) 
      }
    } else {
      throw new Error('请传入正确的参数值')
    }
  },
  getDicts: function(appCode,parentDictCode) {
      var dict = DictHelper.getDict(appCode,parentDictCode);
      if( dict ){
        return dict["children"];
      }
      return null;
  },
  getDictsMap: function(appCode,parentDictCode) {
    var dicts = DictHelper.getDicts(appCode,parentDictCode);
    var map = {};
    for( let dict of dicts )
    {
      map[dict.value] = dict.name;
    }
    return map;
},
  
  getDictName: function(appCode,parentDictCode,dictValue) {
    var dictCode = parentDictCode + "#" + dictValue; 
    var dict = DictHelper.getDict(appCode,dictCode);    
    return dict["name"];
  }
};

function loadDicts(appCode) {
  var key = dictKeyPrefix + appCode;
  var have = sessionStorage.getItem(key);
  if( have === null || have === undefined)
  {   
    request({
    url: moduleUrl.loadDicts,
    method: 'get',
    params: { appCode }
  }).then((res) => {
   sessionStorage.setItem(key, JSON.stringify(recursion(res.data)));
  })
}
}

function recursion(list) {
  var item = {}

  function recursionObj(data) {
    if(data){
      for (let i = 0; i < data.length; i++) {
        item[data[i].key] = data[i]
        if (data[i].children && data[i].children.length > 0) {
          recursionObj(data[i].children)
        }
      }
    }
    
  }
  recursionObj(list)
  // console.log(item, '基础字典树根据KEy值递归键值对')
  return item
}

function init()
{
  for( let appCode of appCodes )
  {
    loadDicts(appCode);
  }
}

init();


export default DictHelper

