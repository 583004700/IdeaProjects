import React from 'react';
import {Component} from '../../../libs';
import '../../styles/iconfont/iconfont.css'
import './style.scss';

export class TabItem {
  id: string;
  name: string;
  selected: boolean;
}

/**
 *  属性 tabItems 为所有 tab 页，数据结构 TabItem[]
 *  属性 onSelected tab 页选中时回调
 *  属性 onClose tab 页关闭时回调
 */

class Tabs extends Component {

  constructor(props) {
    super(props);
  }

  /**
   * 添加一个标签页
   * @param tabItem
   */
  addTabItem = (tabItem: TabItem) => {
    const tabItems: TabItem[] = this.props.tabItems;
    tabItems.push(tabItem);
    this.flush();
  }

  flush = () => {
    this.setState({});
  }

  removeTabItem = (id: string) => {
    const {tabItems,onClose} = this.props;
    for (let i = 0; i < tabItems.length; i++) {
      if (id === tabItems[i].id) {
        onClose(tabItems[i]);
        tabItems.splice(i, 1);
      }
    }
    this.flush();
  }

  selectTabItem = (id: string) => {
    const {tabItems,onSelected} = this.props;
    for (const element of tabItems) {
      element.selected = false;
    }
    this.searchTab(id).selected = true;
    this.flush();
    if(onSelected){
      onSelected(this.searchTab(id));
    }
  }

  searchTab(id: string){
    const {tabItems} = this.props;
    for (const element of tabItems) {
      if(element.id === id){
        return element;
      }
    }
    return null;
  }

  has = (id: string) => {
    return this.searchTab(id) !== null;
  }

  render() {
    const tabItems: TabItem[] = this.props.tabItems;
    return (
      <div className="tabs">
        <div className="ul">
          {
            tabItems.map((e) => {
              return (
                <div onClick={() => {
                  this.selectTabItem(e.id);
                }} className={e.selected ? 'li selected' : 'li'} key={e.id}>
                  {e.name}
                  <span onClick={(event) => {
                    event.stopPropagation();
                    this.removeTabItem(e.id);
                  }} className="close_icon iconfont icon-guanbi"></span>
                </div>
              )
            })
          }
        </div>
      </div>
    )
  }
}

export default Tabs;