<template>
  <div>
    <el-table
      ref="dataTable"
      :data="dataSource.list || []"
      :height="tableHeight"
      :stripe="options.stripe"
      :border="options.border"
      highlight-current-row
      @row-click="handleRowClick"
      @selection-change="handleSelectionChange"
    >
      <!--selection选择框-->
      <el-table-column
        v-if="options.selectType && options.selectType == 'checkbox'"
        type="selection"
        width="50"
        align="center"
      ></el-table-column>
      <!--序号-->
      <el-table-column
        v-if="options.showIndex"
        label="序号"
        type="index"
        width="60"
        align="center"
      ></el-table-column>
      <!--数据列-->
      <template v-for="(column, index) in columns">
        <template v-if="column.scopedSlots">
          <el-table-column
            :key="index"
            :prop="column.prop"
            :label="column.label"
            :align="column.align || 'left'"
            :width="column.width"
          >
            <template #default="scope">
              <slot
                :name="column.scopedSlots"
                :index="scope.$index"
                :row="scope.row"
              >
              </slot>
            </template>
          </el-table-column>
        </template>
        <template v-else>
          <el-table-column
            :key="index"
            :prop="column.prop"
            :label="column.label"
            :align="column.align || 'left'"
            :width="column.width"
            :fixed="column.fixed"
          >
          </el-table-column>
        </template>
      </template>
    </el-table>
    <!--分页-->
    <div class="pagination" v-if="showPagination">
      <el-pagination
        v-if="dataSource.totalCount"
        background
        :total="dataSource.totalCount"
        :page-sizes="[15, 30, 50, 100]"
        :page-size="dataSource.pageSize"
        :current-page.sync="dataSource.pageNo"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handlePageSizeChange"
        @current-change="handlePageNoChange"
        style="text-align: right"
      ></el-pagination>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  dataSource: Object,
  showPagination: {
    type: Boolean,
    default: true,
  },
  options: {
    type: Object,
    default: {
      tableHeight: null,
      stripe: true,
      border: false,
      extHeight: 0,
      showIndex: false,
    },
  },
  columns: Array,
  fetch: Function,
  initFetch: {
    type: Boolean,
    default: true,
  },
});

const topHeight = 60 + 85 + 30 + 46;

const tableHeight = ref(
  props.options.tableHeight
    ? props.options.tableHeight
    : window.innerHeight - topHeight - (props.options.extHeight || 0)
);

const init = () => {
  if (props.initFetch && props.fetch) {
    props.fetch();
  }
};
init();

const dataTable = ref();
const clearSelection = () => {
  dataTable.value.clearSelection();
};

const setCurrentRow = (rowKey, rowValue) => {
  let row = props.dataSource.list.find((item) => {
    return item[rowKey] === rowValue;
  });
  dataTable.value.setCurrentRow(row);
};

defineExpose({ setCurrentRow, clearSelection });

const emit = defineEmits(["rowSelected", "rowClick"]);
const handleRowClick = (row) => {
  emit("rowClick", row);
};

const handleSelectionChange = (row) => {
  emit("rowSelected", row);
};

const handlePageSizeChange = (size) => {
  props.dataSource.pageSize = size;
  props.dataSource.pageNo = 1;
  props.fetch();
};

const handlePageNoChange = (pageNo) => {
  props.dataSource.pageNo = pageNo;
  props.fetch();
};
</script>

<style lang="scss" scoped>
.pagination {
  padding-top: 10px;
}
.el-pagination {
  justify-content: right;
}

.el-table__body tr.current-row > td.el-table__cell {
  background-color: #e6f0f9;
}

.el-table__body tr:hover > td.el-table__cell {
  background-color: #e6f0f9 !important;
}
</style>
