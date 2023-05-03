import React from 'react';
import {Component} from '../../../libs';
import Menu from "./Menu";

import './style.scss';

class SidebarMenus extends Component {

  // 保存菜单id 与 组件的对应关系
  // {key:}
  menuComponents = {}

  /**
   * 定位到某个菜单并打开
   * @param menuId
   */
  openMenu(menuId) {
    let linked = [];
    let current: Menu = this.menuComponents[menuId];
    while (current) {
      linked.unshift(current);
      current = this.menuComponents[current.parentId];
    }

    setTimeout(() => {
      for (let i = 0; i < linked.length; i++) {
        linked[i].openMenu();
      }
    });
  }

  generationEveryMenu = (router, deep: number, parentId) => {
    let current = null;
    if (router.children && router.children.length > 0) {
      let that = this;
      let childrenComponent = router.children.map(r => {
        return that.generationEveryMenu(r, deep + 1, router.routerItem.id);
      });
      current = React.createElement(Menu, {
        propsObject: {
          menuComponents: this.menuComponents,
          id: router.routerItem.id,
          parentId: parentId,
          paddingLeft: deep * 20,
          menuName: router.routerItem.name
        }
      }, childrenComponent);
    } else {
      return <Menu propsObject={{
        menuComponents: this.menuComponents,
        id: router.routerItem.id,
        parentId,
        paddingLeft: deep * 20,
        menuName: router.routerItem.name
      }}></Menu>
    }
    return current;
  }

  componentDidMount() {
    // 打开菜单id7
    this.openMenu(7);
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
              return this.generationEveryMenu(r, 1, null);
            })
          }
        </div>
      </div>
    )
  }
}

export default SidebarMenus;