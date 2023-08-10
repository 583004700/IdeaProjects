import { webHandleSuccess, webHandleFail } from '../../../common/js'
import { nextTick } from '../next-tick'

export function pageScrollTo (options) {
  nextTick(() => {
    const ms = global.__ms
    const { success, fail, complete } = options

    if (!ms) {
      return webHandleFail({
        errMsg: 'pageScrollTo:fail'
      }, fail, complete)
    }

    ms.pageScrollTo(options)
    webHandleSuccess({
      errMsg: 'pageScrollTo:ok'
    }, success, complete)
  })
}
