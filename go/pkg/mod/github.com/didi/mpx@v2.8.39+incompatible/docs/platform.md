# 多平台支持

目前mpx支持了微信、支付宝、百度、qq、头条小程序平台。

## template增强特性

不同平台上的模板增强指令按照平台的指令风格进行设计，文档和代码示例为了方便统一采用微信小程序下的书写方式。

模板增强指令对应表：

增强指令|微信|支付宝|百度|qq|头条
----|----|----|----|----|----
双向绑定|wx:model|a:model|s-model|qq:model|tt:model
双向绑定辅助属性|wx:model-prop|a:model-prop|s-model-prop|qq:model-prop|tt:model-prop
双向绑定辅助属性|wx:model-event|a:model-event|s-model-event|qq:model-event|tt:model-event
双向绑定辅助属性|wx:model-value-path|a:model-value-path|s-model-value-path|qq:model-value-path|tt:model-value-path
动态样式绑定|wx:class|a:class|s-class|qq:class|暂不支持
动态样式绑定|wx:style|a:style|s-style|qq:style|暂不支持
获取节点/组件实例|wx:ref|a:ref|s-ref|qq:ref|tt:ref
显示/隐藏|wx:show|a:show|s-show|qq:show|tt:show

## script增强特性

增强字段|微信|支付宝|百度|qq|头条
----|----|----|----|----|----
computed|支持|支持|支持|支持|部分支持，无法作为props传递(待头条修复生命周期执行顺序可完整支持)
watch|支持|支持|支持|支持|支持
mixins|支持|支持|支持|支持|支持

## style增强特性

无平台差异

## json增强特性

增强字段|微信|支付宝|百度|qq|头条
----|----|----|----|----|----
packages|支持|支持|支持|支持|部分支持，无法分包


# 跨平台编译

自2.0版本开始，mpx开始支持跨小程序平台编译，不同于常规跨平台框架重新定义一套DSL的方式，mpx支持基于现有平台的源码编译为其他已支持平台的目标代码。跨平台编译能力依赖于mpx的多平台支持，目前mpx已经支持将微信小程序跨平台编译为支付宝、百度、qq和头条小程序。

## 使用方法

如果你是使用`mpx init xxx`新生成的项目，package.json里script部分有`npm run build:cross`，直接执行`npm run build:cross`（watch同理），如果仅需构建某几个平台的，可以修改该script，按已有的格式删除或增添某些某些平台

如果你是自行搭建的mpx项目，你只需要进行简单的配置修改，打开项目的webpack配置，找到@mpxjs/webpack-plugin的声明位置，传入mode和srcMode参数即可，示例如下

```js
// 下面的示例配置能够将mpx微信小程序源码编译为支付宝小程序
new MpxwebpackPlugin({
  // mode为mpx编译的目标平台，可选值有(wx|ali|swan|qq|tt)
  mode: 'ali',
  // srcMode为mpx编译的源码平台，目前仅支持wx   
  srcMode: 'wx' 
})
```

## 跨平台差异抹平

为了实现小程序的跨平台编译，我们在编译和运行时做了很多工作以抹平小程序开发中各个方面的跨平台差异

### 模板语法差异抹平

对于通用指令/事件处理的差异，mpx提供了统一的编译转换抹平操作；而对于平台组件和组件属性的差异，我们也在力所能及的范围内进行了转换抹平，对于平台差异性过大无法转换的部分会在编译阶段报错指出。

### 组件/页面对象差异抹平

不同平台间组件/页面对象的差异主要体现在生命周期上，我们在支持多平台能力时已经将不同平台的生命周期映射到mpx框架的一套内部生命周期中，基于这个统一的映射，不同平台的生命周期差异也得到了抹平。

此外，我们还进行了一系列运行时增强来模拟微信平台中提供而其他平台中未提供的能力，例如：
* 在支付宝组件实例中提供了this.triggerEvent方法模拟微信中的自定义组件事件；
* 提供了this.selectComponent/this.selectAllComponents方法模拟微信中获取子组件实例的能力；
* 重写了createSelectorQuery方法抹平了微信/支付宝平台间的使用差异；
* 转换抹平了微信/支付宝中properties定义的差异；
* 利用mpx本身的数据响应能力模拟了微信中的observers/property observer能力等;
* 提供了this.getRelationNodes方法并支持了微信中组件间关系relations的能力

对于原生小程序组件的转换，还会进行一些额外的抹平，已兼容一些已有的原生组件库，例如：
* 将支付宝组件中的props数据挂载到this.data中以模拟微信平台中的表现；
* 利用mpx本身的mixins能力模拟微信中的behaviors能力。

对于一些无法进行模拟的跨平台差异，会在运行时进行检测并报错指出，例如微信转支付宝时使用moved生命周期等。

### json配置差异抹平

类似于模板语法，会在编译阶段进行转换抹平，无法转换的部分会在编译阶段报错指出。

### api调用差异抹平

对于api调用，mpx提供了一个api调用代理插件来抹平跨平台api调用的差异，使用时需要在项目中安装使用`@mpxjs/api-proxy`，并且在调用小程序api时统一使用mpx对象进行调用，示例如下：

```js
// 请在app.mpx中安装mpx插件
import mpx, { createApp } from '@mpxjs/core'
import apiProxy from '@mpxjs/api-proxy'

mpx.use(apiProxy, {
  // 开启api promisify
  usePromise: true
})

createApp({
  onLaunch() {
    // 调用小程序api时使用mpx.xxx，而不要使用wx.xxx或者my.xxx
    mpx.request({url: 'xxx'})
  }
})
```

对于无法转换抹平的api调用会在运行时阶段报错指出。

### webview bridge差异抹平

对于不同平台中webview bridge的调用，我们封装了一个`@mpxjs/webview-bridge`包，用于抹平不同小程序平台中webview bridge的差异，简单使用示例如下：

```js
import mpx from '@mpxjs/webview-bridge'

mpx.navigateBack()
```

对于无法转换抹平的bridge调用会在运行时阶段报错指出，详细使用指南请查看[webview-bridge](/extend/index.md#webview-bridge)

## 跨平台条件编译

Mpx跨平台编译的原则在于，`能转则转，转不了则报错提示`，对于无法抹平差异的部分，我们提供了完善的跨平台条件编译机制便于用户处理因平台差异而无法相互转换的部分，也能够用于实现具有平台差异性的业务逻辑。

mpx中我们支持了三种维度的条件编译，分别是文件维度，区块维度和代码维度，其中，文件维度和区块维度主要用于处理一些大块的平台差异性逻辑，而代码维度主要用于处理一些局部简单的平台差异。

### 文件维度条件编译

文件维度条件编译简单的来说就是文件为维度进行跨平台差异代码的编写，例如在微信->支付宝的项目中存在一个业务地图组件map.mpx，由于微信和支付宝中的原生地图组件标准差异非常大，无法通过框架转译方式直接进行跨平台输出，这时你可以在相同的位置新建一个map.ali.mpx，在其中使用支付宝的技术标准进行开发，编译系统会根据当前编译的mode来加载对应模块，当mode为ali时，会优先加载map.ali.mpx，反之则会加载map.mpx。

文件维度条件编译能够与webpack alias结合使用，对于npm包的文件我们并不方便在原本的文件位置创建.ali的条件编译文件，但我们可以通过webpack alias在相同位置创建一个`虚拟的`.ali文件，并将其指向项目中的其他文件位置。

```js
  // 对于npm包中的文件依赖
  import npmModule from 'somePackage/lib/index'
  
  // 配置以下alias后，当mode为ali时，会优先加载项目目录中定义的projectRoot/somePackage/lib/index文件
  const webpackConf = {
    resolve: {
      alias: {
        'somePackage/lib/index.ali': 'projectRoot/somePackage/lib/index'
      }
    }
  }
```

### 区块维度条件编译

在.mpx单文件中一般存在template、js、stlye、json四个区块，mpx的编译系统支持以区块为维度进行条件编译，只需在区块标签中添加`mode`属性定义该区块的目标平台即可，示例如下：

```html
<!--编译mode为ali时使用如下区块-->
<template mode="ali">
<!--该区块中的所有代码需采用支付宝的技术标准进行编写-->
  <view>支付宝环境</view>
</template>

<!--其他编译mode时使用如下区块-->
<template>
  <view>其他环境</view>
</template>
```

### 代码维度条件编译

如果只有局部的代码存在跨平台差异，mpx同样支持在代码内使用if/else进行局部条件编译，用户可以在js代码和template插值中访问`__mpx_mode__`获取当前编译mode，进行平台差异逻辑编写，js代码中使用示例如下。

除了 `__mpx_mode__` 这个默认插值以外，有别的环境变量需要的话可以在mpx.plugin.conf.js里通过defs进行配置。

```js
if(__mpx_mode__ === 'ali') {
  // 执行支付宝环境相关逻辑
} else {
  // 执行其他环境相关逻辑
}
```
template代码中使用示例如下

```html
<!--此处的__mpx_mode__不需要在组件中声明数据，编译时会基于当前编译mode进行替换-->
<view wx:if="{{__mpx_mode__ === 'ali'}}">支付宝环境</view>
<view wx:else>其他环境</view>
```

JSON中的条件编译（注意，这个依赖JSON的动态方案，得通过name="json"这种方式来编写，其实写的是js代码，最终module.exports导出一个可json化的对象即可）：
```html
<script name="json">
const pages = __mpx_mode__ === 'wx' ? [
  'main/xxx',
  'sub/xxx'
] : [
  'test/xxx'
] // 可以为不同环境动态书写配置
module.exports = {
  usingComponents: {
    aComponents: '../xxxxx' // 可以打注释 xxx组件
  }
}
</script>
```

样式的条件编译：
```css
/*
  @mpx-if (
      __mpx_mode__ === 'wx' ||
      __mpx_mode__ === 'qq'
  )
*/
  /* @mpx-if (__mpx_mode__ === 'wx') */
  wx {
    background: green;
  }
  /*
    @mpx-elif (__mpx_mode__ === 'qq')
  */
  qq {
    background: black;
  }
  /* @mpx-endif */

  /* @mpx-if (__mpx_mode__ === 'swan') */
  swan {
    background: cyan;
  }
  /* @mpx-endif */
  always {
    background: white;
  }
/*
  @mpx-else
*/
other {
  /* @mpx-if (__mpx_mode__ === 'swan') */
  background: blue;
  /* @mpx-else */
  background: red;
  /* @mpx-endif */
}
/*
  @mpx-endif
*/
```

## 其他注意事项

* 当目标平台为支付宝时，需要启用支付宝最新的component2编译才能保障框架正常工作，关于component2[点此查看详情](https://docs.alipay.com/mini/framework/custom-component-overview)；
* 跨平台源码中自定义组件的标签名不能使用驼峰形式`myComponent`，请使用横杠形式`my-component`来书写；
* 生成的目标代码中文件名和文件夹名不能带有`@`符号，目前媒体文件和原生自定义组件在编译时不会修改文件名，需要重点关注。

# 跨平台输出web

从2.3.0版本开始，Mpx开始支持将已有项目跨平台输出web平台中运行的能力，由于该能力目前还处于持续开发阶段，目前仅支持将一些简单的运营类的小程序输出为web项目，无法直接转换大型复杂项目，我们会持续对输出web的能力进行完善和补全，以提高其适用范围和开发体验。

## 技术实现

与小程序平台间的差异相比，web平台和小程序平台间的差异要大很多，小程序相当于是基于web技术的上层封装，所以不同于我们跨平台输出其他小程序平台时以编译转换为主的思路，在输出web时，我们更多地采用了封装的方式来抹平组件/Api和底层环境的差异，与当前大部分的跨平台框架相仿。但与当前大部分跨平台框架以web MVVM框架为base输出到小程序上运行的思路不同，我们是以Mpx小程序增强语法为基础输出到web中运行，前面说过小程序本身是基于web技术进行的实现，小程序->web的转换在可行性和兼容性上会好一些。

在具体实现上，Mpx项目输出到web中运行时在组件化和路由层面都是基于Vue生态实现，所以可以将Mpx的跨端输出产物整合到既有的Vue项目中，或者在条件编译中直接使用Vue语法进行web端的实现。

## 使用方法

使用@mpxjs/cli创建新项目时选择跨平台并选择输出web后，即可生成可输出web的示例项目，运行`npm run serve:web`，就会在dist/web下输出构建后的web项目，并启动静态服务预览运行。

## 支持范围
目前输出web的能力仍处于持续开发阶段，现阶段支持的小程序技术能力范围有限，下列表格中显示了当前版本中已支持的能力范围

### 模板指令
指令名称|是否支持
:----|----
Mustache数据绑定|是
wx:for|是
wx:for-item|是
wx:for-index|是
wx:key|是
wx:if|是
wx:elif|是
wx:else|是
wx:model|是
wx:model-prop|是
wx:model-event|是
wx:model-value-path|是
wx:model-filter|是
wx:class|是
wx:style|是
wx:ref|是
wx:show|是

### 事件绑定方式

绑定方式|是否支持
:----|----
bind|是
catch|是
capture-bind|是
capture-catch|是

### 事件名称

事件名称|是否支持
:----|----
tap|是
longpress|是
longtap|是

web同名事件默认全部支持，已支持组件的特殊事件默认为支持，不支持的情况下会在编译时抛出异常

### 基础组件

组件名称|是否支持
:----|----
view|是
cover-view|是
scroll-view|是
progress|是
navigator|是
swiper|是
swiper-item|是
text|是
image|是
block|是
form|是
input|是
textarea|是
button|是
checkbox|是
checkbox-group|是
radio|是
radio-group|是
picker|是

### 生命周期

声明周期名称|是否支持
:----|----
onLaunch|是
onLoad|是
onReady|是
onShow|是
onHide|是
onUnload|是
onError|是
created|是
attached|是
ready|是
detached|是
updated|是

### 组件配置


配置项|支持度
:----|----
properties|部分支持，observer不支持，请使用watch代替
data|支持
watch|支持
computed|支持
methods|支持
mixins|支持
observers|不支持，请使用watch代替
behaviors|不支持，请使用mixins代替
relations|部分支持，linkChanged、target不支持

### 组件API

api名称|支持度
:----|----
triggerEvent|支持
$nextTick|支持
createSelectorQuery/selectComponent|支持


### 全局API

api名称|支持度
:----|----
navigateTo|支持
navigateBack|支持
redirectTo|支持
request|支持
connectSocket|支持
SocketTask|支持
EventChannel|支持
createSelectorQuery|支持
base64ToArrayBuffer|支持
arrayBufferToBase64|支持
nextTick|支持
set|支持
setNavigationBarTitle|支持
setNavigationBarColor|支持
setStorage|支持
setStorageSync|支持
getStorage|支持
getStorageSync|支持
getStorageInfo|支持
getStorageInfoSync|支持
removeStorage|支持
removeStorageSync|支持
clearStorage|支持
clearStorageSync|支持
getSystemInfo|支持
getSystemInfoSync|支持
showModal|支持
showToast|支持
hideToast|支持
showLoading|支持
hideLoading|支持
onWindowResize|支持
offWindowResize|支持

### JSON配置
配置项|是否支持
:----|----
pages|是
usingComponents|是
packages|是
subpackages|是
navigationBarTitleText|是

### 拓展能力
能力|是否支持
:---|---
fetch|是
i18n|是




