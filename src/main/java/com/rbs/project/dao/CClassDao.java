package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 12:50 2018/12/16
 * @Modified by:
 */
@Repository
public class CClassDao {

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;


    /**
     * 是否添加队伍信息
     */
    public static final int HAS_TEAMS = 0;
    /**
     * 是否添加讨论课信息
     */
    public static final int HAS_CCLASS_SEMINARS = 1;
    /**
     * 是否添加课程信息
     */
    public static final int HAS_COURSE = 2;
    /**
     * Description: 通过课程id查班级
     * @Author: WinstonDeng
     * @Date: 12:58 2018/12/16
     */
    public List<CClass> listByCourseId(long courseId, int... hasSomething) {
        List<CClass> cClasses = cClassMapper.findByCourseId(courseId);

        for (CClass cClass : cClasses) {

            for (int i : hasSomething) {
                if (i == HAS_TEAMS) {
                    List<Team> teams = teamMapper.findByCClassId(cClass.getId());
                    cClass.setTeams(teams);
                }
                if (i == HAS_CCLASS_SEMINARS) {
                    List<CClassSeminar> cClassSeminars=cClassSeminarMapper.findByCClassId(cClass.getCourseId());
                    cClass.setcClassSeminars(cClassSeminars);
                }
                if (i == HAS_COURSE) {
                    Course course = courseMapper.findById(cClass.getCourseId());
                    cClass.setCourse(course);
                }
            }
        }

        return cClasses;
    }
    /**
     * Description: 新增课程
     * @Author: WinstonDeng
     * @Date: 13:01 2018/12/16
     */
    public boolean addCClass(long courseId, CClass cClass) throws MyException {
        try {
            if(courseMapper.findById(courseId)==null){
                throw new MyException("新建课程错误！未找到课程",MyException.NOT_FOUND_ERROR);
            }
            cClass.setCourseId(courseId);
            cClassMapper.insertCClass(cClass);
        } catch (Exception e) {
            throw new MyException("新建课程错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }
}
