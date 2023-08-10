const postcss = require('postcss')
const loadPostcssConfig = require('./load-postcss-config')
const { MPX_ROOT_VIEW, MPX_APP_MODULE_ID } = require('../utils/const')
const trim = require('./plugins/trim')
const rpx = require('./plugins/rpx')
const vw = require('./plugins/vw')
const pluginCondStrip = require('./plugins/conditional-strip')
const scopeId = require('./plugins/scope-id')
const transSpecial = require('./plugins/trans-special')
const { matchCondition } = require('../utils/match-condition')
const parseRequest = require('../utils/parse-request')

module.exports = function (css, map) {
  this.cacheable()
  const cb = this.async()
  const { resourcePath, queryObj } = parseRequest(this.resource)
  const mpx = this.getMpx()
  const id = queryObj.moduleId || queryObj.mid || 'm' + mpx.pathHash(resourcePath)
  const appInfo = mpx.appInfo
  const defs = mpx.defs
  const mode = mpx.mode
  const isApp = resourcePath === appInfo.resourcePath
  const transRpxRulesRaw = mpx.transRpxRules
  const transRpxRules = transRpxRulesRaw ? (Array.isArray(transRpxRulesRaw) ? transRpxRulesRaw : [transRpxRulesRaw]) : []

  const transRpxFn = mpx.webConfig.transRpxFn
  const testResolveRange = (include = () => true, exclude) => {
    return matchCondition(this.resourcePath, { include, exclude })
  }

  const inlineConfig = Object.assign({}, mpx.postcssInlineConfig, { defs })
  loadPostcssConfig(this, inlineConfig).then(config => {
    const plugins = [trim] // init with trim plugin
    const options = Object.assign(
      {
        to: this.resourcePath,
        from: this.resourcePath,
        map: false
      },
      config.options
    )
    // ali平台下处理scoped和host选择器
    if (mode === 'ali') {
      if (queryObj.scoped) {
        plugins.push(scopeId({ id }))
      }
      plugins.push(transSpecial({ id }))
    }

    plugins.push(pluginCondStrip({
      defs
    }))

    for (const item of transRpxRules) {
      const {
        mode,
        comment,
        include,
        exclude,
        designWidth
      } = item || {}

      if (testResolveRange(include, exclude)) {
        // 对同一个资源一旦匹配到，推入一个rpx插件后就不再继续推了
        plugins.push(rpx({ mode, comment, designWidth }))
        break
      }
    }

    if (mpx.mode === 'web') {
      plugins.push(vw({ transRpxFn }))
    }
    // source map
    if (this.sourceMap && !options.map) {
      options.map = {
        inline: false,
        annotation: false,
        prev: map
      }
    }

    plugins.push(...config.plugins) // push user config plugins

    return postcss(plugins)
      .process(css, options)
      .then(result => {
        // ali环境添加全局样式抹平root差异
        if (mode === 'ali' && isApp) {
          result.css += `\n.${MPX_ROOT_VIEW} { display: initial }\n.${MPX_APP_MODULE_ID} { line-height: normal }`
        }

        for (const warning of result.warnings()) {
          this.emitWarning(warning)
        }

        // todo 后续考虑直接使用postcss-loader来处理postcss
        for (const message of result.messages) {
          // eslint-disable-next-line default-case
          switch (message.type) {
            case 'dependency':
              this.addDependency(message.file)
              break

            case 'build-dependency':
              this.addBuildDependency(message.file)
              break

            case 'missing-dependency':
              this.addMissingDependency(message.file)
              break

            case 'context-dependency':
              this.addContextDependency(message.file)
              break

            case 'dir-dependency':
              this.addContextDependency(message.dir)
              break

            case 'asset':
              if (message.content && message.file) {
                this.emitFile(message.file, message.content, message.sourceMap, message.info)
              }
          }
        }

        const map = result.map && result.map.toJSON()
        cb(null, result.css, map)
        return null // silence bluebird warning
      })
  }).catch(e => {
    console.error(e)
    cb(e)
  })
}
