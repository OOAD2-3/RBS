package com.rbs.project.mapper;

import com.rbs.project.exception.MyException;
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
     * 通过attendanceId查询一个报名状况
     * @param attendanceId
     * @return
     */
    Attendance findById(long attendanceId);

    /**
     * 获得一个班级下的讨论课的报名信息
     *
     * @param cClassId
     * @param seminarId
     * @return
     */
    List<Attendance> findByCClassIdAndSeminarId(@Param("cClassId") long cClassId, @Param("seminarId") long seminarId);

    /**
     * 报名一节讨论课
     *
     * @param attendance
     * @return
     */
    boolean insertAttendance(Attendance attendance);

    /**
     * 删除一个队伍的一个讨论课展示信息
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAttendanceById(long id) throws Exception;
}
