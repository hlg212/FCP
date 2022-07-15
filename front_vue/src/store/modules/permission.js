import { asyncRoutes, constantRoutes } from '@/router'
import { getMenuTree,getAppPermissions } from '@/api/user'
import config from '@/config'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}


function resToRoute(resDatas)
{
  let children = [];
  for( let res of resDatas )
  {
    let route = {};
    if( res.path ){
      route["path"]= res.path;
      route["name"]= res.name;
      route["component"]= loadView(res.path);
      //route["component"]= (resolve) => require(["@" + res.path],resolve ) ;
    }
    else{
      route["path"] = res.name;
      route["component"] = Layout;
    }
    
    route["meta"]={title: res.name };
    children.push(route);
    if( res.children )
    {
      route.children = resToRoute(res.children);
    }
  }
  return children;
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
     
      let appRoutes = [];
      getMenuTree(config.appCode).then(response => {
         appRoutes = resToRoute(response.data);
         debugger
         let accessedRoutes = appRoutes.concat(asyncRoutes);
         commit('SET_ROUTES', accessedRoutes)
         resolve(accessedRoutes)
      });
     
      // 这里请求后台，设置页面路由
      // if (roles.includes('admin')) {
      //   accessedRoutes = asyncRoutes || []
      // } else {
      //   accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      // }
     
    })
  }
}

export const loadView = (view) => { // 路由懒加载
  // return () => import(`@/views/${view}`)//不知道为什么不行
  // 必须使用 @/views  带目录名称；
  // 不能使用 @${view}
  return resolve => require([`@/views${view}`], resolve)//可以解决
}


// const actions = {
//   generateRoutes({ commit }, roles) {
//     return new Promise(resolve => {
//       let accessedRoutes
//       if (roles.includes('admin')) {
//         accessedRoutes = asyncRoutes || []
//       } else {
//         accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
//       }
//       commit('SET_ROUTES', accessedRoutes)
//       resolve(accessedRoutes)
//     })
//   }
// }

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
