package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.ShareSeminarApplication;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:05 2018/12/23
 * @Modified by:
 */
@Repository
public interface ShareSeminarApplicationMapper {
    /**
     * 通过id查找共享讨论课申请
     * @param id
     * @return
     */
    ShareSeminarApplication findById(long id);

    /**
     * 通过主课程id查找共享讨论课申请列表
     * @param mainCourseId
     * @return
     */
    List<ShareSeminarApplication> findByMainCourseId(long mainCourseId) throws Exception;

    /**
     * 通过从课程id查找讨论课共享申请列表
     * @param subCourseId
     * @return
     */
    List<ShareSeminarApplication> findBySubCourseId(long subCourseId) throws Exception;
}
