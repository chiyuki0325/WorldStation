import {defineStore} from "pinia"
import {computed, defineAsyncComponent, ref} from "vue"

const routes = {
  '/': () => import('../views/MapListView.vue'),
  '/404': () => import('../views/NotFoundView.vue'),
  '/upload/worldmap': () => import('../views/UploadMapView.vue'),
  '/upload/image': () => import('../views/UploadImageView.vue'),
}

export const useRouterStore = defineStore('router', () => {
  // 高端万兆企业级路由器
  // powered by 威优易(TM)
  const route = ref(window.location.pathname)

  const view = computed(() => {
    const componentLoader = routes[route.value] || routes['/404']
    return defineAsyncComponent({
      loader: componentLoader,
      errorComponent: routes['/404'],
      delay: 200,
    })
  })

  function push(path) {
    if (path !== route.value) {
      if (routes[path]) {
        window.history.pushState({}, '', path)
        route.value = path
      } else {
        push('/404')
      }
    }
  }

  function pop() {
    route.value = window.location.pathname
  }

  return {route, view, push, pop}
})
