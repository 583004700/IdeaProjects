import {RouterItem} from "../components/sidebar-menus/SidebarMenus";

export default {
  // 当前的路由地址
  selected: '5',
  allRouters: [
    {
      routerItem: new RouterItem('1','/fund','基金查询'),
      children: [
        {
          routerItem: new RouterItem('6','/fund/lastNRise','最近n天涨幅'),
        }
      ]
    },
    {
      routerItem: new RouterItem('2','/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem('3','/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem('5','/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem('7','/system/account/userList/selectList','查询统计')}
              ]
            }
          ]
        },
        {
          routerItem: new RouterItem("a",'/system/org','组织机构'),
          children: [
            {routerItem: new RouterItem("c",'/system/account','账号管理'),}
          ]
        }
      ]
    },
    {
      routerItem: new RouterItem('12','/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem('13','/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem('15','/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem('17','/system/account/userList/selectList','查询统计')}
              ]
            }
          ]
        },
        {
          routerItem: new RouterItem("1a",'/system/org','组织机构'),
          children: [
            {routerItem: new RouterItem("1c",'/system/account','账号管理'),}
          ]
        }
      ]
    },{
      routerItem: new RouterItem('22','/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem('23','/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem('25','/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem('27','/system/account/userList/selectList','查询统计')}
              ]
            }
          ]
        },
        {
          routerItem: new RouterItem("2a",'/system/org','组织机构'),
          children: [
            {routerItem: new RouterItem("2c",'/system/account','账号管理'),}
          ]
        }
      ]
    },{
      routerItem: new RouterItem('32','/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem('33','/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem('35','/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem('37','/system/account/userList/selectList','查询统计')}
              ]
            }
          ]
        },
        {
          routerItem: new RouterItem("3a",'/system/org','组织机构'),
          children: [
            {routerItem: new RouterItem("3c",'/system/account','账号管理'),}
          ]
        }
      ]
    },{
      routerItem: new RouterItem('42','/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem('43','/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem('45','/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem('47','/system/account/userList/selectList','查询统计')}
              ]
            }
          ]
        },
        {
          routerItem: new RouterItem("4a",'/system/org','组织机构'),
          children: [
            {routerItem: new RouterItem("4c",'/system/account','账号管理'),}
          ]
        }
      ]
    },
  ]
}