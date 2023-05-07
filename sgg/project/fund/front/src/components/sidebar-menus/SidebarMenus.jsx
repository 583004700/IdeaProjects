import React from 'react';
import {Component} from '../../../libs';
import Menu from "./Menu";

import './style.scss';

export class RouterItem{
  id: string;
  url: string;
  name: string;
  constructor(id: string,url: string, name: string) {
    this.id = id;
    this.url = url;
    this.name = name;
  }
}

/**
 *  属性 onOpen 菜单打开时回调
 *  属性 allRouters 所有菜单数据，结构为
 *
 *  [{
 *       routerItem: new RouterItem(1,'/system','系统管理'),
 *       children: [
 *         {
 *           routerItem: new RouterItem(6,'/system/users','用户管理'),
 *         }
 *       ]
 *  }]
 *
 */

class SidebarMenus extends Component {

  // 保存菜单id 与 组件的对应关系
  // {key:}
  menuComponents = new Map();

  /**
   * 定位到某个菜单并打开
   * @param menuId
   */
  openMenu(menuId) {
    let linked = this.menuComponents.get(menuId).getLinkArr();
    setTimeout(() => {
      for (let i = 0; i < linked.length; i++) {
        linked[i].openMenu();
      }
    });
  }

  /**
   * 关闭某个菜单和所有上级菜单
   * @param menuId
   */
  closeMenu(menuId) {
    let linked = this.menuComponents.get(menuId).getLinkArr();
    setTimeout(() => {
      for (let i = 0; i < linked.length; i++) {
        linked[i].closeMenu();
      }
    });
  }

  generationEveryMenu = (router, deep: number, parentId) => {
    let {onOpen} = this.props;
    let current = null;
    if (router.children && router.children.length > 0) {
      let that = this;
      let childrenComponent = router.children.map(r => {
        return that.generationEveryMenu(r, deep + 1, router.routerItem.id);
      });
      current = React.createElement(Menu, {
        key: router.routerItem.id,
        propsObject: {
          onOpen: onOpen,
          menuComponents: this.menuComponents,
          id: router.routerItem.id,
          parentId: parentId,
          deep: deep,
          paddingLeft: deep * 20,
          menuName: router.routerItem.name
        }
      }, childrenComponent);
    } else {
      return <Menu key={router.routerItem.id} propsObject={{
        onOpen: onOpen,
        menuComponents: this.menuComponents,
        id: router.routerItem.id,
        parentId,
        deep: deep,
        paddingLeft: deep * 20,
        menuName: router.routerItem.name
      }}></Menu>
    }
    return current;
  }

  render() {
    const {allRouters} = this.props;
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