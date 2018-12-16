package com.rbs.project.service;

import com.rbs.project.mapper.CClassMapper;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CClassMapper cClassMapper;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * Description: 创建班级
     * @Author: WinstonDeng
     * @Date: 11:34 2018/12/12
     */
    public long createCClass(long courseId, CClass cClass) throws Exception {
        //新建记录的主键值 初始化为-1
        long createCClassId = -1;
        try {
            cClass.setCourseId(courseId);
            //注意！这里insert返回只是1/0 表示成功/失败 不是主键
            cClassMapper.insertCClass(cClass);
            //获得主键
            createCClassId=cClass.getId();
        }catch (Exception e){
            throw new Exception("新建班级错误");
        }
        return createCClassId;
    }
    /**
     * Description: 解析名单excel文件 并导入数据库
     * @Author: WinstonDeng
     * @Date: 14:44 2018/12/12
     */
    public boolean transStudentListFileToDataBase(long cclassId,String fileName) throws Exception {
        try {
            String realPath="D://fileloadtest//";
            Set<Student> students= ExcelUtils.transExcelToSet(realPath+fileName);
            for(Student student
                    :students){
                //如果不存在，则新增，避免重复
                if(studentMapper.findByAccount(student.getUsername())==null){
                    //cclassStudent.setCClassId(cclassId);//设置学生所属班级，暂时无
                    //默认密码
                    student.setPassword("123456");
                    //初始状态
                    student.setActive(false);
                    studentMapper.insertStudent(student);
                }
            }
        }catch (Exception e){
            throw new Exception("导入学生名单excel到数据库错误");
        }
        return true;
    }
}
