import type {AppRouteModule} from '/@/router/types';

import {LAYOUT} from '/@/router/constant';

const fund: AppRouteModule = {
  path: '/fund',
  name: 'Fund',
  component: LAYOUT,
  redirect: '/fund/lastNRise',
  meta: {
    orderNo: 1,
    icon: 'ion:layers-outline',
    title: '基金查询',
  },

  children: [
    {
      path: 'lastNRise',
      name: 'lastNRise',
      component: () => import('/@/views/demo/fund/LastNRise.vue'),
      meta: {
        title: '最近n天涨幅',
      },
    },
  ],
};

export default fund;
