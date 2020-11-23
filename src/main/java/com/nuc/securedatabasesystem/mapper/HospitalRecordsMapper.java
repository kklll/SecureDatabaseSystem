package com.nuc.securedatabasesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nuc.securedatabasesystem.pojo.HospitalRecords;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName HospitalRecordsMapper
 * @Deacription 医院记录类的表的自动映射
 * @Author DeepBlue
 * @Date 2020/11/21 22:50
 * @Version 1.0
 **/

@Mapper
public interface HospitalRecordsMapper extends BaseMapper<HospitalRecords> {
}