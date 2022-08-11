import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

loadFonts()

createApp(App)
  .use(router)
  .use(createPinia().use(piniaPluginPersistedstate))
  .use(vuetify)
  .mount('#app')