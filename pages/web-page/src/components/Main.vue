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
            class="el-menu-vertical-demo"
            background-color="#001529"
            :collapse-transition="false"
            text-color="#fff"
            :router="true"
            :default-active="activePath"
            active-text-color="#50DE2F"
            v-for="i in list" :key="i.index">
          <!--          一级菜单-->
          <el-submenu :index="i.index+''">
            <!--            模板-->
            <template slot="title">
              <!--              图标-->
              <i class="el-icon-location"></i>
              <!--              文本-->
              <span>{{ i.name }}</span>
            </template>
            <el-menu-item :index="x.path" v-for="x in i.lists" :key="x.index" @click="saveState(x.path)">
              <template slot="title">
                <!--              图标-->
                <i class="el-icon-menu"></i>
                <!--              文本-->
                <span>{{ x.name }}</span>
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
      src: require("../assets/icon.png"),
      activePath: "",
      list: [
        {
          index: 1, name: "患者管理", lists: [
            {index: 1, name: "增加患者信息", path: "add"},
            // {index: 2, name: "功能二", path: "1-1"},
          ]
        },
        // {
        //   index: 2, name: "功能二", lists: [
        //     {index: 1, name: "功能二-1", path: "2-1"},
        //     {index: 2, name: "功能二-2", path: "2-2"},
        //   ]
        // },
        // {
        //   index: 3, name: "功能三", lists: [
        //     {index: 1, name: "功能3-1", path: "3-1"},
        //     {index: 2, name: "功能3-2", path: "3-2"},
        //   ]
        // }
        ]
    }
  },
  methods: {
    logout: function () {
      window.localStorage.clear();
      window.sessionStorage.setItem("token","null");
      this.$message.success("登出成功！");
      this.$router.push("login");
    }
    ,
    toggleCollapse: function () {
      this.collapse = !this.collapse;
    }
    ,
    test: function () {
      const that = this;
      this.$http.get("test").then(function (res) {
            that.$message.success(res.data);
          }
      )
    }
    ,
    saveState: function (path) {
      window.sessionStorage.setItem("path", path);
      this.activePath = path;
    }
  }
  ,
  created() {
    this.activePath = window.sessionStorage.getItem("path");
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