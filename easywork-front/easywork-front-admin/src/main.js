
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
//引入element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/base.scss'
import "@/assets/icon/iconfont.css"
import VueCookies from 'vue-cookies'
import Request from "@/utils/Request"
import Message from "@/utils/Message"
import Confirm from './utils/Confirm'
import Verify from './utils/Verify'
import PermissionCode from './utils/PermissionCode'

import Dialog from './components/Dialog.vue'
import Table from './components/Table.vue'
import Cover from './components/Cover.vue'
import CoverUpload from './components/CoverUpload.vue'


const app = createApp(App)

app.use(ElementPlus)
app.use(router)

app.component('Dialog', Dialog)
app.component('Table', Table)
app.component('Cover', Cover)
app.component('CoverUpload', CoverUpload)

app.config.globalProperties.Request = Request;
app.config.globalProperties.VueCookies = VueCookies;
app.config.globalProperties.Message = Message;
app.config.globalProperties.PermissionCode = PermissionCode;
app.config.globalProperties.Confirm = Confirm;
app.config.globalProperties.Verify = Verify;
app.config.globalProperties.globalInfo = {
    avatarUrl: "/api/file/getAvatar/",
    imageUrl: "/api/file/getImage/"
}


//自定义指令，校验权限
app.directive('has', {
    mounted: (el, binding, vnode) => {
        let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
        let permissionCodeList = userInfo.permissionCodeList
        permissionCodeList = permissionCodeList == undefined ? [] : permissionCodeList;
        if (!permissionCodeList.includes(binding.value)) {
            el.parentNode.removeChild(el)
        }
    }
})

app.mount('#app')
