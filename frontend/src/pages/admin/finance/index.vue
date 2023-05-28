<template>
  <div class="h-100">
    <div class="h-50"
         layout="row top-justify">
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
      <h1>TODO：过滤搜索（根据类别、日期、名称、金额）</h1>
      <v-table density="compact">
        <thead>
        <tr>
          <th class="text-left">
            类别
          </th>
          <th class="text-left">
            日期
          </th>
          <th class="text-left">
            名称
          </th>
          <th class="text-left">
            金额
          </th>
          <th class="text-left">
            详情
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in table"
            :key="item.name">
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
import {ref} from 'vue'
import {useTheme} from "@/store/modules/theme"

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
    data: ['Direct', 'Email', 'Ad Networks', 'Video Ads', 'Search Engines'],
  },
  series: [
    {
      name: 'Traffic Sources',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 335, name: 'Direct'},
        {value: 310, name: 'Email'},
        {value: 234, name: 'Ad Networks'},
        {value: 135, name: 'Video Ads'},
        {value: 1548, name: 'Search Engines'},
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
    text: '年度收支',
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
      data: [150, 230, 224, 218, 135, 147, 260, 110, 130, 120, 130, 140],
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
    data: ['Direct', 'Email', 'Ad Networks', 'Video Ads', 'Search Engines'],
  },
  series: [
    {
      name: 'Traffic Sources',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 335, name: 'Direct'},
        {value: 310, name: 'Email'},
        {value: 234, name: 'Ad Networks'},
        {value: 135, name: 'Video Ads'},
        {value: 1548, name: 'Search Engines'},
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


const table = ref([
  {
    type: '收入',
    date: '2021-01-01',
    name: '工资',
    cost: 10000,
    detail: '工资'
  },
  {
    type: '收入',
    date: '2021-01-01',
    name: '奖金',
    cost: 1000,
    detail: '奖金'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '房租',
    cost: 2000,
    detail: '房租'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '水电',
    cost: 500,
    detail: '水电'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '饮食',
    cost: 1000,
    detail: '饮食'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '交通',
    cost: 500,
    detail: '交通'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '娱乐',
    cost: 1000,
    detail: '娱乐'
  },
  {
    type: '支出',
    date: '2021-01-01',
    name: '其他',
    cost: 500,
    detail: '其他'
  },

])
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
