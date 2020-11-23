<template>
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>患者管理</el-breadcrumb-item>
      <el-breadcrumb-item>增加患者</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    卡片布局区-->
    <el-card>
      <!--      添加与搜索-->
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary">Demo</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>

export default {
  name: "Home",
  data: function () {
    return {
      input3: ""
    }
  }
}
</script>

<style scoped>

</style>