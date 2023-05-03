import React from 'react';
import {Component} from '../../../libs';

export default class Menu extends Component {

  id = null;
  parentId = null;
  open = false;

  state = {
    subMenuHeight: 0
  }

  constructor(props) {
    super(props);
    let {id, parentId, menuComponents} = props.propsObject;
    this.id = id;
    this.parentId = parentId;
    menuComponents[this.id] = this;
  }

  menuItemClick = (e) => {
    e.stopPropagation();
    if (this.open) {
      this.closeMenu();
    } else {
      this.openMenu();
    }
  }

  /*
  展开当前菜单
   */
  openMenu() {
    if (!this.open) {
      this.open = true;
      if (this.props.children) {
        this.subMenu.fastHeight = this.subMenu.fastHeight === undefined ? this.subMenu.scrollHeight : this.subMenu.fastHeight;
        this._dealWithParent(this.subMenu.parentNode, this.subMenu.fastHeight);
      }
      this.setState({subMenuHeight: this.subMenu.fastHeight});
    }
  }

  closeMenu() {
    if (this.open) {
      this.open = false;
      if (this.props.children) {
        this.subMenu.fastHeight = this.subMenu.fastHeight === undefined ? this.subMenu.scrollHeight : this.subMenu.fastHeight;
        this._dealWithParent(this.subMenu.parentNode, -this.subMenu.fastHeight);
      }
      this.setState({subMenuHeight: 0});
    }
  }

  _dealWithParent(node, subMenuHeight) {
    if (node && (node.classList.contains("menu") || (node.classList.contains("subMenu")))) {
      let fastHeight = node.fastHeight;
      node.fastHeight = ((fastHeight === undefined ? node.clientHeight : fastHeight) + subMenuHeight);
      node.style.height = ((fastHeight === undefined ? node.clientHeight : fastHeight) + subMenuHeight) + "px";
      if (node && node.parentNode) {
        this._dealWithParent(node.parentNode, subMenuHeight);
      }
    }
  }

  render() {
    const {propsObject} = this.props;
    const {subMenuHeight} = this.state;
    const {paddingLeft, menuName} = propsObject;
    return (
      <div style={this.style()} className="menu">
        <div onClick={(e) => {
          this.menuItemClick(e)
        }} style={{paddingLeft: paddingLeft, position: "relative", fontSize: this.props.children ? 13 : 12}}
             className={this.open ? "menuItem menuOpen" : "menuItem"}>{menuName}
          <i style={{fontSize: 12, position: "absolute", marginTop: -7, top: "50%", right: 20}}
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