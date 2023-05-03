import React, {Component} from 'react'
import {connect} from "react-redux";
import {CHANGE_SELECTED} from "../redux/constant/routersActionType";
import Tabs, {TabItem} from '../components/head-tabs/Tabs';
import SidebarMenus from "../components/left-menus/SidebarMenus";

class Index extends Component {

  state = {
    tabItems: []
  }

  getAllRouters(): TabItem[] {
    let items = [];
    let i1: TabItem = new TabItem();
    i1.id = "3";
    i1.name = "订单查询";
    i1.selected = true;
    items.push(i1);
    return items;
  }

  componentDidMount() {
    this.setState({tabItems: this.getAllRouters()});

    let t2: TabItem = new TabItem();
    t2.id = "i2";
    t2.name = "系统设置";

    setTimeout(()=>{
      this.headTab.addTabItem(t2);
    },3000);

    setTimeout(()=>{
      this.headTab.selectTabItem("i2");
    },6000);
  }

  render() {
    const {tabItems} = this.state;
    const {routers} = this.props;
    return (
      <div>
        <Tabs tabItems={tabItems} ref={c => {
          this.headTab = c;
        }}/>

        <SidebarMenus style={{height: document.body.clientHeight - 33,width: 200}} routers={routers}/>
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