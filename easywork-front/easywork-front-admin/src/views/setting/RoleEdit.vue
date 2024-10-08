<template>
  <div>
    <Dialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      :buttons="dialogConfig.buttons"
      :showCancel="true"
      @close="dialogConfig.show = false"
    >
      <el-form
        :model="formData"
        :rules="rules"
        ref="formDataRef"
        label-width="80px"
        @submit.prevent="submitForm"
      >
        <el-form-item label="角色名称" prop="roleName">
          <el-input :maxLength="20" v-model="formData.roleName" />
        </el-form-item>

        <el-form-item label="关联菜单" prop="menuIds" v-if="!formData.roleId">
          <div class="tree-panel">
            <el-tree
              ref="menuTreeRef"
              node-key="menuId"
              show-checkbox
              :data="treeData"
              default-expand-all
              :props="replaceFields"
              @check-change="handleMenuTreeChecked"
            />
          </div>
        </el-form-item>

        <el-form-item label="角色描述">
          <el-input
            type="textarea"
            :maxLength="250"
            v-model="formData.roleDesc"
          />
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();

const api = {
  saveRole: "settings/saveRole",
};

const props = defineProps({
  treeData: {
    type: Array,
    default: () => [],
  },
});

const replaceFields = ref({
  label: "menuName",
});


const dialogConfig = ref({
  show: false,
  title: "新增角色",
  buttons: [
    {
      type: "primary",
      text: "保存",
      click: (e) => {
        submitForm();
      },
    },
  ],
});


const formData = ref({});

const formDataRef = ref();
const rules = {
  roleName: [{ required: true, message: "请输入角色名称" }],
  menuIds: [{ required: true, message: "请选择菜单", trigger: "change" }],
};

const showEdit = (data) => {
  dialogConfig.value.show = true;
  nextTick(() => {
    data.menuIds = [];
    formDataRef.value.resetFields();
    dialogConfig.value.title = data.roleId ? "修改角色" : "新增角色";
    formData.value = data;
  });
};

defineExpose({
  showEdit,
});

const menuTreeRef = ref();
const handleMenuTreeChecked = () => {
  formData.value.menuIds = menuTreeRef.value.getCheckedKeys();
};

const emit = defineEmits(["reload"]);

const submitForm = async () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
  });
  let params = {};
  Object.assign(params, formData.value);
  if (params.roleId) {
    params.menuIds = null;
  } else {
    params.menuIds = params.menuIds.join(",");
    let halfMenuIdArray = menuTreeRef.value.getHalfCheckedKeys() || [];
    params.halfMenuIds = halfMenuIdArray.join(",");
  }
  let result = await proxy.Request({
    url: api.saveRole,
    params,
  });
  if (!result) {
    return;
  }
  dialogConfig.value.show = false;
  proxy.Message.success("保存成功");
  emit("reload");
};
</script>

<style lang="scss" scoped>
.tree-panel {
  width: 100%;
  max-height: calc(100vh / 2);
  overflow: auto;
}
</style>