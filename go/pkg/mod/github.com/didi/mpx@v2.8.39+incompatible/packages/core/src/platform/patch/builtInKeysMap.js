import { INNER_LIFECYCLES } from '../../core/innerLifecycle'
import { makeMap } from '@mpxjs/utils'

let builtInKeys

if (__mpx_mode__ === 'web') {
  builtInKeys = [
    'proto',
    'mixins',
    'mpxCustomKeysForBlend',
    'mpxConvertMode',
    'mpxFileResource',
    '__nativeRender__',
    '__type__',
    '__pageCtor__'
  ]
} else {
  builtInKeys = [
    'setup',
    'dataFn',
    'proto',
    'mixins',
    'watch',
    'computed',
    'mpxCustomKeysForBlend',
    'mpxConvertMode',
    'mpxFileResource',
    '__nativeRender__',
    '__type__',
    '__pageCtor__'
  ]
}

export default makeMap(builtInKeys.concat(INNER_LIFECYCLES))
