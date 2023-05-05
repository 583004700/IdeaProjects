import React from 'react';
import {Component} from '../../../libs';

export default class Menu extends Component {

  id = null;
  parentId = null;
  childrenIds = [];
  allChildrenIds = [];
  deep = null;
  linkObj = null;
  linkArr = null;
  open = false;

  selected = false;

  state = {
    subMenuHeight: 0
  }

  constructor(props) {
    super(props);
    let {id, parentId, menuComponents, deep} = props.propsObject;
    this.id = id;
    this.parentId = parentId;
    this.deep = deep;
    if (parentId !== null) {
      menuComponents.get(parentId).childrenIds.push(this);
    }
    menuComponents.set(this.id, this);
  }

  componentDidMount() {
    let linkArr = this.getLinkArr();
    for (let i = 0; i < linkArr.length; i++) {
      if (linkArr[i] !== this) {
        linkArr[i].allChildrenIds.push(this);
      }
    }
  }

  menuItemClick = (e) => {
    e.stopPropagation();
    if (this.open) {
      let hasOpen = false;
      for (let i = 0; i < this.allChildrenIds.length; i++) {
        if (this.allChildrenIds[i].open && this.allChildrenIds[i].allChildrenIds.length === 0) {
          hasOpen = true;
          break;
        }
      }
      if (hasOpen) {
        this.retractMenu();
      } else {
        this.closeMenu();
      }
    } else {
      this.openMenu();
    }
  }

  /**
   * 获取当前节点的来源
   * @returns {{}}
   */
  getLinkObj() {
    if (!this.linkObj) {
      let result = new Map();
      let currentId = this.id;
      let {menuComponents} = this.props.propsObject;
      while (currentId !== null && currentId !== undefined) {
        result.set(currentId, menuComponents.get(currentId));
        currentId = menuComponents.get(currentId).parentId;
      }
      this.linkObj = result;
    }
    return this.linkObj;
  }

  /**
   * 获取当前节点的路径
   * @returns {[]}
   */
  getLinkArr() {
    if (!this.linkArr) {
      let result = [];
      let currentId = this.id;
      let {menuComponents} = this.props.propsObject;
      while (currentId !== null && currentId !== undefined) {
        result.unshift(menuComponents.get(currentId));
        currentId = menuComponents.get(currentId).parentId;
      }
      this.linkArr = result;
    }
    return this.linkArr;
  }

  /*
  展开当前菜单
   */
  openMenu() {
    if (!this.open) {
      let {menuComponents} = this.props.propsObject;
      let linkObj = this.getLinkObj();
      let keys = [...menuComponents.keys()].reverse();
      let sameRetract = false;
      for (const menuComponentsKey of keys) {
        let v = menuComponents.get(menuComponentsKey);
        if (this.props.children) {
          if (v.parentId === this.parentId && v.open && !sameRetract) {
            let hasOpen = false;
            // 打开一个菜单时，要收起同级的其它已打开的菜单
            for (let i = 0; i < v.allChildrenIds.length; i++) {
              if (v.allChildrenIds[i].open && v.allChildrenIds[i].allChildrenIds.length === 0) {
                hasOpen = true;
                break;
              }
            }
            if (hasOpen) {
              // 如果其它菜单中有已经打开的子菜单
              v.retractMenu();
            } else {
              v.closeMenu();
            }
            sameRetract = true;
          }
        } else {
          if (!linkObj.has(v.id)) {
            // 打开一个没有子节点的菜单时，要关闭其它所有的菜单
            v.closeMenu();
          }
        }
      }
      this.setSelected(true);
      if (this.props.children) {
        let fastHeight = this.subMenu.fastHeight === undefined ? this.subMenu.scrollHeight : this.subMenu.fastHeight;
        this._dealWithParent(menuComponents.get(this.id), fastHeight, 1);
      }
      this.open = true;
      this.setState({subMenuHeight: this.subMenu.fastHeight});
    }
  }

  setSelected(selected) {
    this.selected = selected;
    this.setState({});
  }

  closeMenu() {
    this.setSelected(false);
    this.retractMenu();
  }

  retractMenu() {
    let {menuComponents} = this.props.propsObject;
    if (this.open) {
      if (this.props.children) {
        let fastHeight = this.subMenu.fastHeight === undefined ? this.subMenu.scrollHeight : this.subMenu.fastHeight;
        this._dealWithParent(menuComponents.get(this.id), -fastHeight, 1);
      }
      this.open = false;
      this.setState({subMenuHeight: 0});
    }
  }

  _dealWithParent(component, subMenuHeight, deep) {
    let {menuComponents} = this.props.propsObject;
    if (component) {
      if(deep > 1) {
        let node = component.subMenu;
        this._dealWithParentNode(node, subMenuHeight);
      }
      let menuDeal = !(component.open !== true && subMenuHeight < 0);
      if(menuDeal){
        let node = component.menu;
        this._dealWithParentNode(node, subMenuHeight);
      }
      if (menuDeal && component && component.parentId !== null) {
        this._dealWithParent(menuComponents.get(component.parentId), subMenuHeight, deep + 1);
      }
    }
  }

  _dealWithParentNode(node, subMenuHeight) {
    let fastHeight = node.fastHeight;
    node.fastHeight = ((fastHeight === undefined ? node.clientHeight : fastHeight) + subMenuHeight);
    node.style.height = ((fastHeight === undefined ? node.clientHeight : fastHeight) + subMenuHeight) + "px";
  }

  render() {
    const {propsObject} = this.props;
    const {subMenuHeight} = this.state;
    const {paddingLeft, menuName} = propsObject;
    return (
      <div ref={c => this.menu = c} style={this.style()} className="menu">
        <div onClick={(e) => {
          this.menuItemClick(e)
        }} style={{
          paddingLeft: paddingLeft,
          position: "relative",
          fontSize: this.props.children ? "inherit" : 12,
          fontWeight: !this.props.children && this.selected ? 700 : "inherit"
        }}
             className={this.selected ? "menuItem menuOpen" : "menuItem"}>{menuName}
          <i style={{fontSize: 12, position: "absolute", marginTop: -7, top: "50%", right: 30}}
             className={this.props.children ? this.open ? "el-icon-arrow-up rotate0" : "el-icon-arrow-up rotate-180" : ""}></i>
        </div>
        <div ref={c => this.subMenu = c} className="subMenu" style={{height: subMenuHeight, position: "relative"}}>
          {
            this.props.children
          }
          <div style={{
            width: "100%",
            height: "100%",
            position: "absolute",
            top: 0,
            left: 0,
            display: this.open ? "none" : "block"
          }}>
          </div>
        </div>
      </div>
    )
  }
}