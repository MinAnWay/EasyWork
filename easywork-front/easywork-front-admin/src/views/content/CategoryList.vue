<template>
  <div>
    <div class="top-panel">
      <el-card>
        <el-button
          type="primary"
          @click="showEdit()"
          v-has="proxy.PermissionCode.account.edit"
          >新增分类</el-button
        >
      </el-card>
    </div>

    <el-card class="table-data-card">
      <template #header>
        <span>用户列表</span>
      </template>
      <Table
        ref="tableInfoRef"
        :columns="columns"
        :fetch="loadDataList"
        :dataSource="tableData"
        :options="tableOptions"
        :extHeight="tableOptions.extHeight"
        :showPagintation="false"
      >
        <template #iconPathSlot="{ index, row }">
          <Cover
            :cover="row.iconPath"
            :bgColor="row.bgColor"
            :title="row.categoryName"
          ></Cover>
        </template>

        <template #typeSlot="{ index, row }">
          <span v-if="row.type == 0">问题分类</span>
          <span v-if="row.type == 1">考题分类</span>
          <span v-if="row.type == 2">问题/考题分类</span>
        </template>

        <template #slotOperation="{ index, row }">
          <div class="roe-op-panel">
            <a
              class="a-link"
              href="javascript:void(0)"
              @click="showEdit(row)"
              v-has="proxy.PermissionCode.category.edit"
              >修改</a
            >

            <a
              class="a-link"
              href="javascript:void(0)"
              style="padding-left: 10px"
              @click="delCategory(row)"
              v-has="proxy.PermissionCode.category.del"
              >删除</a
            >

            <a
              href="javascript:void(0)"
              style="padding-left: 10px"
              @click="changeSort(index, 'up')"
              v-has="proxy.PermissionCode.category.del"
              :class="[index == 0 ? 'not-allow' : 'a-link']"
              >上移</a
            >

            <a
              href="javascript:void(0)"
              style="padding-left: 10px"
              @click="changeSort(index, 'down')"
              v-has="proxy.PermissionCode.category.del"
              :class="[
                index == tableData.list.length - 1 ? 'not-allow' : 'a-link',
              ]"
              >下移</a
            >
          </div>
        </template>
      </Table>
    </el-card>
    <CategoryEdit ref="categoryEditRef" @reload="loadDataList"></CategoryEdit>
  </div>
</template>

<script setup>
import CategoryEdit from "./CategoryEdit.vue";
import Cover from "@/components/Cover.vue";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();

const userInfo = ref(
  JSON.parse(sessionStorage.getItem("userInfo")) || { menuList: [] }
);

const api = {
  loadCategory: "/category/loadAllCategory",
  saveCategory: "/category/saveCategory",
  delCategory: "/category/delCategory",
  changeSort: "/category/changeSort",
};

const searchForm = ref({});

const tableData = ref({});
const tableOptions = ref({
  extHeight: 90,
});

const columns = [
  {
    label: "封面",
    prop: "iconPath",
    scopedSlots: "iconPathSlot",
    width: 150,
  },
  {
    label: "分类名称",
    prop: "categoryName",
  },
  {
    label: "类型",
    prop: "type",
    scopedSlots: "typeSlot",
  },
  {
    label: "操作",
    prop: "operation",
    width: 200,
    scopedSlots: "slotOperation",
  },
];

const tableInfoRef = ref({});
const loadDataList = async () => {
  let result = await proxy.Request({
    url: api.loadCategory,
  });
  if (!result) {
    return;
  }
  tableData.value.list = result.data;
};

//删除
const delCategory = (data) => {
  proxy.Confirm(`确定要删除【${data.categoryName}】吗？`, async () => {
    let result = await proxy.Request({
      url: api.delCategory,
      params: {
        categoryId: data.categoryId,
      },
    });
    if (!result) {
      return;
    }
    proxy.Message.success("删除成功");
    loadDataList();
  });
};

const changeSort = async (index, type) => {
  let dataList = tableData.value.list;
  if (
    (type == "down" && index == dataList.length - 1) ||
    (type == "up" && index == 0)
  ) {
    return;
  }
  let temp = dataList[index];
  let number = type == "down" ? 1 : -1;
  dataList.splice(index, 1);
  dataList.splice(index + number, 0, temp);
  let categoryIds = [];
  dataList.forEach((element) => {
    categoryIds.push(element.categoryId);
    
  });
  let result = await proxy.Request({
    url: api.changeSort,
    params: {
      categoryIds: categoryIds.join(","),
    },
  });
  
  if (!result) {
    return;
  }
  proxy.Message.success("重新排序成功");
  loadDataList();
};

const categoryEditRef = ref();
const showEdit = (data = {}) => {
  categoryEditRef.value.showEdit(Object.assign({}, data));
};
</script>

<style lang="scss" scoped>
.detail-tree-panel {
  height: calc(100vh - 273px);
  overflow: auto;
  width: 100%;
}

.not-allow {
  cursor: not-allowed;
  color: #ddd;
  text-decoration: none;
}
</style>