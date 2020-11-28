package com.nuc.securedatabasesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nuc.securedatabasesystem.core.Secret;
import com.nuc.securedatabasesystem.core.SecretTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HospitalRecords
 * @Deacription TODO
 * @Author DeepBlue
 * @Date 2020/11/21 22:50
 * @Version 1.0
 **/

@Data
@SecretTable
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hospital_records")
public class HospitalRecords {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 患者姓名
     */
    @Secret
    @TableField(value = "patient_name")
    private String patientName;

    /**
     * 患者性别
     */
    @Secret
    @TableField(value = "sex")
    private String sex;

    /**
     * 患者住址
     */
    @Secret
    @TableField(value = "address")
    private String address;

    /**
     * 患者身份证号码
     */
    @Secret
    @TableField(value = "idcrad")
    private String idcrad;

    /**
     * 患者出生日期
     */
    @Secret
    @TableField(value = "birthday")
    private String birthday;

    /**
     * 联系电话
     */
    @Secret
    @TableField(value = "phone")
    private String phone;

    /**
     * 主治医生姓名
     */
    @TableField(value = "doctor")
    private String doctor;

    /**
     * 症状
     */
    @Secret
    @TableField(value = "symptom")
    private String symptom;

    /**
     * 所属科室
     */
    @TableField(value = "department")
    private String department;

    /**
     * 病史
     */
    @Secret
    @TableField(value = "medical_history")
    private String medicalHistory;

    /**
     * 医嘱
     */
    @Secret
    @TableField(value = "doctor_order")
    private String doctorOrder;

    /**
     * 就诊日期
     */
    @Secret
    @TableField(value = "clinic_date")
    private String clinicDate;

    /**
     * 出院日期
     */
    @Secret
    @TableField(value = "leave_date")
    private String leaveDate;

    /**
     * 签名
     */
    @TableField(value = "sign")
    private String sign;
}