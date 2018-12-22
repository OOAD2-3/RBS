package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Question;
import com.rbs.project.pojo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:07 2018/12/22
 * @Modified by:
 */
@Repository
public class QuestionDao {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private StudentMapper studentMapper;

    public final static int HAS_CCLASS_SEMINAR=0;
    public final static int HAS_TEAM=1;
    public final static int HAS_STUDENT=2;
    public final static int HAS_ATTENDANCE=3;
    private void hasSomethingFun(Question question,int ...hasSomething){
        for(int i:hasSomething){
            if(i==HAS_CCLASS_SEMINAR){
                question.setcClassSeminar(cClassSeminarMapper.findById(question.getcClassSeminarId()));
            }
            if(i==HAS_TEAM){
                question.setTeam(teamMapper.findById(question.getTeamId()));
            }
            if(i==HAS_STUDENT){
                question.setStudent(studentMapper.findById(question.getId()));
            }
            if(i==HAS_ATTENDANCE){
                question.setAttendance(attendanceMapper.findById(question.getAttendanceId()));
            }
        }
    }

    /**
     * Description: 通过id查找提问
     * @Author: WinstonDeng
     * @Date: 19:28 2018/12/22
     */
    public Question getById(long questionId,int ...hasSomething) throws MyException{
        Question question=questionMapper.findById(questionId);
        if(question==null){
            throw new MyException("查找提问错误！未找到提问",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(question,hasSomething);
        return question;
    }
    /**
     * Description: 通过cClassSeminarId查找提问
     * @Author: WinstonDeng
     * @Date: 15:28 2018/12/22
     */
    public List<Question> listAllQuestionsByCClassSemianr(CClassSeminar cClassSeminar,int ...hasSomething) throws MyException{
        if(cClassSeminarMapper.findById(cClassSeminar.getId())==null){
            throw new MyException("查看提问错误！班级讨论课不存在",MyException.NOT_FOUND_ERROR);
        }
        if(cClassMapper.findById(cClassSeminar.getcClassId())==null){
            throw new MyException("查看提问错误！班级讨不存在",MyException.NOT_FOUND_ERROR);
        }
        if(seminarMapper.findById(cClassSeminar.getSeminarId())==null){
            throw new MyException("查看提问错误！讨论课不存在",MyException.NOT_FOUND_ERROR);
        }
        List<Question> questions=questionMapper.findByCClassSeminarId(cClassSeminar.getId());
        if(questions==null){
            throw new MyException("查看提问错误！该记录不存在",MyException.NOT_FOUND_ERROR);
        }
        for(Question question
                :questions){
            hasSomethingFun(question,hasSomething);
        }
        return questions;
    }

    /**
     * Description: 通过id修改提问
     * @Author: WinstonDeng
     * @Date: 15:46 2018/12/22
     */
    public boolean updateQuestion(Question question) throws  MyException{
        if(questionMapper.findById(question.getId())==null){
            throw new MyException("修改提问错误！未找到记录",MyException.NOT_FOUND_ERROR);
        }
        boolean flag=false;
        try {
            Question temp=questionMapper.findById(question.getId());
            if(temp.getScore()!=question.getScore()){
                temp.setScore(question.getScore());
            }
            if(!temp.getSelected().equals(question.getSelected())){
                temp.setSelected(question.getSelected());
            }
            flag=questionMapper.updateQuestion(question);
        }catch (Exception e){
            throw new MyException("修改提问错误！数据库处理错误",MyException.ERROR);
        }
        return flag;
    }

    /**
     * Description: 通过cClassSeminarId,teamId,studentId查找提问
     * @Author: WinstonDeng
     * @Date: 16:54 2018/12/22
     */
    public Question findByPrimaryKeys(long cClassSeminarId, long teamId, long studentId,int ...hasSomething) throws MyException{
        if(cClassSeminarMapper.findById(cClassSeminarId)==null){
            throw new MyException("查找提问错误！未找到班级讨论课",MyException.NOT_FOUND_ERROR);
        }
        if(teamMapper.findById(teamId)==null){
            throw new MyException("查找提问错误！未找到队伍",MyException.NOT_FOUND_ERROR);
        }
        if(studentMapper.findById(studentId)==null){
            throw new MyException("查找提问错误！未找到学生",MyException.NOT_FOUND_ERROR);
        }
        Question question=questionMapper.findByPrimaryKeys(cClassSeminarId,teamId,studentId);
        if(question==null){
            throw new MyException("查找提问错误！未找到该记录",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(question,hasSomething);
        return question;
    }

    /**
     * Description: 新增提问
     * @Author: WinstonDeng
     * @Date: 20:44 2018/12/22
     */
    public boolean addQuestionByStudent(Question question)throws MyException{
        if(questionMapper.findById(question.getId())!=null){
            throw new MyException("新增提问错误！该记录已存在",MyException.ERROR);
        }
        if(cClassSeminarMapper.findById(question.getcClassSeminarId())==null){
            throw new MyException("新增提问错误！班级讨论课不存在",MyException.NOT_FOUND_ERROR);
        }
        if(attendanceMapper.findById(question.getAttendanceId())==null){
            throw new MyException("新增提问错误！被提问的展示不存在",MyException.NOT_FOUND_ERROR);
        }
        if(teamMapper.findById(question.getTeamId())==null){
            throw new MyException("新增提问错误！队伍不存在",MyException.NOT_FOUND_ERROR);
        }
        if(studentMapper.findById(question.getStudentId())==null){
            throw new MyException("新增提问错误！学生不存在",MyException.NOT_FOUND_ERROR);
        }
        boolean flag=false;
        try {
            flag=questionMapper.insertQuestion(question);
        }catch (Exception e){
            throw new MyException("新增提问错误！数据库处理错误",MyException.ERROR);
        }
        return flag;
    }
}
