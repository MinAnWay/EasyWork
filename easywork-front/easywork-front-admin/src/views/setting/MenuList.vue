<template>
  <div>
    <div class="menu-tree">
      <el-row :gutter="10">
        <el-col :span="7">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>菜单管理</span>
              </div>
            </template>
            <el-tree
                ref="refTree"
                :data="treeData"
                default-expand-all
                node-key="menuId"
                :expand-on-click-node="false"
                :props="treeProps"
                :highlight-current="true"
                @node-click="nodeClick"
                class="tree-panel"
            >
              <template #default="{ node, data }">
                <span class="custom-node-style">
                  <span class="node-title">{{ data.menuName }}</span>
                  <el-dropdown trigger="click">
                    <span class="iconfont icon-more" v-has="proxy.PermissionCode.menu.edit"></span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click="showEditDialog('add', data)" 
                        >添加子菜单</el-dropdown-item
                        >
                        <el-dropdown-item
                            v-if="data.pid !== -1"
                            @click="showEditDialog('edit', data)"
                        >修改</el-dropdown-item
                        >
                        <el-dropdown-item
                            v-if="data.pid !== -1"
                            @click="delMenu(data)"
                        >删除</el-dropdown-item
                        >
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </span>
              </template>
            </el-tree>
          </el-card>
        </el-col>

        <el-col :span="17">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>菜单详情</span>
              </div>
            </template>
            <el-form
                :model="detailData"
                label-width="100px"
                class="detail-form"
            >
              <el-form-item label="菜单ID:">
                {{ detailData.menuId }}
              </el-form-item>

              <el-form-item label="菜单名称:">
                {{ detailData.menuName }}
              </el-form-item>

              <el-form-item label="菜单层级:">
                <el-breadcrumb
                    separator-class="el-icon-arrow-right"
                    :style="{ 'line-height': '40px' }"
                >
                  <el-breadcrumb-item
                      v-for="(item, index) in detailData.menuNames"
                      :key="index"
                  >{{ item }}
                  </el-breadcrumb-item
                  >
                </el-breadcrumb>
              </el-form-item>

              <el-form-item label="菜单类型:">
                {{ detailData.menuType == 0 ? "菜单" : "按钮" }}
              </el-form-item>

              <el-form-item label="请求路径:">
                {{ detailData.menuUrl ? detailData.menuUrl : "无" }}
              </el-form-item>

              <el-form-item label="权限编码:">
                {{ detailData.permissionCode }}
              </el-form-item>

              <el-form-item label="菜单图标:">
                <span
                    :class="'iconfont icon-' + detailData.icon"
                    v-if="detailData.icon"
                ></span>
                <span v-else>无</span>
              </el-form-item>

              <el-form-item label="排序号:">
                {{ detailData.sort }}
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <MenuEdit
        ref="menuEditRef"
        :treeData="treeData"
        @reload="loadTreeData"
    ></MenuEdit>
  </div>
</template>

<script setup>
import MenuEdit from "./MenuEdit.vue";
import {ref, reactive, getCurrentInstance, nextTick} from "vue";

const {proxy} = getCurrentInstance();


const api = {
  loadMenu: "settings/menuList",
  delMenu: "settings/delMenu",
};

const refTree = ref();
const treeProps = {
  class: "cust-tree-item",
  children: "children",
  label: "menuName",
  value: "menuId",
};

//当前的node
const treeData = ref([]);
const loadTreeData = async () => {
  let result = await proxy.Request({
    url: api.loadMenu,
  });
  if (!result) {
    return;
  }
  const data = result.data;
  treeData.value = data;

  await nextTick(() => {
    let firstNodeData = data[0].children ? data[0].children[0] : data[0];
    let curKey = firstNodeData.menuId;
    refTree.value.setCurrentKey(curKey);
    const curNode = refTree.value.getNode(curKey);
    nodeClick(curNode.data, curNode);
  });
};
loadTreeData();

//详情
const detailData = ref({});

const nodeClick = (data, node) => {
  let menuNames = [];
  getMenuNames(node, menuNames);
  data.menuNames = menuNames;
  Object.assign(detailData.value, data);
};

//递归获取菜单名称
const getMenuNames = (node, menuNames) => {
  if (node.data.menuName) {
    menuNames.unshift(node.data.menuName);
  }
  if (node.parent) {
    getMenuNames(node.parent, menuNames);
  }
};

const delMenu = (data) => {
  proxy.Confirm("确定删除该菜单？", async () => {
    let result = await proxy.Request({
      url: api.delMenu,
      params: {
        menuId: data.menuId,
      },
    });
    if (!result) {
      return;
    }
    proxy.Message.success("删除成功");
    await loadTreeData();
  });
};

const menuEditRef = ref();
const showEditDialog = (type, data) => {
  menuEditRef.value.showEditDialog(type, data);
};
</script>

<style lang="scss" scoped>
.menu-tree {
  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .iconfont {
      color: #409eff;
      font-size: 20px;
      cursor: pointer;
    }
  }

  .tree-panel {
    overflow: auto;
    height: calc(100vh - 250px);
  }

  :deep .el-tree-node__content {
    height: 40px;
  }

  .custom-node-style {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
  }
}
</style>
