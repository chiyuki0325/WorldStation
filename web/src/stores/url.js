import {defineStore} from "pinia";

export const useUrlStore = defineStore('url', {
  state: () => ({
    loginUrl: '/oauth2/authorization/smbx-world',
  }),
  getters: {
    getLoginUrl: (state) => state.loginUrl,
  },
  actions: {
    setLoginUrl(url) {
      this.loginUrl = url
    },
    jumpToLogin() {
      window.location.href = this.loginUrl
    }
  },
})
