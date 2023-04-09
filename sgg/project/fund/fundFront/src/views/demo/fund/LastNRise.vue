<template>
  <BasicTable @register="registerTable">
    <template #form-custom> custom-slot</template>

    <template #toolbar>
      <a-button type="primary" @click="updateGs">更新基金估值</a-button>
    </template>
  </BasicTable>
</template>
<script lang="ts">
import {defineComponent, ref} from 'vue';
import {BasicTable, useTable} from '/@/components/Table';
import {getBasicColumns, getFormConfig} from './lastNRiseTableData';
import {Alert} from 'ant-design-vue';

import {updateGsData, demoListApi} from '/@/api/demo/fund/fund';

export default defineComponent({
  components: {BasicTable, AAlert: Alert},
  setup() {
    const checkedKeys = ref<Array<string | number>>([]);
    const [registerTable, {getForm}] = useTable({
      title: '最近n天涨幅',
      api: demoListApi,
      columns: getBasicColumns(),
      useSearchForm: true,
      formConfig: getFormConfig(),
      showTableSetting: true,
      tableSetting: {fullScreen: true},
      showIndexColumn: true,
      pagination: {//分页的配置
        pageSize: 200,
        size: "small",
        simple: true
      },
      rowKey: 'id'
    });

    function updateGs(e) {
      let ub = e.target.parentNode;
      ub.style.display = 'none';
      let result = updateGsData();
      alert("正在更新基金数据...，请稍后查看！")
      result.then((data) => {
        alert("更新基金数据完成！");
      }).catch((data) => {
        alert("更新基金数据完成！");
      }).finally((data: any) => {
        ub.style.display = 'block';
      });
    }

    return {
      registerTable,
      updateGs,
      checkedKeys
    };
  },
});
</script>
