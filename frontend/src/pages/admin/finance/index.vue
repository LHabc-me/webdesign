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
         layout="row center-center">
      <h1>这里放详细信息</h1>
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
