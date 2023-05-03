import routers from '../../config/router.config'
import {CHANGE_SELECTED} from "../constant/routersActionType";

const initState = routers;
export default function routersReducer(preState = initState, action) {
  const {type, data} = action;
  // 根据type决定如何加工数据
  switch (type) {
    case CHANGE_SELECTED:
      preState.selected = data;
      return JSON.parse(JSON.stringify(preState));
    default:
      return preState;
  }
}