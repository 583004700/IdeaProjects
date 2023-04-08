var p=Object.defineProperty,c=Object.defineProperties;var f=Object.getOwnPropertyDescriptors;var s=Object.getOwnPropertySymbols;var u=Object.prototype.hasOwnProperty,v=Object.prototype.propertyIsEnumerable;var r=(e,o,t)=>o in e?p(e,o,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[o]=t,n=(e,o)=>{for(var t in o||(o={}))u.call(o,t)&&r(e,t,o[t]);if(s)for(var t of s(o))v.call(o,t)&&r(e,t,o[t]);return e},i=(e,o)=>c(e,f(o));import{r as C,a as b}from"./index-ced00421.js";import _ from"./PreviewCode-b94c41bc.js";import{d as w,r as D,e as h,H as M,a7 as m,_ as J,a8 as V,a9 as $,f as x}from"./vue-854c8149.js";import{K as y}from"./antd-cc917134.js";import{_ as A}from"./index-04297947.js";import"./index-c2ff991c.js";import"./useWindowSizeFn-68619b88.js";import"./useCopyToClipboard-f3a47d7e.js";const g=`<template>
  <div>
    <v-form-create
      :formConfig="formConfig"
      :formData="formData"
      v-model="fApi"
    />
    <a-button @click="submit">提交</a-button>
  </div>
</template>
<script>

export default {
  name: 'Demo',
  data () {
    return {
      fApi:{},
      formData:{},
      formConfig: `;let j=`
    }
  },
  methods: {
    async submit() {
      const data = await this.fApi.submit()
      console.log(data)
     }
  }
}
<\/script>`;const k=w({name:"CodeModal",components:{PreviewCode:_,Modal:y},setup(){const e=D({visible:!1,jsonData:{}}),o=a=>{a.schemas&&b(a.schemas),e.visible=!0,e.jsonData=a},t=h(()=>g+JSON.stringify(C(e.jsonData),null,"	")+j);return i(n({},M(e)),{editorVueJson:t,showModal:o})}});function N(e,o,t,a,P,B){const l=m("PreviewCode"),d=m("Modal");return J(),V(d,{title:"代码",footer:null,visible:e.visible,onCancel:o[0]||(o[0]=E=>e.visible=!1),wrapClassName:"v-code-modal",style:{top:"20px"},width:"850px",destroyOnClose:!0},{default:$(()=>[x(l,{editorJson:e.editorVueJson,fileFormat:"vue"},null,8,["editorJson"])]),_:1},8,["visible"])}const G=A(k,[["render",N]]);export{G as default};
