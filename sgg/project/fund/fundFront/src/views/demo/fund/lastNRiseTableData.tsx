import {FormProps} from '/@/components/Table';
import {BasicColumn} from '/@/components/Table/src/types/table';
import {VxeFormItemProps} from '/@/components/VxeTable';

export function getBasicColumns(): BasicColumn[] {
  return [
    {
      title: '基金代码',
      dataIndex: 'fundcode',
      fixed: 'left',
      sorter: false,
      width: 200,
    },
    {
      title: '基金名称',
      dataIndex: 'name',
      width: 150,
      sorter: false,
    },
    {
      title: '最近一天涨幅',
      dataIndex: 'gszzl',
      width: 150,
      sorter: true,
    },
    {
      title: '最近n天涨幅',
      dataIndex: 'nDaysGszzl',
      width: 150,
      sorter: true,
    },
    {
      title: '涨幅时间',
      dataIndex: 'gztime',
      width: 150,
      sorter: false,
    },
    {
      title: '数据更新时间',
      dataIndex: 'updatedTime',
      width: 150,
      sorter: false,
    }
  ];
}

export function getFormConfig(): Partial<FormProps> {
  return {
    labelWidth: 100,
    schemas: [
      {
        field: `gzdate`,
        label: `查询日期:`,
        component: 'DatePicker',
        required: true,
        defaultValue: new Date(),
        colProps: {
          xl: 6,
          xxl: 4
        },
      },
      {
        field: `n`,
        label: `天数:`,
        component: 'InputNumber',
        required: true,
        defaultValue: 1,
        colProps: {
          xl: 4,
          xxl: 4
        }
      },
      {
        field: `continuation`,
        label: `是否连续涨:`,
        component: 'Checkbox',
        required: false,
        defaultValue: true,
        colProps: {
          xl: 3,
          xxl: 4
        }
      },
      {
        field: `fundName`,
        label: `基金名称:`,
        component: 'Input',
        required: false,
        defaultValue: "",
        colProps: {
          xl: 8,
          xxl: 4
        }
      }
    ],
  };
}

export const vxeTableFormSchema: VxeFormItemProps[] = [
  {
    span: 12,
    align: 'right',
    className: '!pr-0',
    itemRender: {
      name: 'AButtonGroup',
      children: [
        {
          props: {type: 'primary', content: '查询', htmlType: 'submit'},
          attrs: {class: 'mr-2'},
        },
        {props: {type: 'default', htmlType: 'reset', content: '重置'}},
      ],
    },
  },
];
