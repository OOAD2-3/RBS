package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.ShareTeamApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 21:53 2018/12/24
 * @Modified by:
 */
@Repository
public interface ShareTeamApplicationMapper {
    /**
     * 通过id查找组队共享申请
     * @param id
     * @return
     */
    ShareTeamApplication findById(long id);

    /**
     * 通过主课程id查找组队共享申请列表
     * @param mainCourseId
     * @return
     */
    List<ShareTeamApplication> findByMainCourseId(long mainCourseId);

    /**
     * 通过从课程id查找组队共享申请列表
     * @param subCourseId
     * @return
     */
    List<ShareTeamApplication> findBySubCourseId(long subCourseId);


    /**
     * 通过id 和状态码 status 修改组队共享请求状态
     * @param id
     * @param status
     * @return
     */
    boolean updateStatusById(@Param("id") long id,@Param("status") Integer status);
}
