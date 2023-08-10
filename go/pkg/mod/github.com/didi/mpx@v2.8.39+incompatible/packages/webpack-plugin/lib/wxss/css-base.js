/*
  MIT License http://www.opensource.org/licenses/mit-license.php
  Author Tobias Koppers @sokra
  Modified by @hiyuki
*/
// css base code, injected by the css-loader
module.exports = function (useSourceMap) {
  const list = []

  // return the list of modules as css string
  list.toString = function toString () {
    return this.map(function (item) {
      const content = cssWithMappingToString(item, useSourceMap)
      if (item[2]) {
        return '@media ' + item[2] + '{' + content + '}'
      } else {
        return content
      }
    }).join('')
  }

  // import a list of modules into the list
  list.i = function (modules, mediaQuery) {
    if (typeof modules === 'string') {
      modules = [[null, modules, '']]
    }
    const alreadyImportedModules = {}
    for (let i = 0; i < this.length; i++) {
      const id = this[i][0]
      if (typeof id === 'number') {
        alreadyImportedModules[id] = true
      }
    }
    for (let i = 0; i < modules.length; i++) {
      const item = modules[i]
      // skip already imported module
      // this implementation is not 100% perfect for weird media query combinations
      //  when a module is imported multiple times with different media queries.
      //  I hope this will never occur (Hey this way we have smaller bundles)
      if (typeof item[0] !== 'number' || !alreadyImportedModules[item[0]]) {
        if (mediaQuery && !item[2]) {
          item[2] = mediaQuery
        } else if (mediaQuery) {
          item[2] = '(' + item[2] + ') and (' + mediaQuery + ')'
        }
        list.push(item)
      }
    }
  }
  return list
}

function cssWithMappingToString (item, useSourceMap) {
  const content = item[1] || ''
  const cssMapping = item[3]
  if (!cssMapping) {
    return content
  }

  if (useSourceMap && typeof btoa === 'function') {
    const sourceMapping = toComment(cssMapping)
    const sourceURLs = cssMapping.sources.map(function (source) {
      return '/*# sourceURL=' + cssMapping.sourceRoot + source + ' */'
    })

    return [content].concat(sourceURLs).concat([sourceMapping]).join('\n')
  }

  return [content].join('\n')
}

// Adapted from convert-source-map (MIT)
function toComment (sourceMap) {
  // eslint-disable-next-line no-undef
  const base64 = btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap))))
  const data = 'sourceMappingURL=data:application/json;charset=utf-8;base64,' + base64

  return '/*# ' + data + ' */'
}
