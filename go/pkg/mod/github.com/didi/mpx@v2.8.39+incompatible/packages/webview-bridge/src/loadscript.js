function loadScript (url, { time = 5000, crossOrigin = false } = {}) {
  function request () {
    return new Promise((resolve, reject) => {
      const sc = document.createElement('script')
      sc.type = 'text/javascript'
      sc.async = 'async'

      // 可选地增加 crossOrigin 特性
      if (crossOrigin) {
        sc.crossOrigin = 'anonymous'
      }

      sc.onload = sc.onreadystatechange = function () {
        if (!this.readyState || /^(loaded|complete)$/.test(this.readyState)) {
          resolve()
          sc.onload = sc.onreadystatechange = null
        }
      }

      sc.onerror = function () {
        reject(new Error(`load ${url} error`))
        sc.onerror = null
      }

      sc.src = url
      document.getElementsByTagName('head')[0].appendChild(sc)
    })
  }

  function timeout () {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        reject(new Error(`load ${url} timeout`))
      }, time)
    })
  }

  return Promise.race([request(), timeout()])
}

export default loadScript
