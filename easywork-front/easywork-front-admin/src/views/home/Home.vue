<template>
  <div>
    <div class="part-panel">
      <el-card class="box-card">
        <div slot="header" class="card-title">
          <span>数据概括</span>
        </div>
        <div class="data-list">
          <el-row :gutter="10">
            <el-col :span="4" v-for="item in allDataList">
              <div class="data-item">
                <div class="title">{{ item.statisticsName }}</div>
                <div class="data-panel">
                  <div class="data">{{ item.count }}</div>
                  <div class="pre">
                    昨日新增:<span class="new">{{ item.preCount }}</span>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </div>
    <div class="part-panel">
      <el-card>
        <div slot="header" class="card-title">
          <span>近一周数据概括</span>
        </div>
        <div class="data-list">
          <el-row :gutter="10">
            <el-col :span="12">
              <div ref="myCharts4AppWeekDataRef" class="chart"></div>
            </el-col>

            <el-col :span="12">
              <div ref="myCharts4ContentWeekDataRef" class="chart"></div>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import * as echarts from "echarts";
import { ref, reactive, getCurrentInstance, nextTick, shallowRef } from "vue";
const { proxy } = getCurrentInstance();

const api = {
  getAllData: "/index/getAllData",
  getAppWeekData: "/index/getAppWeekData",
  getContentWeekData: "/index/getContentWeekData",
};

//查询数据概括
const allDataList = ref([]);
const loadAllData = async () => {
  let result = await proxy.Request({
    url: api.getAllData,
  });
  if (!result) {
    return;
  }
  allDataList.value = result.data;
};
loadAllData();

const getOption = (title, xAxisData = [], seriesData = []) => {
  return {
    title: {
      text: title,
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
        textStyle: {
          color: "red",
        },
      },
    },
    legend: {
      x: 200,
    },
    grid: {
      left: 50,
      right: 0,
    },
    xAxis: {
      axisLine: {
        lineStyle: {
          color: "#90979c",
        },
      },
      data: xAxisData,
      axisLabel: {
        interval: 0,
        rotate: "45",
      },
    },
    yAxis: {
      type: "value",
    },
    series: seriesData,
  };
};

//查询App下载
const myCharts4AppWeekDataRef = ref();
const myCharts4AppWeekData = shallowRef();
const init4AppWeekData = () => {
  nextTick(() => {
    myCharts4AppWeekData.value = echarts.init(myCharts4AppWeekDataRef.value);
    //获取数据
    loadAppWeekData();
    console.log("数据获取成功");
    
  });
};
init4AppWeekData();
const loadAppWeekData = async () => {
  let result = await proxy.Request({
    url: api.getAppWeekData,
  });
  if (!result) {
    return;
  }
  const data = result.data;
  const xAxisData = data.dataList;
  const seriesData = [];
  const colors = ["#1B9CFC", "#67C23A"];
  data.itemDataList.forEach((item, index) => {
    seriesData.push({
      name: item.statisticsName,
      type: "bar",
      smooth: true,
      data: item.listData,
      itemStyle: {
        color: colors[index],
      },
    });
  });
  myCharts4AppWeekData.value.setOption(
    getOption("App下载注册用户统计", xAxisData, seriesData)
  );
};

//内容统计
const myCharts4ContentWeekDataRef = ref();
const myCharts4ContentWeekData = shallowRef();
const init4ContentWeekData = () => {
  nextTick(() => {
    myCharts4ContentWeekData.value = echarts.init(myCharts4ContentWeekDataRef.value);
    //获取数据
    loadContentWeekData();
    console.log("数据获取成功");
    
  });
};
init4ContentWeekData();
const loadContentWeekData = async () => {
  let result = await proxy.Request({
    url: api.getContentWeekData,
  });
  if (!result) {
    return;
  }
  const data = result.data;
  const xAxisData = data.dataList;
  const seriesData = [];
  const colors = ["#1B9CFC", "#67C23A","#33166e","#fb7993","#a4c4fc"];
  data.itemDataList.forEach((item, index) => {
    seriesData.push({
      name: item.statisticsName,
      type: "bar",
      smooth: true,
      data: item.listData,
      itemStyle: {
        color: colors[index],
      },
    });
  });
  myCharts4ContentWeekData.value.setOption(
    getOption("内容统计", xAxisData, seriesData)
  );
};
</script>

<style lang="scss" scoped>
.card-title {
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 5px;
}
.part-panel {
  margin-top: 10px;
  &:first-child {
    margin-top: 0px;
  }
}
.data-list {
  .data-item {
    background: #f4f9fd;
    color: #9a9fa6;
    padding: 20px;
    border-radius: 5px;
    width: 100%;
    .data-panel {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .data {
      font-size: 20px;
      color: #000012;
      font-weight: bold;
      margin-top: 10px;
    }
    .pre {
      margin-top: 5px;
      .new {
        color: #ff6873;
      }
    }
  }
}
.chart {
  height: calc(100vh - 400px);
}
</style>
