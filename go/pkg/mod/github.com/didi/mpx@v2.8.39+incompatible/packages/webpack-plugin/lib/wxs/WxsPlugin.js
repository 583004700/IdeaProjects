const WxsTemplatePlugin = require('./WxsTemplatePlugin')
const WxsParserPlugin = require('./WxsParserPlugin')
const WxsModuleIdsPlugin = require('./WxsModuleIdsPlugin')

class WxsPlugin {
  constructor (options = { mode: 'wx' }) {
    this.options = options
  }

  apply (compiler) {
    compiler.hooks.thisCompilation.tap('WxsPlugin', (compilation, { normalModuleFactory }) => {
      new WxsTemplatePlugin(this.options).apply(compilation)
      new WxsModuleIdsPlugin(this.options).apply(compilation)

      compilation.hooks.buildModule.tap('WxsPlugin', (module) => {
        module.wxs = true
      })

      const handler = (parser) => {
        new WxsParserPlugin(this.options).apply(parser)
      }

      normalModuleFactory.hooks.parser
        .for('javascript/auto')
        .tap('WxsPlugin', handler)

      normalModuleFactory.hooks.parser
        .for('javascript/dynamic')
        .tap('WxsPlugin', handler)

      normalModuleFactory.hooks.parser
        .for('javascript/esm')
        .tap('WxsPlugin', handler)
    })
  }
}

module.exports = WxsPlugin
