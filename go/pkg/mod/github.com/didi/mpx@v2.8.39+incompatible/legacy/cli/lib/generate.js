const chalk = require('chalk')
const Metalsmith = require('metalsmith')
const nunjucks = require('nunjucks')
const async = require('async')
const path = require('path')
const multimatch = require('multimatch')
const getOptions = require('./options')
const ask = require('./ask')
const filter = require('./filter')
const logger = require('./logger')

// 配置变量插值标签，以免与小程序动态绑定语法冲突
nunjucks.configure({
  tags: {
    variableStart: '<$',
    variableEnd: '$>'
  },
  autoescape: false,
  trimBlocks: true,
  lstripBlocks: true
})

const render = nunjucks.renderString

/**
 * Generate a template given a `src` and `dest`.
 *
 * @param {Object} option
 * @param {String} option.name
 * @param {String} option.src
 * @param {String} option.dest
 * @param {Array} option.mockList
 * @param {Function} done
 */

module.exports = function generate ({ name, src, dest, mockList }, done) {
  const opts = getOptions(name, src)
  const metalsmith = Metalsmith(path.join(src, 'template'))
  const data = Object.assign(metalsmith.metadata(), {
    destDirName: name,
    inPlace: dest === process.cwd(),
    noEscape: true
  })

  const helpers = { chalk, logger }

  if (opts.metalsmith && typeof opts.metalsmith.before === 'function') {
    opts.metalsmith.before(metalsmith, opts, helpers)
  }

  const mockData = opts.getMockData && opts.getMockData(mockList)

  metalsmith
    .use(preUseDefault(opts.prompts))
    .use(mockData ? mock(mockData) : askQuestions(opts.prompts))
    .use(computed(opts.computed))
    .use(filterFiles(opts.filters))
    .use(renderTemplateFiles(opts.skipInterpolation))

  if (typeof opts.metalsmith === 'function') {
    opts.metalsmith(metalsmith, opts, helpers)
  } else if (opts.metalsmith && typeof opts.metalsmith.after === 'function') {
    opts.metalsmith.after(metalsmith, opts, helpers)
  }

  metalsmith.clean(false)
    .source('.') // start from template root instead of `./src` which is Metalsmith's default for `source`
    .destination(dest)
    .build((err, files) => {
      done(err)
      if (typeof opts.complete === 'function') {
        const helpers = { chalk, logger, files }
        opts.complete(data, helpers)
      } else {
        logMessage(opts.completeMessage, data)
      }
    })

  return data
}

/**
 * Create a middleware for asking questions.
 *
 * @param {Object} prompts
 * @return {Function}
 */

function askQuestions (prompts) {
  return (files, metalsmith, done) => {
    ask(prompts, metalsmith.metadata(), done)
  }
}

function mock (mockData) {
  return (files, metalsmith, done) => {
    processMock(mockData, metalsmith.metadata(), done)
  }
}

function processMock (mockData, data, done) {
  if (!mockData) {
    return done()
  }
  Object.assign(data, mockData)
  done()
}

function preUseDefault (prompts) {
  return (files, metalsmith, done) => {
    const data = metalsmith.metadata()
    Object.keys(prompts).forEach((key) => {
      const prompt = prompts[key]
      if (typeof prompt.default === 'function') {
        let temp
        Object.defineProperty(data, key, {
          get () {
            if (temp !== undefined) {
              return temp
            }
            return prompt.default(data)
          },
          set (val) {
            temp = val
          },
          enumerable: true
        })
      } else {
        data[key] = prompt.default
      }
    })
    done()
  }
}

function computed (computed) {
  return (files, metalsmith, done) => {
    processComputed(computed, metalsmith.metadata(), done)
  }
}

function processComputed (computed, data, done) {
  if (!computed) {
    return done()
  }
  Object.keys(computed).forEach((key) => {
    Object.defineProperty(data, key, {
      get () {
        return computed[key].call(data)
      },
      enumerable: true
    })
  })
  done()
}

/**
 * Create a middleware for filtering files.
 *
 * @param {Object} filters
 * @return {Function}
 */

function filterFiles (filters) {
  return (files, metalsmith, done) => {
    filter(files, filters, metalsmith.metadata(), done)
  }
}

/**
 * Template in place plugin.
 *
 * @param {Object} files
 * @param {Metalsmith} metalsmith
 * @param {Function} done
 */

function renderTemplateFiles (skipInterpolation) {
  skipInterpolation = typeof skipInterpolation === 'string'
    ? [skipInterpolation]
    : skipInterpolation
  return (files, metalsmith, done) => {
    const keys = Object.keys(files)
    const metalsmithMetadata = metalsmith.metadata()
    async.each(keys, (file, next) => {
      // skipping files with skipInterpolation option
      if (skipInterpolation && multimatch([file], skipInterpolation, { dot: true }).length) {
        return next()
      }
      const str = files[file].contents.toString()
      render(str, metalsmithMetadata, (err, res) => {
        if (err) {
          err.message = `[${file}] ${err.message}`
          return next(err)
        }
        files[file].contents = Buffer.from(res)
        next()
      })
    }, done)
  }
}

/**
 * Display template complete message.
 *
 * @param {String} message
 * @param {Object} data
 */

function logMessage (message, data) {
  if (!message) return
  render(message, data, (err, res) => {
    if (err) {
      console.error('\n   Error when rendering template complete message: ' + err.message.trim())
    } else {
      console.log('\n' + res.split(/\r?\n/g).map(line => '   ' + line).join('\n'))
    }
  })
}
