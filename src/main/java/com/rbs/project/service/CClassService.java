package com.rbs.project.service;

import com.rbs.project.dao.CClassDao;
import com.rbs.project.dao.StudentDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:41 2018/12/16
 * @Modified by:
 */
@Service
public class CClassService {

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private StudentDao studentDao;

    /**
     * Description: 创建班级
     * @Author: WinstonDeng
     * @Date: 11:34 2018/12/12
     */
    public long createCClass(long courseId, CClass cClass) throws MyException {
        //新建记录的主键值 初始化为-1
        long createCClassId = -1;
        //判空
        if((Long)courseId==null){
            throw new MyException("courseId不能为空",MyException.ERROR);
        }
        if(cClass.getGrade()==null){
            throw new MyException("grade不能为空",MyException.ERROR);
        }
        if(cClass.getSerial()==null){
            throw new MyException("serial不能为空",MyException.ERROR);
        }
        if(cClass.getTime()==null){
            throw new MyException("time不能为空",MyException.ERROR);
        }
        if(cClass.getPlace()==null){
            throw new MyException("location不能为空",MyException.ERROR);
        }

        cClass.setCourseId(courseId);
        cClassDao.addCClass(courseId,cClass);
        //获得主键
        createCClassId=cClass.getId();
        return createCClassId;
    }
    /**
     * Description: 解析名单excel文件 并导入数据库
     * @Author: WinstonDeng
     * @Date: 14:44 2018/12/12
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean transStudentListFileToDataBase(long cclassId,String fileName) throws MyException {
        //读取路径
        String filePath="D:/projectTemp/studentfile/";
        Set<Student> students= ExcelUtils.transExcelToSet(filePath+fileName);
        for(Student student
                :students){
            //通过学号判断是否存在
            try {
                studentDao.getStudentByAccount(student.getUsername());
            }catch (MyException e){
                //默认密码
                student.setPassword("123456");
                //初始状态
                student.setActive(false);
                //增加到student
                long studentId=studentDao.addStudent(student);
                student.setId(studentId);
                //增加到klass_student
                CClassStudent cClassStudent =new CClassStudent();
                cClassStudent.setcClassId(cclassId);
                cClassStudent.setCourseId(cClassDao.getById(cclassId).getCourseId());
                cClassStudent.setStudentId(student.getId());
                cClassDao.addCClassStudent(cClassStudent);
            }
        }
        return true;
    }

    /**
     * Description: 通过课程查看班级列表
     * @Author: WinstonDeng
     * @Date: 10:42 2018/12/17
     */
    public List<CClass> listCClassesByCourseId(long courseId) throws MyException{
        if((Long)courseId==null){
            throw new MyException("courseId不能为空",MyException.ERROR);
        }
        return cClassDao.listByCourseId(courseId);
    }


    /**
     * Description: 按id删除班级
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/18
     */
    public boolean removeCClassById(long cClassId) throws MyException{
        if((Long)cClassId==null){
            throw new MyException("cClassId不能为空",MyException.ERROR);
        }
        return cClassDao.removeCClass(cClassId);
    }


}
