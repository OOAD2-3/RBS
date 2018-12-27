package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.ShareSeminarApplication;
import com.rbs.project.pojo.entity.ShareTeamApplication;
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
    private ShareTeamApplicationMapper shareTeamApplicationMapper;

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
            }
            if(i==HAS_MAIN_COURSE_TEACHER){
                Course mainCourse=courseMapper.findById(shareSeminarApplication.getMainCourseId());
                shareSeminarApplication.setMainCourseTeacher(teacherMapper.findById(mainCourse.getTeacherId()));
            }
            if(i==HAS_SUB_COURSE){
                Course subCourse=courseMapper.findById(shareSeminarApplication.getSubCourseId());
                shareSeminarApplication.setSubCourse(subCourse);
            }if(i==HAS_SUB_COURSE_TEACHER){
                shareSeminarApplication.setSubCourseTeacher(teacherMapper.findById(shareSeminarApplication.getSubCourseTeacherId()));
            }
        }
    }
    /**
    警告：此处重载了，但不是个好做法，根本原因在于两种请求字段完全重复，应该用多态来实现，但是没有这样做
     */
    private void hasSomethingFun(ShareTeamApplication shareTeamApplication,int ...hasSomething){
        for(int i:hasSomething){
            if(i==HAS_MAIN_COURSE){
                Course mainCourse=courseMapper.findById(shareTeamApplication.getMainCourseId());
                shareTeamApplication.setMainCourse(mainCourse);
            }
            if(i==HAS_MAIN_COURSE_TEACHER){
                Course mainCourse=courseMapper.findById(shareTeamApplication.getMainCourseId());
                shareTeamApplication.setMainCourseTeacher(teacherMapper.findById(mainCourse.getTeacherId()));
            }
            if(i==HAS_SUB_COURSE){
                Course subCourse=courseMapper.findById(shareTeamApplication.getSubCourseId());
                shareTeamApplication.setSubCourse(subCourse);
            }if(i==HAS_SUB_COURSE_TEACHER){
                shareTeamApplication.setSubCourseTeacher(teacherMapper.findById(shareTeamApplication.getSubCourseTeacherId()));
            }
        }
    }


    /**
     * Description: 通过id查找共享讨论课申请信息
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public ShareSeminarApplication getShareSeminarApplicationById(long id,int ...hasSomething) throws MyException{
        ShareSeminarApplication shareSeminarApplication=shareSeminarApplicationMapper.findById(id);
        if(shareSeminarApplication==null){
            throw new MyException("获取共享讨论课请求错误！该记录不存在",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(shareSeminarApplication,hasSomething);
        return shareSeminarApplication;
    }

    /**
     * Description: 通过主课程id查找共享讨论课信息列表
     * @Author: WinstonDeng
     * @Date: 16:42 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsByMainCourseId(long mainCourseId,int ...hasSomething)throws Exception{
        List<ShareSeminarApplication> shareSeminarApplications=shareSeminarApplicationMapper.findByMainCourseId(mainCourseId);
        for(ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            hasSomethingFun(shareSeminarApplication,hasSomething);
        }
        return shareSeminarApplications;
    }

    /**
     * Description: 通过从课程id查找共享讨论课信息列表
     * @Author: WinstonDeng
     * @Date: 16:55 2018/12/23
     */
    public List<ShareSeminarApplication> listAllShareSeminarApplicationsBySubCourseId(long subCourseId,int ...hasSomething) throws Exception{
        List<ShareSeminarApplication> shareSeminarApplications=shareSeminarApplicationMapper.findBySubCourseId(subCourseId);
        for(ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            hasSomethingFun(shareSeminarApplication,hasSomething);
        }
        return shareSeminarApplications;
    }

    /**
     * Description: 通过id查找共享组队申请信息
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/23
     */
    public ShareTeamApplication getShareTeamApplicationById(long id,int ...hasSomething) throws MyException{
        ShareTeamApplication shareTeamApplication=shareTeamApplicationMapper.findById(id);
        if(shareTeamApplication==null){
            throw new MyException("获取共享分组请求错误！该记录不存在",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(shareTeamApplication,hasSomething);
        return shareTeamApplication;
    }
    /**
     * Description: 通过主课程id查找共享分组信息列表
     * @Author: WinstonDeng
     * @Date: 16:42 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamApplicationsByMainCourseId(long mainCourseId, int ...hasSomething)throws Exception{
        List<ShareTeamApplication> shareTeamApplications=shareTeamApplicationMapper.findByMainCourseId(mainCourseId);
        for(ShareTeamApplication shareTeamApplication:shareTeamApplications){
            hasSomethingFun(shareTeamApplication,hasSomething);
        }
        return shareTeamApplications;
    }

    /**
     * Description: 通过从课程id查找共享分组信息列表
     * @Author: WinstonDeng
     * @Date: 16:55 2018/12/23
     */
    public List<ShareTeamApplication> listAllShareTeamApplicationsBySubCourseId(long subCourseId,int ...hasSomething) throws Exception{
        List<ShareTeamApplication> shareTeamApplications=shareTeamApplicationMapper.findBySubCourseId(subCourseId);
        for(ShareTeamApplication shareTeamApplication:shareTeamApplications){
            hasSomethingFun(shareTeamApplication,hasSomething);
        }
        return shareTeamApplications;
    }

    /**
     * Description: 通过id更新组队共享请求状态
     * @Author: WinstonDeng
     * @Date: 0:20 2018/12/25
     */
    public boolean updateShareTeamApplicationStatus(long requestId, Integer status) throws MyException {
        ShareTeamApplication shareTeamApplication=shareTeamApplicationMapper.findById(requestId);
        if(shareTeamApplication==null){
            throw new MyException("修改组队共享请求状态错误！未找到该条记录",MyException.NOT_FOUND_ERROR);
        }
        if(!shareTeamApplicationMapper.updateStatusById(requestId,status)){
            throw new MyException("修改组队共享请求状态错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }


    /**
     * Description: 发起队伍共享请求
     * @Author: WinstonDeng
     * @Date: 10:45 2018/12/27
     */
    public boolean addTeamShareApplication(long courseId, long subCourseId) throws MyException{
        //1. 检查是否为组队共享从课程
        if(courseMapper.findById(courseId).getTeamMainCourseId()!=0){
            throw new MyException("发起组队共享申请错误！从课程不能发起共享",MyException.ERROR);
        }
        //3. 若无，新建队伍共享请求
        Course subCourse=courseMapper.findById(subCourseId);
        if(subCourse==null){
            throw new MyException("发起队伍共享请求错误！未找到从课程",MyException.NOT_FOUND_ERROR);
        }
        ShareTeamApplication shareTeamApplication=new ShareTeamApplication();
        shareTeamApplication.setMainCourseId(courseId);
        shareTeamApplication.setSubCourseId(subCourse.getId());
        shareTeamApplication.setSubCourseTeacherId(subCourse.getTeacherId());
        if(!shareTeamApplicationMapper.addShareTeamApplication(shareTeamApplication)){
            throw new MyException("发起队伍共享请求错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 取消队伍共享
     * @Author: WinstonDeng
     * @Date: 11:16 2018/12/27
     */
    public boolean removeTeamShare(long requestId) throws MyException{
        //1. 删除从课程的共享小组  删除从课程的klass_team中的从课程班级

        //2. 删除共享记录
        ShareTeamApplication shareTeamApplication=shareTeamApplicationMapper.findById(requestId);
        Course course=courseMapper.findById(shareTeamApplication.getSubCourseId());
        course.setTeamMainCourseId(0);
        if(!courseMapper.updateTeamMainCourseId(course)){
            throw new MyException("取消队伍共享错误！从课程取消映射数据库处理错误",MyException.ERROR);
        }
        if(!shareTeamApplicationMapper.deleteTeamShareApplication(requestId)){
            throw new MyException("取消队伍共享错误！删除共享记录数据库处理错误",MyException.ERROR);
        }
        return true;
    }
}
