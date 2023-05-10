import React, {Component} from 'react'
import {connect} from "react-redux";
import {CHANGE_SELECTED} from "../redux/constant/routersActionType";
import Tabs, {TabItem} from '../components/tabs/Tabs';
import SidebarMenus from "../components/sidebar-menus/SidebarMenus";

class Index extends Component {

  state = {
    tabItems: []
  }
  tabs = null;
  sidebarMenus = null;
  allRoutersMap = new Map();

  onMenuOpen = (menu)=>{
    if(!menu.childrenIds || menu.childrenIds.length === 0) {
      let routerItem = this.allRoutersMap.get(menu.id).routerItem;
      let tabItem: TabItem = new TabItem();
      tabItem.id = routerItem.id;
      tabItem.name = routerItem.name;
      if(!this.tabs.has(tabItem.id)) {
        this.tabs.addTabItem(tabItem);
      }
      this.tabs.selectTabItem(tabItem.id);
    }
  }

  onTabSelected = (tab)=>{
    console.log(tab);
    this.sidebarMenus.openMenu(tab.id);
  }

  onTabClose = (tab)=>{
    console.log(tab);
  }

  parseRouter(routerItemObj){
     this.allRoutersMap.set(routerItemObj.routerItem.id,routerItemObj);
     if(routerItemObj.children){
       for (const element of routerItemObj.children) {
         this.parseRouter(element);
       }
     }
  }

  componentDidMount() {
    //this.sidebarMenus.openMenu("7");
    const {allRouters} = this.props.routers;
    for (const element of allRouters) {
      this.parseRouter(element);
    }

    const l = ()=>{
      let path = location.hash.replace("#","");
      alert(path);
    }

    window.addEventListener("hashchange", l, false);

    window.addEventListener("load", l, false);
  }

  render() {
    let c = require('./fund/lastNRise').default;
    let comp = React.createElement(c,null,null);
    const {tabItems} = this.state;
    const {allRouters} = this.props.routers;
    console.log(allRouters);
    return (
      <div>
        <Tabs tabItems={tabItems} ref={c => {
          this.tabs = c;
        }} onSelected={this.onTabSelected} onClose={this.onTabClose}/>

        <SidebarMenus ref={c=>this.sidebarMenus = c} onOpen={this.onMenuOpen}
                      style={{height: document.body.clientHeight - 33,width: 200,float: 'left'}} allRouters={allRouters}/>
        {comp}
      </div>
    )
  }
}


function mapStateToProps(state) {
  return {routers: state.routers}
}

const IndexContainer = connect(mapStateToProps,
  {
    changeSelected: (data) => {
      return {
        type: CHANGE_SELECTED,
        data: data
      }
    }
  }
)(Index)

export default IndexContainer;