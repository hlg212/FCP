import request from '@/utils/request'
const config = {
  api: 'http://localhost:8087/',   // 接口调用IP
  appCode: 'platform', //系统名编码
  timeout: 15000,
  mqttConfig: {
    wsbroker: '192.168.0.81',
    wsport: 5671,
    type: '/ws',
    userName: 'admin',
    password: 'admin',
    clientId: 'platform'
  }
};

// 配置中心获取配置
// 不会写，先暂停

// request({
//   url: moduleUrl.loadDicts,
//   method: 'get',
//   params: { appCode }
// }).then((res) => {
//  sessionStorage.setItem(key, JSON.stringify(recursion(res.data)));
// })

export default config
