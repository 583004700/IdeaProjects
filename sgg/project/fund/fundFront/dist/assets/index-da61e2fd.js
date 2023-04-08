import{M as k,a as w}from"./index-dcf4c10b.js";import{P as g}from"./index-3913a657.js";import{d as C,k as i,u as D,a7 as a,_ as E,a8 as M,a9 as r,a0 as d,f as n,E as c}from"./vue-854c8149.js";import{a5 as V}from"./antd-cc917134.js";import{_ as R}from"./index-f276d676.js";import"./index-bb5c6944.js";import"./index-817ed082.js";import"./index-cfc96c05.js";import"./useWindowSizeFn-642e749d.js";import"./useRootSetting-33dae5a0.js";import"./useContentViewHeight-e7859710.js";const b=C({components:{MarkDown:k,PageWrapper:g,MarkdownViewer:w,ACard:V},setup(){const e=i(null),o=i(`
# 标题h1

##### 标题h5

**加粗**
*斜体*
~~删除线~~
[链接](https://github.com/vbenjs/vue-vben-admin)
↓分割线↓

---


* 无序列表1
  * 无序列表1.1

1. 有序列表1
2. 有序列表2

* [ ] 任务列表1
* [x] 任务列表2

> 引用示例

\`\`\`js
// 代码块:
(() => {
  var htmlRoot = document.getElementById('htmlRoot');
  var theme = window.localStorage.getItem('__APP__DARK__MODE__');
  if (htmlRoot && theme) {
    htmlRoot.setAttribute('data-theme', theme);
    theme = htmlRoot = null;
  }
})();
\`\`\`

| 表格 | 示例 | 🎉️ |
| --- | --- | --- |
| 1 | 2 | 3 |
| 4 | 5 | 6 |
`);function l(){const t=D(e);if(!t)return;t.getVditor().setTheme("dark","dark","dracula")}function m(t){o.value=t}function s(){o.value=""}return{value:o,toggleTheme:l,markDownRef:e,handleChange:m,clearValue:s}}}),A={class:"mt-2"};function B(e,o,l,m,s,t){const u=a("a-button"),p=a("MarkDown"),f=a("MarkdownViewer"),_=a("a-card"),h=a("PageWrapper");return E(),M(h,{title:"MarkDown组件示例"},{default:r(()=>[d("div",null,[n(u,{onClick:e.toggleTheme,class:"mb-2",type:"primary"},{default:r(()=>[c(" 黑暗主题 ")]),_:1},8,["onClick"]),n(u,{onClick:e.clearValue,class:"mb-2",type:"default"},{default:r(()=>[c(" 清空内容 ")]),_:1},8,["onClick"]),n(p,{value:e.value,"onUpdate:value":o[0]||(o[0]=v=>e.value=v),onChange:e.handleChange,ref:"markDownRef",placeholder:"这是占位文本"},null,8,["value","onChange"])]),d("div",A,[n(_,{title:"Markdown Viewer 组件演示"},{default:r(()=>[n(f,{value:e.value},null,8,["value"])]),_:1})])]),_:1})}const O=R(b,[["render",B]]);export{O as default};
