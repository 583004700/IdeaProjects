import React from 'react';
import {Component} from '../../../libs';
import '../../styles/iconfont/iconfont.css'
import './style.scss';

export class TabItem {
  id: string;
  name: string;
  selected: boolean;
}

class Tabs extends Component {

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
    const tabItems = this.props.tabItems;
    for (let i = 0; i < tabItems.length; i++) {
      if(id === tabItems[i].id){
        tabItems.splice(i,1);
      }
    }
    this.flush();
  }

  selectTabItem = (id: string) =>{
    const tabItems = this.props.tabItems;
    for (let i = 0; i < tabItems.length; i++) {
      tabItems[i].selected = id === tabItems[i].id;
    }
    this.flush();
  }

  render() {
    const tabItems: TabItem[] = this.props.tabItems;
    return (
      <div className="tabs">
        <div className="ul">
          {
            tabItems.map((e) => {
              return (
                <div onClick={()=>{
                  this.selectTabItem(e.id);
                }} className={e.selected ? 'li selected' : 'li'} key={e.id}>
                  {e.name}
                  <span onClick={()=>{
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