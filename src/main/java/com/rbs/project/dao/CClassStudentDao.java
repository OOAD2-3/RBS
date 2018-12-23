package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 10:51 2018/12/23
 */
@Repository
public class CClassStudentDao {
    @Autowired
    private CClassStudentMapper cClassStudentMapper;

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
     * Description: 获取一个team下面的所有成员
     * @Author: 17Wang
     * @Time: 10:49 2018/12/23
     */
    public List<Long> getStudentIdByTeamId(long teamId){
        return cClassStudentMapper.getStudentIdByTeamId(teamId);
    }
}
