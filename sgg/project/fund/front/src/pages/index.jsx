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

  getAllRouters(): TabItem[] {
    let items = [];
    let i1: TabItem = new TabItem();
    i1.id = "3";
    i1.name = "订单查询";
    items.push(i1);
    return items;
  }

  menuOpen(menu){
    console.log(menu);
  }

  tabSelected(tab){
    console.log(tab);
  }

  tabClose(tab){
    console.log(tab);
  }

  componentDidMount() {
    this.setState({tabItems: this.getAllRouters()});

    let t2: TabItem = new TabItem();
    t2.id = "i2";
    t2.name = "系统设置";

    setTimeout(()=>{
      this.tabs.addTabItem(t2);
    },3000);

    setTimeout(()=>{
      this.tabs.selectTabItem("i2");
    },6000);

    this.sidebarMenus.openMenu("7");
  }

  render() {
    const {tabItems} = this.state;
    const {allRouters} = this.props.routers;
    return (
      <div>
        <Tabs tabItems={tabItems} ref={c => {
          this.tabs = c;
        }} onSelected={this.tabSelected} onClose={this.tabClose}/>

        <SidebarMenus ref={c=>this.sidebarMenus = c} onOpen={this.menuOpen} style={{height: document.body.clientHeight - 33,width: 200}} allRouters={allRouters}/>
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