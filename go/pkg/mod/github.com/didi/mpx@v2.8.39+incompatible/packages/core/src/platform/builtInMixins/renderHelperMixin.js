import { isObject } from '@mpxjs/utils'

export default function renderHelperMixin () {
  return {
    methods: {
      _i (val, handler) {
        let i, l, keys, key
        if (Array.isArray(val) || typeof val === 'string') {
          for (i = 0, l = val.length; i < l; i++) {
            handler.call(this, val[i], i)
          }
        } else if (typeof val === 'number') {
          for (i = 0; i < val; i++) {
            handler.call(this, i + 1, i)
          }
        } else if (isObject(val)) {
          keys = Object.keys(val)
          for (i = 0, l = keys.length; i < l; i++) {
            key = keys[i]
            handler.call(this, val[key], key, i)
          }
        }
      },
      _c (key, value) {
        this.__mpxProxy.renderData[key] = value
        return value
      },
      _r () {
        this.__mpxProxy.renderWithData()
      }
    }
  }
}
