<template>
  <div class="h-100">
    <div layout="row top-justify"
         style="height: 45vh">
      <div class="w-33 h-100">
        <v-chart :option="optionMonthPieChart"
                 :theme="theme.name"
                 autoresize/>
      </div>
      <div class="w-33 h-100">
        <v-chart :option="optionYearLineChart"
                 :theme="theme.name"
                 autoresize/>
      </div>
      <div class="w-33 h-100">
        <v-chart :option="optionYearPieChart"
                 :theme="theme.name"
                 autoresize/>
      </div>
    </div>
    <div class="h-50"
         layout="column top-center">
      <!--      <h1>TODO：过滤搜索（根据类别、日期、名称、金额）</h1>-->
      <v-table density="compact">
        <thead>
        <tr>
          <th class="text-center">
            类别
          </th>
          <th class="text-center">
            日期
          </th>
          <th class="text-center">
            名称
          </th>
          <th class="text-center">
            金额
          </th>
          <th class="text-center">
            详情
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in table"
            :key="item.name"
            class="text-center">
          <td>{{ item.type }}</td>
          <td>{{ item.date }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.cost }}</td>
          <td>{{ item.detail }}</td>
        </tr>
        </tbody>
      </v-table>
    </div>
  </div>


</template>

<script setup>
import VChart from 'vue-echarts'
import {onActivated, ref} from 'vue'
import {useTheme} from "@/store/modules/theme"
import {post} from "@/net";

const theme = useTheme()

const optionMonthPieChart = ref({
  title: {
    text: '本月收支',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['利润', '广告费', '人工成本', '服务器租赁'],
  },
  series: [
    {
      name: '年度收支',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 3100, name: '服务器租赁'},
        {value: 2340, name: '广告费'},
        {value: 6448, name: '人工成本'},
        {value: 15048, name: '利润'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
})

const optionYearLineChart = ref({
  title: {
    text: '年度利润',
    left: 'center',
  },
  xAxis: {
    type: 'category',
    data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [1560, 6230, 2264, 2168, 3365, 6147, 2606, 3180, 5830, 8120, 4130, 6140],
      type: 'line'
    }
  ]
})

const optionYearPieChart = ref({
  title: {
    text: '年度收支',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['利润', '广告费', '人工成本', '服务器租赁'],
  },
  series: [
    {
      name: '年度收支',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 13120, name: '服务器租赁'},
        {value: 25344, name: '广告费'},
        {value: 133500, name: '人工成本'},
        {value: 173227, name: '利润'},
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
})

// 利润、广告费、人工成本、服务器租赁
const table = ref([
  {
    type: '支出',
    date: '2021-01-01',
    name: '人工成本',
    cost: 10000,
    detail: '工资'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '人工成本',
    cost: 1000,
    detail: '奖金'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '人工成本',
    cost: 2000,
    detail: '工资'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '服务器租赁',
    cost: 500,
    detail: '服务器租赁'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '广告费',
    cost: 1000,
    detail: '广告费'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '广告费',
    cost: 500,
    detail: '广告费'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '服务器租赁',
    cost: 1000,
    detail: '服务器租赁'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '人工成本',
    cost: 500,
    detail: '奖金'
  },

])


onActivated(() => {
  post('/api/admin/cost/info')
    .then(({data}) => {
      data.forEach(item => {
        table.value.push(
          {
            type: '收入',
            date: item.time,
            name: '出售图书',
            cost: item.cost,
            detail: `用户 ${item.userId} 购买图书 ${item.bookId}`
          })
      })
    })
})
</script>

<style lang="scss" scoped>

</style>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
