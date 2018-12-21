package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:36 2018/12/20
 */
@Mapper
@Repository
public interface AttendanceMapper {

    /**
     * 获得一个班级下的讨论课的报名信息
     * @param cClassId
     * @param seminarId
     * @return
     */
     List<Attendance> findByCClassIdAndSeminarId(@Param("cClassId") long cClassId,@Param("seminarId") long seminarId);
}
