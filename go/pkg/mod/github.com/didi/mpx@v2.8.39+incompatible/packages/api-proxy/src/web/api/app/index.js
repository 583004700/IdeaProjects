global.__mpxAppCbs = global.__mpxAppCbs || {
  show: [],
  hide: [],
  error: []

}

function onError (callback) {
  global.__mpxAppCbs.error.push(callback)
}

function offError (callback) {
  const cbs = global.__mpxAppCbs.error
  const index = cbs.indexOf(callback)
  if (index > -1) cbs.splice(index, 1)
}

function onAppShow (callback) {
  global.__mpxAppCbs.show.push(callback)
}

function offAppShow (callback) {
  const cbs = global.__mpxAppCbs.show
  const index = cbs.indexOf(callback)
  if (index > -1) cbs.splice(index, 1)
}

function onAppHide (callback) {
  global.__mpxAppCbs.hide.push(callback)
}

function offAppHide (callback) {
  const cbs = global.__mpxAppCbs.hide
  const index = cbs.indexOf(callback)
  if (index > -1) cbs.splice(index, 1)
}

export {
  onAppShow,
  onAppHide,
  offAppShow,
  offAppHide,
  onError,
  offError
}
