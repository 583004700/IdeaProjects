export class RouterItem{
  id: number;
  url: string;
  name: string;
  constructor(id: number,url: string, name: string) {
    this.id = id;
    this.url = url;
    this.name = name;
  }
}

export default {
  // 当前的路由地址
  selected: '/fund/lastNRise',
  allRouters: [
    {
      routerItem: new RouterItem(1,'/fund','基金查询'),
      children: [
        {
          routerItem: new RouterItem(6,'/fund/lastNRise','最近n天涨幅'),
        }
      ]
    },
    {
      routerItem: new RouterItem(2,'/system','系统管理'),
      children: [
        {
          routerItem: new RouterItem(3,'/system/account','账号管理'),
          children: [
            {
              routerItem: new RouterItem(5,'/system/account/userList','用户列表'),
              children: [
                {routerItem: new RouterItem(7,'/system/account/userList/selectList','查询统计')}
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
  ]
}