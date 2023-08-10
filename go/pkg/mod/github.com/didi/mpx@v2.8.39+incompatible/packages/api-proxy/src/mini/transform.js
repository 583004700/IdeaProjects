import { error, getEnvObj, genFromMap, makeMap } from '../common/js'
import getWxToAliApi from './platform/wxToAli'
import getWxToQqApi from './platform/wxToQq'
import getWxToTtApi from './platform/wxToTt'

const fromMap = genFromMap()

function joinName (from = '', to = '') {
  return `${fromMap[from]}_${to}`
}

function transformApi (options) {
  const envObj = getEnvObj()
  const from = options.from
  const to = options.to
  const fromTo = joinName(from, to)
  const wxToAliApi = getWxToAliApi()
  const wxToQqApi = getWxToQqApi()
  const wxToTtApi = getWxToTtApi()
  const platformMap = {
    wx_ali: wxToAliApi,
    wx_qq: wxToQqApi,
    wx_tt: wxToTtApi
  }
  const needProxy = Object.create(null)
  const excludeMap = makeMap(options.exclude)
  const platformApi = platformMap[fromTo] || {}
  Object.keys(envObj).concat(Object.keys(platformApi)).forEach((key) => {
    if (!excludeMap[key]) {
      needProxy[key] = envObj[key] || platformApi[key]
    }
  })
  const result = Object.create(null)
  Object.keys(needProxy).forEach(api => {
    // 非函数不做转换
    if (typeof needProxy[api] !== 'function') {
      result[api] = needProxy[api]
      return
    }

    result[api] = (...args) => {
      let from = options.from
      const to = options.to
      if (args.length > 0) {
        from = args.pop()
        if (typeof from !== 'string' || !fromMap[from]) {
          args.push(from)
          from = options.from
        }
      }

      const fromTo = joinName(from, to)
      if (options.custom[fromTo] && options.custom[fromTo][api]) {
        return options.custom[fromTo][api].apply(this, args)
      } else if (
        platformMap[fromTo] &&
        platformMap[fromTo][api]
      ) {
        return platformMap[fromTo][api].apply(this, args)
      } else if (envObj[api]) {
        return envObj[api].apply(this, args)
      } else {
        error(`当前环境不存在 ${api} 方法`)
      }
    }
  })

  return result
}

export default transformApi
