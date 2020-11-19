<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <span>安全数据库系统</span>
      </div>
      <ElButton type="primary" @click="logout()">Sign Out</ElButton>
<!--      <ElButton type="primary" @click="test()">接口测试</ElButton>-->
    </el-header>
    <el-container>
      <!--      侧边栏-->
      <el-aside :width="collapse? '64px':'200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu
            :collapse="collapse"
            default-active="2"
            class="el-menu-vertical-demo"
            background-color="#001529"
            :collapse-transition="false"
            text-color="#fff" :router="true"
            active-text-color="#50DE2F">
          <!--          一级菜单-->
          <el-submenu index="1">
            <!--            模板-->
            <template slot="title">
              <!--              图标-->
              <i class="el-icon-location"></i>
              <!--              文本-->
              <span>导航一</span>
            </template>
            <el-menu-item index="1-4">
              <template slot="title">
                <!--              图标-->
                <i class="el-icon-menu"></i>
                <!--              文本-->
                <span>导航一</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
        <el-menu
            :collapse="collapse"
            default-active="2"
            class="el-menu-vertical-demo"
            :collapse-transition="false"
            background-color="#001529"
            text-color="#fff" :router="true"
            active-text-color="#50DE2F">
          <!--          一级菜单-->
          <el-submenu index="1">
            <!--            模板-->
            <template slot="title">
              <!--              图标-->
              <i class="el-icon-location"></i>
              <!--              文本-->
              <span>导航一</span>
            </template>
            <el-menu-item index="1-4">
              <template slot="title">
                <!--              图标-->
                <i class="el-icon-menu"></i>
                <!--              文本-->
                <span>导航一</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
export default {
  name: "Main",
  data: function () {
    return {
      collapse: false,
      src: require("../assets/icon.png")
    }
  },
  methods: {
    logout: function () {
      window.localStorage.clear();
      this.$message.success("登出成功！");
      this.$router.push("login");
    },
    toggleCollapse: function () {
      this.collapse = !this.collapse;
    },
    test: function () {
      const that = this;
      this.$http.get("test").then(function (res) {
            that.$message.success(res.data);
          }
      )
    }
  }

}
</script>

<style lang="less" scoped>
.el-header {
  background-color: #324A8B;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #ffffff;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}

.home-container {
  height: 100%;
}

.el-aside {
  background-color: #001529;

  .el-menu {
    border-right: none;
  }
}

.toggle-button {
  background-color: #e9e9eb;
  font-size: 10px;
  line-height: 24px;
  color: #324A8B;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}

.el-main {
  background-color: #F7F8FA;
}
</style>