/*
    该文件用于汇总所有的reducer为一个总的reducer
 */
import routers from './routers';
import {combineReducers} from "redux";

const allReducer = combineReducers({
  routers
})

export default allReducer;