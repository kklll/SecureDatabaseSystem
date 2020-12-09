<template>
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>患者管理</el-breadcrumb-item>
      <el-breadcrumb-item>增加患者信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    卡片布局区-->
    <el-card>
      <!--      添加与搜索-->
      <el-row :gutter="20">
        <el-col :span="7">
          <!--          <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">-->
          <!--            <el-button slot="append" icon="el-icon-search"></el-button>-->
          <!--          </el-input>-->
        </el-col>
        <el-col :span="4">
          <!--          <el-button type="primary">Demo</el-button>-->
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="dialogFormVisible=true">添加患者信息</el-button>
        </el-col>
      </el-row>
      <template>
        <el-table :data="tableData" style="width: 100%">
          <el-table-column type="expand" prop="patientName" align="center">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="患者姓名:">
                  <span>{{ props.row.patientName }}</span>
                </el-form-item>
                <el-form-item label="患者性别:">
                  <span>{{ props.row.sex }}</span>
                </el-form-item>
                <el-form-item label="家庭住址:">
                  <span>{{ props.row.address }}</span>
                </el-form-item>
                <el-form-item label="出生日期:">
                  <span>{{ props.row.birthday }}</span>
                </el-form-item>
                <el-form-item label="联系电话:">
                  <span>{{ props.row.phone }}</span>
                </el-form-item>
                <el-form-item label="主治医生:">
                  <span>{{ props.row.doctor }}</span>
                </el-form-item>
                <el-form-item label="症状:">
                  <span>{{ props.row.symptom }}</span>
                </el-form-item>
                <el-form-item label="所属科室:">
                  <span>{{ props.row.department }}</span>
                </el-form-item>
                <el-form-item label="病史:">
                  <span>{{ props.row.medicalHistory }}</span>
                </el-form-item>
                <el-form-item label="医嘱:">
                  <span>{{ props.row.doctorOrder }}</span>
                </el-form-item>
                <el-form-item label="就诊日期:">
                  <span>{{ props.row.clinicDate }}</span>
                </el-form-item>
                <el-form-item label="出院日期:">
                  <span>{{ props.row.leaveDate }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="patientName" label="患者姓名" align="center">
          </el-table-column>
          <el-table-column prop="sex" label="性别" center align="center">
          </el-table-column>
          <el-table-column prop="doctor" label="主治医生" center align="center">
          </el-table-column>
          <el-table-column prop="symptom" label="症状" center align="center">
          </el-table-column>
          <el-table-column prop="department" label="所属科室" center align="center">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  type="warning"
                  @click="handleEdit(scope.$index, scope.row)">编辑
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-card>
    <el-dialog title="患者信息" :visible.sync="dialogFormVisible">
      <el-form :model="addForm" ref="addRef" :rules="rules">
        <el-form-item label="患者姓名" prop="patientName" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.patientName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="患者住址" prop="address" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idcrad" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.idcrad" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="addForm.birthday"
              align="right"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主治医生" prop="doctor" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.doctor" placeholder="请选择医生">
            <el-option :label="i" :value="i" v-for="i in doctors" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="症状" prop="symptom" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.symptom" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="所属科室" prop="department" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.department" placeholder="请选择科室">
            <el-option :label="i" :value="i" v-for="i in departments" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病史" prop="medicalHistory" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.medicalHistory" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="医嘱" prop="doctorOrder" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.doctorOrder" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="出院日期" prop="leaveDate" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="addForm.leaveDate"
              align="right"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <!--          <iframe width="100%" height="500"-->
      <!--                  src="//player.bilibili.com/player.html?aid=82179137&bvid=BV1HJ411L7DP&cid=140610898&page=1"-->
      <!--                  scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"></iframe>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload('addRef')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="患者信息" :visible.sync="dialogFormVisible">
      <el-form :model="addForm" ref="addRef" :rules="rules">
        <el-form-item label="患者姓名" prop="patientName" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.patientName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="患者住址" prop="address" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idcrad" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.idcrad" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="addForm.birthday"
              align="right"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" :label-width="formLabelWidth" required>
          <el-input v-model="addForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主治医生" prop="doctor" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.doctor" placeholder="请选择医生">
            <el-option :label="i" :value="i" v-for="i in doctors" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="症状" prop="symptom" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.symptom" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="所属科室" prop="department" :label-width="formLabelWidth" required>
          <el-select v-model="addForm.department" placeholder="请选择科室">
            <el-option :label="i" :value="i" v-for="i in departments" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病史" prop="medicalHistory" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.medicalHistory" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="医嘱" prop="doctorOrder" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.doctorOrder" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="出院日期" prop="leaveDate" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="addForm.leaveDate"
              align="right"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <!--          <iframe width="100%" height="500"-->
      <!--                  src="//player.bilibili.com/player.html?aid=82179137&bvid=BV1HJ411L7DP&cid=140610898&page=1"-->
      <!--                  scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"></iframe>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" :disabled="disableOk" @click="upload('addRef')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="患者信息" :visible.sync="dialogEditVisible">
      <el-form :model="editData" ref="changeRef" :rules="rules">
        <el-form-item label="患者姓名" prop="patientName" :label-width="formLabelWidth" required>
          <el-input v-model="editData.patientName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :label-width="formLabelWidth" required>
          <el-select v-model="editData.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="患者住址" prop="address" :label-width="formLabelWidth" required>
          <el-input v-model="editData.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idcrad" :label-width="formLabelWidth" required>
          <el-input v-model="editData.idcrad" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="editData.birthday"
              align="right"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" :label-width="formLabelWidth" required>
          <el-input v-model="editData.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主治医生" prop="doctor" :label-width="formLabelWidth" required>
          <el-select v-model="editData.doctor" placeholder="请选择医生">
            <el-option :label="i" :value="i" v-for="i in doctors" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="症状" prop="symptom" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="editData.symptom" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="所属科室" prop="department" :label-width="formLabelWidth" required>
          <el-select v-model="editData.department" placeholder="请选择科室">
            <el-option :label="i" :value="i" v-for="i in departments" :key="i"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病史" prop="medicalHistory" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="editData.medicalHistory" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="医嘱" prop="doctorOrder" :label-width="formLabelWidth" required>
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="editData.doctorOrder" maxlength="500"
                    show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="出院日期" prop="leaveDate" :label-width="formLabelWidth" required>
          <el-date-picker
              v-model="editData.leaveDate"
              align="right"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
<!--      <iframe width="100%" height="500"-->
<!--              src="//player.bilibili.com/player.html?aid=82179137&bvid=BV1HJ411L7DP&cid=140610898&page=1"-->
<!--              scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"></iframe>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取 消</el-button>
        <el-button type="primary" :disabled="disableOk" @click="change('changeRef')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "add",
  data: function () {
    return {
      tableData: [],
      input3: "",
      editData: {},
      disableOk: false,
      dialogEditVisible: false,
      addForm: {name: "", sex: ""},
      dialogFormVisible: false,
      formLabelWidth: '120px',
      doctors: ["张医生","李医生","刘医生","王医生","牛医生","阎医生","马医生"],
      departments: ["内科", "外科", "妇产科", "儿童科", "五官科", "传染科"],
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      rules: {
        patientName: {required: true, message: '请输入必填项'},
        sex: {required: true, message: '请输入必填项'},
        address: {required: true, message: '请输入必填项'},
        idcrad: {required: true, message: '请输入必填项'},
        birthday: {required: true, message: '请输入必填项'},
        phone: {required: true, message: '请输入必填项'},
        doctor: {required: true, message: '请输入必填项'},
        symptom: {required: true, message: '请输入必填项'},
        department: {required: true, message: '请输入必填项'},
        doctorOrder: {required: true, message: '请输入必填项'},
        medicalHistory: {required: true, message: '请输入必填项'},
        clinicDate: {required: true, message: '请输入必填项'},
        leaveDate: {required: true, message: '请输入必填项'},
      }
    }
  },
  methods: {
    upload(formName) {
      const that = this;
      this.disableOk = true;
      this.$refs[formName].validate(valid => {
        if (!valid) return;
        this.$http.post("add", this.addForm).then(function (res) {
          console.log(res.data);
          that.disableOk = false;
          that.$message.success(res.data.message);
          that.dialogFormVisible = false;
        }).catch(function () {
          that.disableOk = false;
        })
      })
    }, change(formName) {
      const that = this;
      this.disableOk = true;
      this.$refs[formName].validate(valid => {
        if (!valid) return;
        this.$http.post("change", this.editData).then(function (res) {
          console.log(res.data);
          that.disableOk = false;
          that.$message.success(res.data.message);
          that.dialogEditVisible = false;
        }).catch(function () {
          that.disableOk = false;
        })
      })
    },
    getTableDate() {
      const that = this;
      this.$http("select").then(function (res) {
        console.log(res.data.result);
        that.tableData = res.data.result;
        console.log(that.tableData);
      }).catch()
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.dialogEditVisible = true;
      this.editData = row;
    }
  },
  created() {
    this.getTableDate();
  }
}
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
  font-weight: bold;
}
</style>