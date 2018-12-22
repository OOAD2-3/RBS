package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.relationship.CClassRound;
import com.rbs.project.pojo.relationship.CClassStudent;
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

    @Autowired
    private CClassStudentMapper cClassStudentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CClassRoundMapper cClassRoundMapper;

    @Autowired
    private RoundMapper roundMapper;

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
     * Description: 通过班级id查看班级
     *
     * @Author: WinstonDeng
     * @Date: 16:30 2018/12/19
     */
    public CClass getById(long cClassId) throws MyException {
        CClass cClass = null;
        if (cClassMapper.findById(cClassId) == null) {
            throw new MyException("通过id查找班级错误！未找到班级", MyException.NOT_FOUND_ERROR);
        }
        try {
            cClass = cClassMapper.findById(cClassId);
        } catch (Exception e) {
            throw new MyException("通过id查找班级错误！数据库处理错误", MyException.ERROR);
        }
        return cClass;
    }

    /**
     * Description: 通过课程id查班级
     *
     * @Author: WinstonDeng
     * @Date: 12:58 2018/12/16
     */
    public List<CClass> listByCourseId(long courseId, int... hasSomething) throws MyException {
        if (courseMapper.findById(courseId) == null) {
            throw new MyException("通过课程查找班级列表错误！该课程不存在", MyException.NOT_FOUND_ERROR);
        }
        List<CClass> cClasses = cClassMapper.findByCourseId(courseId);

        for (CClass cClass : cClasses) {

            for (int i : hasSomething) {
                if (i == HAS_TEAMS) {
                    List<Team> teams = teamMapper.findByCClassId(cClass.getId());
                    cClass.setTeams(teams);
                }
                if (i == HAS_CCLASS_SEMINARS) {
                    List<CClassSeminar> cClassSeminars = cClassSeminarMapper.findByCClassId(cClass.getCourseId());
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
     * Description: 新增班级
     *
     * @Author: WinstonDeng
     * @Date: 13:01 2018/12/16
     */
    public boolean addCClass(long courseId, CClass cClass) throws MyException {
        try {
            if (courseMapper.findById(courseId) == null) {
                throw new MyException("新建课程错误！未找到课程", MyException.NOT_FOUND_ERROR);
            }
            cClass.setCourseId(courseId);
            cClassMapper.insertCClass(cClass);
        } catch (Exception e) {
            throw new MyException("新建课程错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按id删除班级
     * ！！！！！！！！！！！！！缺失级联删除！！！！！！！！！！！！
     *
     * @Author: WinstonDeng
     * @Date: 11:30 2018/12/18
     */
    public boolean removeCClass(long cClassId) throws MyException {
        boolean flag = false;
        try {
            if (cClassMapper.findById(cClassId) == null) {
                throw new MyException("删除课程错误！未找到课程", MyException.NOT_FOUND_ERROR);
            }
            flag = cClassMapper.deleteCClassById(cClassId);
        } catch (Exception e) {
            throw new MyException("删除课程错误！数据库处理错误", MyException.ERROR);
        }
        return flag;
    }

    /**
     * Description: 新增班级学生
     *
     * @Author: WinstonDeng
     * @Date: 16:18 2018/12/19
     */
    public boolean addCClassStudent(CClassStudent cClassStudent) throws MyException {
        boolean flag = false;
        if (cClassMapper.findById(cClassStudent.getcClassId()) == null) {
            throw new MyException("新增班级学生错误！未找到班级", MyException.NOT_FOUND_ERROR);
        }
        if (courseMapper.findById(cClassStudent.getCourseId()) == null) {
            throw new MyException("新增班级学生错误！未找到课程", MyException.NOT_FOUND_ERROR);
        }
        if (studentMapper.findById(cClassStudent.getStudentId()) == null) {
            throw new MyException("新增班级学生错误！未找到学生", MyException.NOT_FOUND_ERROR);
        }
        try {
            flag = cClassStudentMapper.insertCClassStudent(cClassStudent);
        } catch (Exception e) {
            throw new MyException("新增班级学生错误！数据库处理错误", MyException.ERROR);
        }
        return flag;
    }

    /**
     * Description: 修改klass_student表的teamid字段
     *
     * @Author: 17Wang
     * @Time: 13:01 2018/12/19
     */
    public boolean updateTeamIdInKlassStudent(long teamId, long cClassId, long studentId) throws Exception {
        if (cClassStudentMapper.getByPrimaryKeys(cClassId, studentId) == null) {
            throw new MyException("修改klass_student的teamid字段错误！找不到该行", MyException.NOT_FOUND_ERROR);
        }
        if (!cClassStudentMapper.updateTeamIdByPrimaryKeys(teamId, cClassId, studentId)) {
            throw new MyException("修改klass_student的teamid字段错误！更新失败", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 检查TeamId
     *
     * @Author: 17Wang
     * @Time: 23:23 2018/12/19
     */
    public long getTeamIdByPrimaryKeys(long cClassId, long studentId) throws MyException {
        if (cClassStudentMapper.getByPrimaryKeys(cClassId, studentId) == null) {
            throw new MyException("修改klass_student的teamid字段错误！找不到该行", MyException.NOT_FOUND_ERROR);
        }
        Long l = cClassStudentMapper.getTeamIdByPrimaryKeys(cClassId, studentId);
        if (l == null) {
            return 0;
        }
        return cClassStudentMapper.getTeamIdByPrimaryKeys(cClassId, studentId);
    }

    /**
     * Description: 新增班级轮次 对应klass_round表
     * @Author: WinstonDeng
     * @Date: 15:34 2018/12/20
     */
    public boolean addCClassRound(CClassRound cClassRound) throws MyException{
        if(cClassMapper.findById(cClassRound.getcClassId())==null){
            throw new MyException("新增班级轮次错误！未找到班级",MyException.NOT_FOUND_ERROR);
        }
        if(roundMapper.findById(cClassRound.getRoundId())==null){
            throw new MyException("新增班级轮次错误！未找到轮次",MyException.NOT_FOUND_ERROR);
        }
        if(cClassRoundMapper.findByPrimaryKeys(cClassRound.getcClassId(),cClassRound.getRoundId())!=null){
            throw new MyException("新增班级轮次错误！该班级轮次已存在",MyException.ERROR);
        }
        try {
            cClassRoundMapper.insertCClassRound(cClassRound);
        }catch (Exception e){
            throw new MyException("新增班级轮次错误！数据库处理出错",MyException.ERROR);
        }
        return true;
    }
}
