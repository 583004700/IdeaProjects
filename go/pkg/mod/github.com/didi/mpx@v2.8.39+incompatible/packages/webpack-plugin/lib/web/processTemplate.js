const templateCompiler = require('../template-compiler/compiler')
const genComponentTag = require('../utils/gen-component-tag')
const addQuery = require('../utils/add-query')
const parseRequest = require('../utils/parse-request')
// const { matchCondition } = require('../utils/match-condition')

function calculateRootEleChild (arr) {
  if (!arr) {
    return 0
  }
  return arr.reduce((total, item) => {
    if (item.type === 1) {
      if (item.tag === 'template') {
        total += calculateRootEleChild(item.children)
      } else {
        total += 1
      }
    }
    return total
  }, 0)
}

module.exports = function (template, {
  loaderContext,
  // hasScoped,
  hasComment,
  isNative,
  srcMode,
  moduleId,
  ctorType,
  usingComponents,
  componentGenerics
}, callback) {
  const mpx = loaderContext.getMpx()
  const {
    mode,
    defs,
    wxsContentMap,
    decodeHTMLText,
    externalClasses,
    checkUsingComponents
    // autoVirtualHostRules
  } = mpx
  const { resourcePath } = parseRequest(loaderContext.resource)
  const builtInComponentsMap = {}

  let wxsModuleMap, genericsInfo
  let output = '/* template */\n'

  if (ctorType === 'app') {
    template = {
      tag: 'template',
      content: '<div class="app"><mpx-keep-alive><router-view class="page"></router-view></mpx-keep-alive></div>'
    }
    builtInComponentsMap['mpx-keep-alive'] = {
      resource: addQuery('@mpxjs/webpack-plugin/lib/runtime/components/web/mpx-keep-alive.vue', { isComponent: true })
    }
  }

  if (template) {
    // 由于远端src template资源引用的相对路径可能发生变化，暂时不支持。
    if (template.src) {
      return callback(new Error('[mpx loader][' + loaderContext.resource + ']: ' + 'template content must be inline in .mpx files!'))
    }
    if (template.lang) {
      return callback(new Error('[mpx loader][' + loaderContext.resource + ']: ' + 'template lang is not supported in trans web mode temporarily, we will support it in the future!'))
    }

    output += genComponentTag(template, (template) => {
      if (ctorType === 'app') {
        return template.content
      }
      if (template.content) {
        const templateSrcMode = template.mode || srcMode
        const { root, meta } = templateCompiler.parse(template.content, {
          warn: (msg) => {
            loaderContext.emitWarning(
              new Error('[template compiler][' + loaderContext.resource + ']: ' + msg)
            )
          },
          error: (msg) => {
            loaderContext.emitError(
              new Error('[template compiler][' + loaderContext.resource + ']: ' + msg)
            )
          },
          usingComponents,
          hasComment,
          isNative,
          isComponent: ctorType === 'component',
          mode,
          srcMode: templateSrcMode,
          defs,
          decodeHTMLText,
          externalClasses,
          // todo 后续输出web也采用mpx的scoped处理
          hasScoped: false,
          moduleId,
          filePath: resourcePath,
          i18n: null,
          checkUsingComponents,
          // web模式下全局组件不会被合入usingComponents中，故globalComponents可以传空
          globalComponents: [],
          // web模式下实现抽象组件
          componentGenerics
          // todo 后续输出web也基于autoVirtualHostRules决定是否添加root wrapper
          // hasVirtualHost: matchCondition(resourcePath, autoVirtualHostRules)
        })
        if (meta.wxsModuleMap) {
          wxsModuleMap = meta.wxsModuleMap
        }
        if (meta.wxsContentMap) {
          for (const module in meta.wxsContentMap) {
            wxsContentMap[`${resourcePath}~${module}`] = meta.wxsContentMap[module]
          }
        }
        if (meta.builtInComponentsMap) {
          Object.keys(meta.builtInComponentsMap).forEach((name) => {
            builtInComponentsMap[name] = {
              resource: addQuery(meta.builtInComponentsMap[name], { isComponent: true })
            }
          })
        }
        if (meta.genericsInfo) {
          genericsInfo = meta.genericsInfo
        }
        // 输出H5有多个root element时, 使用mpx-root-view标签包裹
        // todo 后续输出web也基于autoVirtualHostRules决定是否添加root wrapper
        if (root.tag === 'temp-node') {
          const childLen = calculateRootEleChild(root.children)
          if (childLen >= 2) {
            root.tag = 'div'
            templateCompiler.addAttrs(root, [{
              name: 'class',
              value: 'mpx-root-view'
            }])
          }
        }
        return templateCompiler.serialize(root)
      }
    })
    output += '\n\n'
  }

  callback(null, {
    output,
    builtInComponentsMap,
    genericsInfo,
    wxsModuleMap
  })
}
