import React, {Component} from 'react'
import {connect} from "react-redux";
import {CHANGE_SELECTED} from "../redux/constant/routersActionType";
import Tabs, {TabItem} from '../components/tabs/Tabs';
import SidebarMenus from "../components/sidebar-menus/SidebarMenus";
import '../styles/base.scss'

class Index extends Component {

  state = {
    tabItems: [],
  }
  tabs = null;
  sidebarMenus = null;
  allRoutersMap = new Map();

  components = [];

  onMenuOpen = (menu) => {
    if (!menu.childrenIds || menu.childrenIds.length === 0) {
      let routerItem = this.allRoutersMap.get(menu.id).routerItem;
      let tabItem: TabItem = new TabItem();
      tabItem.id = routerItem.id;
      tabItem.name = routerItem.name;
      if (!this.tabs.has(tabItem.id)) {
        this.tabs.addTabItem(tabItem);
      }
      this.tabs.selectTabItem(tabItem.id);
    }
  }

  onTabSelected = (tab) => {
    console.log(tab);
    this.sidebarMenus.openMenu(tab.id);
  }

  onTabClose = (tab) => {
    console.log(tab);
  }

  parseRouter(routerItemObj) {
    this.allRoutersMap.set(routerItemObj.routerItem.id, routerItemObj);
    if (routerItemObj.children) {
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

    const l = () => {
      let path = location.hash.replace("#", "");
      //alert(path);
    }

    window.addEventListener("hashchange", l, false);

    window.addEventListener("load", l, false);

    let lastNRise = require('./fund/lastNRise').default;
    let lastNRiseComp = React.createElement(lastNRise, null, null);
    let account = require('./system/account').default;
    let accountComp = React.createElement(account, null, null);
    this.components.push({h: true, c: lastNRiseComp});
    this.components.push({h: false, c: accountComp});
    this.setState({});
    setTimeout(()=>{
      this.components[0].h = false;
      this.setState({});
    },3000)

    setTimeout(()=>{
      this.components[0].h = true;
      this.setState({});
    },8000)

    setTimeout(()=>{
      this.components[0].h = false;
      this.setState({});
    },13000)
  }

  render() {
    const {tabItems} = this.state;
    const {allRouters} = this.props.routers;
    return (
      <div className="clearfix">
        <Tabs tabItems={tabItems} ref={c => {
          this.tabs = c;
        }} onSelected={this.onTabSelected} onClose={this.onTabClose}/>

        <SidebarMenus ref={c => this.sidebarMenus = c} onOpen={this.onMenuOpen}
                      style={{height: document.body.clientHeight - 33, width: 200, float: 'left'}}
                      allRouters={allRouters}/>
        <div style={{float: "left"}}>
          {
            this.components.map(t => {
              return (
                <div style={{display: !t.h ? "block" : "none"}}>
                  {t.c}
                </div>
              )
            })
          }
        </div>
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