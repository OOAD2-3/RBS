package com.rbs.project.service;

import com.rbs.project.dao.CClassDao;
import com.rbs.project.dao.StudentDao;
import com.rbs.project.dao.UserDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassMapper;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.utils.ExcelUtils;
import com.rbs.project.utils.FileLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        //获得主键
        createCClassId=cClass.getId();
        cClass.setCourseId(courseId);
        cClassDao.addCClass(courseId,cClass);
        return createCClassId;
    }
    /**
     * Description: 解析名单excel文件 并导入数据库
     * @Author: WinstonDeng
     * @Date: 14:44 2018/12/12
     */
    public boolean transStudentListFileToDataBase(long cclassId,String fileName) throws MyException {
        String realPath="D://fileloadtest//";
        Set<Student> students= ExcelUtils.transExcelToSet(realPath+fileName);
        for(Student student
                :students){
                //cclassStudent.setCClassId(cclassId);//设置学生所属班级，暂时无
                //默认密码
                student.setPassword("123456");
                //初始状态
                student.setActive(false);
                studentDao.addStudent(student);
        }
        return true;
    }
    /**
     * Description: 通过课程查看班级列表
     * @Author: WinstonDeng
     * @Date: 10:42 2018/12/17
     */
    public List<CClass> listCClassesByCourseId(long courseId) throws MyException{
        return cClassDao.listByCourseId(courseId);
    }

    /**
     * Description: 上传学生名单
     * @Author: WinstonDeng
     * @Date: 10:37 2018/12/18
     */
    public String uploadStudentFile(long cClassId, MultipartFile file){
        String filePath="D://fileloadtest//";
        return FileLoadUtils.upload(file,filePath);
    }

    /**
     * Description: 按id删除课程
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/18
     */
    public boolean removeCClassById(long cClassId) throws MyException{
        return cClassDao.removeCClass(cClassId);
    }
}
