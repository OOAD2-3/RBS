package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.ShareSeminarApplicationMapper;
import com.rbs.project.mapper.TeacherMapper;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.ShareSeminarApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:21 2018/12/23
 * @Modified by:
 */

@Repository
public class ShareDao {

    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    public final static int HAS_MAIN_COURSE=0;
    public final static int HAS_MAIN_COURSE_TEACHER=1;
    public final static int HAS_SUB_COURSE=2;
    public final static int HAS_SUB_COURSE_TEACHER=3;

    private void hasSomethingFun(ShareSeminarApplication shareSeminarApplication,int ...hasSomething){
        for(int i:hasSomething){
            if(i==HAS_MAIN_COURSE){
                Course mainCourse=courseMapper.findById(shareSeminarApplication.getMainCourseId());
                shareSeminarApplication.setMainCourse(mainCourse);
                if(i==HAS_MAIN_COURSE_TEACHER){
                    shareSeminarApplication.setMainCourseTeacher(teacherMapper.findById(mainCourse.getTeacherId()));
                }
            }
            if(i==HAS_SUB_COURSE){
                Course subCourse=courseMapper.findById(shareSeminarApplication.getSubCourseId());
                shareSeminarApplication.setSubCourse(subCourse);
                if(i==HAS_SUB_COURSE_TEACHER){
                    shareSeminarApplication.setSubCourseTeacher(teacherMapper.findById(subCourse.getTeacherId()));
                }
            }

        }
    }

    /**
     * Description: 通过id查找共享讨论课信息
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public ShareSeminarApplication getShareSeminarApplicationById(long id) throws MyException{
        ShareSeminarApplication shareSeminarApplication=shareSeminarApplicationMapper.findById(id);
        if(shareSeminarApplication==null){
            throw new MyException("获取共享讨论课请求错误！该记录不存在",MyException.NOT_FOUND_ERROR);
        }
        return shareSeminarApplication;
    }

    /**
     * Description: 通过主课程id查找共享讨论课信息列表
     * @Author: WinstonDeng
     * @Date: 16:42 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsByMainCourseId(long mainCourseId)throws Exception{
        List<ShareSeminarApplication> shareSeminarApplications=shareSeminarApplicationMapper.findByMainCourseId(mainCourseId);
        return shareSeminarApplications;
    }

    /**
     * Description: 通过从课程id查找共享讨论课信息列表
     * @Author: WinstonDeng
     * @Date: 16:55 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsBySubCourseId(long subCourseId) throws Exception{
        List<ShareSeminarApplication> shareSeminarApplications=shareSeminarApplicationMapper.findBySubCourseId(subCourseId);
        return shareSeminarApplications;
    }
}
