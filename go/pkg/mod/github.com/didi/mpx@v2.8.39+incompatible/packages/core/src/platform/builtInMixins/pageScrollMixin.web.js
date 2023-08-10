import { error } from '@mpxjs/utils'
import MpxScroll from '../../helper/MpxScroll'
import { getScrollTop } from '../../helper/MpxScroll/dom'

let ms

function refreshMs () {
  if (ms) ms.destroy()
  try {
    global.__ms = ms = new MpxScroll()
    return true
  } catch (e) {
    error('MpxScroll init error, please check.', undefined, e)
  }
}

let loading = null

function showLoading (vm) {
  const { backgroundColor = 'transparent', backgroundTextStyle = 'dark' } = vm.$options.__mpxPageConfig
  loading = document.createElement('div')
  loading.className = 'pull-down-loading'
  loading.style.cssText = `background-color: ${backgroundColor};`
  const dot = document.createElement('div')
  dot.className = `dot-flashing ${backgroundTextStyle}`
  loading.append(dot)
  vm.$el.prepend(loading)
}

function hideLoading (vm) {
  if (loading) {
    vm.$el.removeChild(loading)
    loading = null
  }
}

export default function pageScrollMixin (mixinType) {
  if (mixinType !== 'page') {
    return
  }
  return {
    mounted () {
      this.__lastScrollY = 0
    },
    activated () {
      if (!refreshMs()) {
        return
      }

      const { disableScroll, enablePullDownRefresh } = this.$options.__mpxPageConfig

      // 下拉刷新
      if (enablePullDownRefresh) {
        showLoading(this)
        ms.usePullDownRefresh()
        ms.hooks.pullingDown.on(this.__mpxPullDownHandler)
      }

      // 页面滚动
      if (!disableScroll) {
        ms.pageScrollTo({
          scrollTop: this.__lastScrollY,
          duration: 0
        })

        if (this.onPageScroll || this.onReachBottom) {
          ms.useScroll()
          ms.hooks.scroll.on(this.__mpxPageScrollHandler)
        }
      } else {
        document.body.style.overflow = 'hidden'
      }
    },
    deactivated () {
      if (ms) {
        this.__lastScrollY = getScrollTop()
        ms.destroy()
        hideLoading(this)
      }
    },
    beforeDestroy () {
      if (ms) {
        ms.destroy()
        hideLoading(this)
      }
    },
    methods: {
      __mpxPullDownHandler (autoStop = false, isRefresh = false) {
        this.__pullingDown = true
        // 同微信保持一致
        // 如果是手动触摸下拉，3s 后用户还没有调用过 __stopPullDownRefresh，则自动调用关闭 pullDown
        // 如果是手动调用 startPullDownRefresh 的 api，则一直处于 pull down 状态，除非用户手动调用 stopPullDownRefresh
        if (isRefresh) {
          this.__clearPullDownTimer()
        }
        if (autoStop) {
          this.__mpxPullDownTimer = setTimeout(() => {
            if (this.__pullingDown) {
              this.__stopPullDownRefresh()
            }
          }, 3000)
        }
        this.onPullDownRefresh && this.onPullDownRefresh()
      },
      __stopPullDownRefresh () {
        this.__pullingDown = false
        if (this.$options.__mpxPageConfig.enablePullDownRefresh && ms) {
          ms.stopPullDownRefresh()
          this.__clearPullDownTimer()
        }
      },
      __startPullDownRefresh () {
        if (!this.__pullingDown && this.$options.__mpxPageConfig.enablePullDownRefresh && ms) {
          ms.startPullDownRefresh()
        }
      },
      __mpxPageScrollHandler (e) {
        const { onReachBottomDistance = 50 } = this.$options.__mpxPageConfig
        if (this.onPageScroll) {
          const _e = {}
          Object.defineProperty(_e, 'scrollTop', {
            configurable: false,
            enumerable: true,
            get: () => e.scrollTop
          })
          this.onPageScroll(_e)
        }
        if (this.onReachBottom) {
          ms.onReachBottom(onReachBottomDistance, this.onReachBottom)
        }
      },
      __clearPullDownTimer () {
        if (this.__mpxPullDownTimer) {
          clearTimeout(this.__mpxPullDownTimer)
          this.__mpxPullDownTimer = null
        }
      }
    }
  }
}
