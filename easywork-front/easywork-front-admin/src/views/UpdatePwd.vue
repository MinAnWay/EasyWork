<template>
  <div>
    <Dialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      :buttons="dialogConfig.buttons"
      width="400px"
      :showCancel="false"
      @close="dialogConfig.show = false"
    >
      <el-form
        :model="formData"
        :rules="rules"
        ref="formDataRef"
        label-width="80px"
        @submit.prevent
      >
        <!-- input输入 -->
        <el-form-item label="新密码" prop="password">
          <el-input
            type="password"
            show-password
            size="large"
            placeholder="请输入新密码"
            v-model.trim="formData.password"
          >
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="rePassword">
          <el-input
            type="password"
            show-password
            size="large"
            placeholder="请再次输入新密码"
            v-model.trim="formData.rePassword"
          >
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();

const api = {
  updateMyPwd: "/updateMyPwd",
};

const dialogConfig = ref({
  show: false,
  title: "修改密码",
  buttons: [
    {
      type: "primary",
      text: "确定",
      click: () => {
        save();
      },
    },
  ],
});

const checkRePassword = (rule, value, callback) => {
  if (value !== formData.value.password) {
    callback(new Error(rule.message));
  } else {
    callback();
  }
};

const formData = ref({});
const formDataRef = ref();
const rules = {
  password: [
    { required: true, message: "请输入密码" },
    {
      validator: proxy.Verify.password,
      message: "密码只能是数字、字母、特殊字符,6-18位",
    },
  ],
  rePassword: [
    { required: true, message: "请再次输入密码" },
    {
      validator: checkRePassword,
      message: "两次密码输入不一致",
    },
  ],
};

const show = () => {
  dialogConfig.value.show = true;
  nextTick(() => {
    formDataRef.value.resetFields();
    formData.value = {};
  });
};
defineExpose({
  show,
});

const save = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
    let params = Object.assign({}, formData.value);
    let result = await proxy.Request({
      url: api.updateMyPwd,
      params,
    });
    if (!result) {
      return;
    }
    proxy.Message.success("密码修改成功");
    dialogConfig.value.show = false;
  });
};
</script>

<style lang="scss" scoped>
</style>
