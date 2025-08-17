import {defineStore} from "pinia";

export const useUserIdStore = defineStore('user_id', {
  state: () => ({
    userId: -1,  // unset
  }),
  getters: {
    getUserId: (state) => {
      return state.userId
    },
    isLoggedIn: (state) => {
      return state.userId !== -1
    },
  },
  actions: {
    setUserId(userId) {
      this.userId = userId
    },
    clearUserId() {
      this.userId = -1
    },
  },
})
