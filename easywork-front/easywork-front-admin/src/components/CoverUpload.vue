<template>
  <div>
    <el-upload
      name="file"
      :show-file-list="false"
      accept=".png,.PNG,.jpg,.JPG,.jpeg,.JPEG,.gif,.GIF,.bmp,.BMP"
      :multiple="false"
      :http-request="uploadImage"
    >
      <div
        class="cover-upload-btn"
        :style="{ width: width + 'px', height: height + 'px' }"
      >
        <template v-if="modelValue">
          <img :src="proxy.globalInfo.imageUrl + modelValue" />
        </template>
        <template v-else>
          <span class="iconfont icon-jia"></span>
        </template>
      </div>
    </el-upload>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance } from "vue";
const { proxy } = getCurrentInstance();

const props = defineProps({
  width: {
    type: Number,
    default: 150,
  },
  height: {
    type: Number,
    default: 150,
  },
  modelValue: {
    type: Number,
  },
});
const api = {
  uploadFile: "/file/uploadFile",
};
const emit = defineEmits();
const uploadImage = async (file) => {
  file = file.file;
  let result = await proxy.Request({
    url: api.uploadFile,
    params: {
      file: file,
      type: props.type,
    },
  });
  if (!result) {
    return;
  }
  emit("update:modelValue", result.data);
};
</script>

<style lang="scss">
.cover-upload-btn {
  background: rgb(245, 245, 245);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  .iconfont {
    font-size: 50px;
    color: #ddd;
  }
  img {
    width: 100%;
  }
}
</style>