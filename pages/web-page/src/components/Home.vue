<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>导航一</el-breadcrumb-item>
      <el-breadcrumb-item>详细一</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    卡片布局区-->
    <el-card>
      <!--      添加与搜索-->
      <el-row gutter="20">
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
}
</script>

<style scoped>

</style>