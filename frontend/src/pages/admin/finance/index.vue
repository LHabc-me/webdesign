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
    <div class="h-50 mx-auto"
         layout="column top-center">
      <!--      <h1>TODO：过滤搜索（根据类别、日期、名称、金额）</h1>-->
      <v-table density="compact" class="w-100">
        <thead>
        <tr>
          <th class="text-center">
            {{ i18n.global.t('type') }}
          </th>
          <th class="text-center">
            {{ i18n.global.t('date') }}
          </th>
          <th class="text-center">
            {{ i18n.global.t('name') }}
          </th>
          <th class="text-center">
            {{ i18n.global.t('amount') }}
          </th>
          <th class="text-center w-50">
            {{ i18n.global.t('detail') }}
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
import {computed, onActivated, ref, watch} from 'vue'
import {useTheme} from '@/store/modules/theme'
import {post} from '@/net'
import {i18n} from '@/i18n'
import {useI18n} from '@/store/modules/i18n'
import {useRouter} from 'vue-router'

const theme = useTheme()
const router = useRouter()
const i18nStore = useI18n()

const optionMonthPieChart = computed(() => {
  return {
    title: {
      text: i18n.global.t('month-income-and-expenditure'),
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)',
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: [
        i18n.global.t('profit'),
        i18n.global.t('advertising-fee'),
        i18n.global.t('labor-costs'),
        i18n.global.t('server-rental-fee'),
      ],
    },
    series: [
      {
        name: i18n.global.t('annual-income-and-expenditure'),
        type: 'pie',
        radius: '55%',
        center: ['50%', '60%'],
        data: [
          {value: 3100, name: i18n.global.t('server-rental-fee')},
          {value: 2340, name: i18n.global.t('advertising-fee')},
          {value: 6448, name: i18n.global.t('labor-costs')},
          {value: 15048, name: i18n.global.t('profit')},
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
  }
})

const optionYearLineChart = computed(() => {
  return {
    title: {
      text: i18n.global.t('annual-income'),
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
  }
})

const optionYearPieChart = computed(() => {
  return {
    title: {
      text: i18n.global.t('annual-income-and-expenditure'),
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)',
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: [
        i18n.global.t('profit'),
        i18n.global.t('advertising-fee'),
        i18n.global.t('labor-costs'),
        i18n.global.t('server-rental-fee')
      ],
    },
    series: [
      {
        name: i18n.global.t('annual-income-and-expenditure'),
        type: 'pie',
        radius: '55%',
        center: ['50%', '60%'],
        data: [
          {value: 13120, name: i18n.global.t('server-rental-fee')},
          {value: 25344, name: i18n.global.t('advertising-fee')},
          {value: 133500, name: i18n.global.t('labor-costs')},
          {value: 173227, name: i18n.global.t('profit')},
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
  }
})


const tableTail = ref([])

// 利润、广告费、人工成本、服务器租赁费
const table = computed(() => {
  return [
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('labor-costs'),
      cost: 10000,
      detail: i18n.global.t('salary'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('labor-costs'),
      cost: 1000,
      detail: i18n.global.t('bonus'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('labor-costs'),
      cost: 2000,
      detail: i18n.global.t('salary'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('server-rental-fee'),
      cost: 500,
      detail: i18n.global.t('server-rental-fee'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('advertising-fee'),
      cost: 1000,
      detail: i18n.global.t('advertising-fee'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('advertising-fee'),
      cost: 500,
      detail: i18n.global.t('advertising-fee'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('server-rental-fee'),
      cost: 1000,
      detail: i18n.global.t('server-rental-fee'),
    },
    {
      type: i18n.global.t('expenditure'),
      date: '2021-01-01',
      name: i18n.global.t('labor-costs'),
      cost: 500,
      detail: i18n.global.t('bonus'),
    },
    ...tableTail.value.map(item => {
      return {
        type: i18n.global.t('income'),
        date: item.time,
        name: i18n.global.t('sail-book'),
        cost: item.cost,
        detail: `${i18n.global.t('user')} ${item.userId} ${i18n.global.t('purchase-book')} ${item.bookId}`,
      }
    })]
})

onActivated(() => {
  post('/api/admin/cost/info')
    .then(({data}) => {
      data.forEach(item => {
        tableTail.value.push(item)
      })
    })
})
</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
    requireLogin: true,
  }
}
</route>
