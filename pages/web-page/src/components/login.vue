<template>
  <div class="login-container">
    <div class="login-box">
      <!--      头像-->
      <!--      'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'-->
      <el-avatar :src="'https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/dd.jpg'"
                 class="avatar-box"></el-avatar>
      <!--      表单-->
      <el-form :model="loginForm" ref="loginFormRef" :rules="loginFormRules" label-width="0px" class="login-form">
        <!--          用户名 -->
        <el-form-item prop="username">
          <el-input v-model=loginForm.username prefix-icon="el-icon-user"></el-input>
        </el-form-item>

        <!--          密码-->
        <el-form-item prop="password">
          <el-input type="password" v-on:keyup.enter.native="login" v-model=loginForm.password
                    prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="login()">登录</el-button>
          <el-button type="info" @click="resetLoginForm()">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import md5 from "md5";

export default {
  name: "login",
  data() {
    return {
      //表单数据绑定
      loginForm: {},
      //表单验证规则
      loginFormRules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      const that = this;
      this.$refs.loginFormRef.validate(valid => {
        if (!valid) return;
        let pass = this.loginForm.password;
        //10次hash
        for (let i = 0; i < 10; i++) {
          pass = md5(pass)
        }
        console.log(pass);
        this.$http.post('login', {username: this.loginForm.username, password: pass}).then(function (result) {
          // console.log(result.data);
          if (result.data.code === "-1") {
            return that.$message.error("登陆失败，账户名或密码错误！");
          } else {
            console.log(result.data);
            window.sessionStorage.setItem("token", result.data.result);
            that.$message.success("登录成功");
            that.$router.push("/");
          }
        }).catch(function () {
          that.$message.error("登陆失败，请检查您的网络连接！");
        });
      })
    },

  }
}
</script>

<style lang="less" scoped>
.login-container {
  //background-color: #2b4b6b;
  //height: 100%;
  background: linear-gradient(217deg, rgba(255, 0, 0, .8), rgba(255, 0, 0, 0) 70.71%),
  linear-gradient(127deg, rgba(0, 255, 0, .8), rgba(0, 255, 0, 0) 70.71%),
  linear-gradient(336deg, rgba(0, 0, 255, .8), rgba(0, 0, 255, 0) 70.71%);
  height: 100%;
}

.login-box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar-box {
  height: 100px;
  width: 100px;
  left: 50%;
  position: absolute;

  transform: translate(-50%, -50%);
  align-content: center;
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.login-form {
  position: absolute;
  //top: auto;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>