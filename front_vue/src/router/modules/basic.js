
import Layout from '@/layout'

const basicRouter = {
  path: '/basic',
  component: Layout,
  name: 'platform',
  meta: {
    title: '基础平台',
    icon: 'nested'
  },
  children: [
    {
      path: '/basic/app',
      component: () => import('@/views/platform/basic/app/index'), // Parent router-view
      name: 'app',
      meta: { title: '应用管理' },
      //redirect: '/nested/menu1/menu1-1',
    },
    {
      path: '/basic/dict',
      name: 'dict',
      component: () => import('@/views/platform/basic/dict/index'),
      meta: { title: '字典管理' }
    },
    {
      path: '/basic/user',
      name: 'user',
      component: () => import('@/views/platform/basic/user/index'),
      meta: { title: '用户管理' }
    },
    {
      path: '/basic/org',
      name: 'org',
      component: () => import('@/views/platform/basic/org/index'),
      meta: { title: '机构管理' }
    },
    {
      path: '/basic/res',
      name: 'res',
      component: () => import('@/views/platform/basic/res/index'),
      meta: { title: '资源管理' }
    },
    {
      path: '/basic/role',
      name: 'role',
      component: () => import('@/views/platform/basic/role/index'),
      meta: { title: '角色管理' }
    },
    {
      path: '/basic/client',
      name: 'client',
      component: () => import('@/views/platform/basic/client/index'),
      meta: { title: '客户端管理' }
    }
  ]
}

export default basicRouter


// import request from '@/utils/request'
// import config from '@/config'
// //var serviceUrl = "http://localhost:8096/basic/";
// var serviceUrl = "system/";
// var moduleUrl ={
//   getMenuTree: serviceUrl + "auth/getMenuTree"
// }
// var appCode = config.appCode;
// const basicRouter = {};
// request({
//   url: moduleUrl.getMenuTree,
//   method: 'get',
//   params: { appCode }
// }).then((data) => {
//   debugger;
//   for(let res of data )
//   {
    
//   }
// })

// export default basicRouter