package com.nuc.securedatabasesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

/**
 * @Author DeepBlue
 * @Date 2020/11/19 20:39
 */
@Data
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
    @TableField(value = "patient_name")
    private String patientName;

    /**
     * 患者性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 患者住址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 患者身份证号码
     */
    @TableField(value = "idcrad")
    private String idcrad;

    /**
     * 患者出生日期
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 联系电话
     */
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
    @TableField(value = "medical_history")
    private String medicalHistory;

    /**
     * 医嘱
     */
    @TableField(value = "doctor_order")
    private String doctorOrder;

    /**
     * 就诊日期
     */
    @TableField(value = "clinic_date")
    private Date clinicDate;

    /**
     * 出院日期
     */
    @TableField(value = "discharge_date")
    private Date dischargeDate;
}