import React from 'react';
import {Component} from '../../../libs';
import Menu from "./Menu";

import './style.scss';

class MenuComponent{
  component: Component;
  componentId: any;
  parentComponentId: any;

  constructor(component: Component,componentId: any,parentComponentId: any) {
    this.component = component;
    this.componentId = componentId;
    this.parentComponentId = parentComponentId;
  }
}

class LeftMenus extends Component {

  f = function(c){
    //console.log(c);
  };

  // 保存菜单id 与 组件的对应关系
  // {key:}
  menuComponents = {

  }

  /**
   * 定位到某个菜单并打开
   * @param menuId
   */
  openMenu(menuId){
    let linked = [];
    let current: MenuComponent = this.menuComponents[menuId];
    while(current){
      linked.unshift(current.component);
      current = this.menuComponents[current.parentComponentId];
    }
    for (let i = 0; i < linked.length; i++) {
      console.log(linked[i]);
    }
  }

  generationEveryMenu = (router, deep: number) => {
    let current = null;
    if (router.children && router.children.length > 0) {
      let that = this;
      let childrenComponent = router.children.map(r => {
        let kv = that.generationEveryMenu(r, deep + 1);
        let item = r.routerItem;
        that.menuComponents[item.id] = new MenuComponent(kv,item.id,router.routerItem.id);
        return kv;
      });
      current = React.createElement(Menu, {
        propsObject: {
          paddingLeft: deep * 20,
          menuName: router.routerItem.name
        }
      }, childrenComponent);
      this.menuComponents[router.routerItem.id] = new MenuComponent(current,router.routerItem.id,null);
    } else {
      //current = React.createElement(Menu, {propsObject: {paddingLeft: deep * 20, menuName: router.routerItem.name}});
      return <Menu ref={(c)=>{this.f(c)}} propsObject={{paddingLeft: deep * 20, menuName: router.routerItem.name}}></Menu>
    }
    return current;
  }

  componentDidMount() {
    console.log(this.menuComponents);
    this.openMenu(3);
  }

  render() {
    const {routers} = this.props;
    let {allRouters} = routers;
    return (
      <div className='menuContainer' style={this.style({overflow: "hidden"})}>
        <div style={{
          width: this.style().width + 15,
          height: this.style().height,
          overflowY: "scroll"
        }}>
          {
            allRouters.map(r => {
              return this.generationEveryMenu(r, 1);
            })
          }
        </div>
      </div>
    )
  }
}

export default LeftMenus;