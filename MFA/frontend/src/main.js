import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import  "./assets/scripts/axios";


import { library, icon } from '@fortawesome/fontawesome-svg-core'
import { faCamera } from '@fortawesome/free-solid-svg-icons'

library.add(faCamera)

// eslint-disable-next-line no-unused-vars
const camera = icon({ prefix: 'fas', iconName: 'camera' })


createApp(App).use(store).use(router).mount('#app')
