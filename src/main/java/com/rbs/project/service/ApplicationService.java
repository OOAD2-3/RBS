package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.ShareTeamApplication;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;
import com.rbs.project.pojo.relationship.CClassStudent;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:43 2018/12/23
 */
@Service
public class ApplicationService {
    @Autowired
    private TeamApplicationDao teamApplicationDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ShareDao shareDao;

    @Autowired
    private StudentDao studentDao;

    /**
     * Description: 查看team的请求
     * 1、需要加上主从课程！
     *
     * @Author: 17Wang
     * @Time: 14:15 2018/12/23
     */
    public TeamValidApplication getTeamValidRequestByTeamId(long teamId) throws MyException {
        Team team = teamDao.getTeamById(teamId);
        long teacherId = courseDao.getCourseById(team.getCourseId()).getTeacherId();
        return teamApplicationDao.getTeamValidRequestByTeamIdAndTeacherId(teamId, teacherId);
    }

    /**
     * Description: 查看一个老师的所有team的请求
     *
     * @Author: 17Wang
     * @Time: 15:53 2018/12/23
     */
    public List<TeamValidApplication> listTeamApplicationByTeacherId(long teacherId) {
        return teamApplicationDao.getTeamValidRequestByTeacherId(teacherId, TeamApplicationDao.HAS_TEAM);
    }

    /**
     * Description: 新增一条请求
     *
     * @Author: 17Wang
     * @Time: 14:04 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeamValidApplication(long teamId, String reason) throws Exception {

        TeamValidApplication teamValidApplication = new TeamValidApplication();
        Team team = teamDao.getTeamById(teamId);

        //设置信息
        teamValidApplication.setTeamId(teamId);
        teamValidApplication.setTeacherId(courseDao.getCourseById(team.getCourseId()).getTeacherId());
        teamValidApplication.setReason(reason);
        teamApplicationDao.addTeamValidApplication(teamValidApplication);

        //小组状态
        System.out.println(teamValidApplication.getId());
        if (team.getStatus() != Team.STATUS_IN_REVIEW) {
            teamDao.updateStatusByTeamId(Team.STATUS_IN_REVIEW,
                    teamApplicationDao.getTeamValidRequestById(teamValidApplication.getId()).getTeamId());
        }

        return true;
    }

    /**
     * Description: 修改请求的状态
     *
     * @Author: 17Wang
     * @Time: 14:50 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamValidApplicationStatus(long requestId, int status) throws Exception {
        //如果同意，小组状态改变
        if (status == TeamValidApplication.STATUS_AGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        //如果拒绝，小组状态改变
        if (status == TeamValidApplication.STATUS_DISAGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        return teamApplicationDao.updateTeamValidApplicationStatusById(requestId, status);
    }

    /**
     * Description: 修改组队共享请求的状态
     * @Author: WinstonDeng
     * @Date: 20:59 2018/12/24
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamShareApplicationStatus(long requestId, Integer status) throws Exception{
        //如果同意
        if(status== ShareTeamApplication.STATUS_ACCEPT){
            //  若请求通过，发出申请的课程为主课程，接受申请的课程为从课程，主课程小组名单映射到从课程中
            //  例如，某小组主课程 A 有5 人，五人中选修从课程 B 的为其中的 3 人，则 B 中此小组为此 3 人组成的小组
            //  若接受共享分组请求，该课程原有分组将被删除，并且，失去发起共享分组、接受其他共享分组请求以及课程中组队的功能
            //1、从课程删掉所有的teamId（team表），调用teamndao的级联删除函数delete
            //
            //  1.删除从课程原有分组
            ShareTeamApplication shareTeamApplication=shareDao.getShareTeamApplicationById(requestId);
            List<Team> subCourseTeams=teamDao.listByCourseId(shareTeamApplication.getSubCourseId());
            for(Team team:subCourseTeams){
                //删除分组,调用dao层级联删除函数
                teamDao.deleteTeamById(team.getId());
            }

            //  2.从课程小组调整
            //  要确认从课程队伍属于哪个班，要先查klass_student表里courseid和teamid对应学生的klass_id，再通过分班策略
            //  进行区分，建议做成类方法
            List<Team> mainCourseTeams=teamDao.listByCourseId(shareTeamApplication.getMainCourseId());
            for(Team team
                    :mainCourseTeams){
                //主课程学生
                List<Student> mainCourseStudents=studentDao.listByTeamId(team.getId());
                //从课程学生
                List<CClassStudent> subCourseStudents=new ArrayList<>();
                for(Student student
                        :mainCourseStudents){
                    System.out.println(student.getId());
                    //如果这个学生在从课程
                    CClassStudent temp=studentDao.getByIdAndCourseId(student.getId(),shareTeamApplication.getSubCourseId());
                    if(temp!=null){
                        subCourseStudents.add(temp);
                    }
                }
                //如果没有从课程学生
                if(subCourseStudents.isEmpty()){
                    continue;
                }
                //通过主课程学生确定从课程学生共享后队伍所在班级
                long cClassIdInSubCourseTeam= getCClassIdByStrategy(subCourseStudents);
                //建立klass_team表的新关系
                teamDao.addCClassTeam(team.getId(),cClassIdInSubCourseTeam);
            }
            //  3.建立主从课程映射
            //  从课程使用队伍学生时，要先确认自己是从课程
            //  从课程学生只看klass_student表的course_id klass_id，不看主课程里这两个字段
            //  更新从课程的team_main_course_id字段,建立映射
            courseDao.updateTeamMainCourseId(shareTeamApplication.getSubCourseId(),shareTeamApplication.getMainCourseId());
        }
        //如果拒绝，只更新请求表的字段
        return shareDao.updateShareTeamApplicationStatus(requestId,status);
    }

    /**
     * Description: 从课程分班策略
     * @Author: WinstonDeng
     * @Date: 15:39 2018/12/25
     */
    private long getCClassIdByStrategy(List<CClassStudent> subCourseStudents) {
        //小组人数
        final int memberNum=subCourseStudents.size();
        //计数器
        Map<Long,Integer> countMap=new HashMap<>();

        //初始化  把第一个学生的的班级存进去
        countMap.put(subCourseStudents.get(0).getcClassId(),1);
        //统计
        for(int i=1;i<memberNum;i++){
            Long key=subCourseStudents.get(i).getcClassId();
            //如果存在 对应计数器+1 ，否则新建
            if(countMap.containsKey(key)){
                int cnt=countMap.get(key);
                countMap.put(key,cnt+1);
            }else {
                countMap.put(key,1);
            }
        }
        //从课程分班策略
        //获得人数最多的班级
        long cClassIdWithMaxMember=subCourseStudents.get(0).getcClassId();
        int maxMember=countMap.get(cClassIdWithMaxMember);
        for(Long key:countMap.keySet()){
            if(countMap.get(key)>maxMember){
                cClassIdWithMaxMember=key;
                maxMember=countMap.get(cClassIdWithMaxMember);
            }
        }
        //若从课程有多个班级，从课程小组所在班级由系统按少数服从多数原则
        if(cClassIdWithMaxMember>memberNum/2){
            return cClassIdWithMaxMember;
        }else {
            //若出现该小组在不同班级的人数相同的情况，则优先分配到总小组数少的班级
            List<Long> cClassIdQueue=new ArrayList<>();
            for(Map.Entry<Long,Integer> m:countMap.entrySet()){
                if(m.getValue().equals(maxMember)){
                    cClassIdQueue.add(m.getKey());
                }
            }
            int teamNum=teamDao.listByCClassId(cClassIdQueue.get(0)).size();
            long resultCClassId=cClassIdQueue.get(0);
            for(int i=1;i<cClassIdQueue.size();i++){
                if(teamDao.listByCClassId(cClassIdQueue.get(i)).size()<teamNum){
                    resultCClassId=cClassIdQueue.get(i);
                }
            }
            return resultCClassId;
        }


    }

    /**
     * Description: 发起组队共享申请
     * @Author: WinstonDeng
     * @Date: 23:18 2018/12/26
     */
    public boolean addTeamShareRequest(long courseId, long subCourseId)throws MyException {
        return shareDao.addTeamShareApplication(courseId,subCourseId);
    }

    /**
     * Description: 取消队伍共享
     * @Author: WinstonDeng
     * @Date: 23:24 2018/12/26
     */
    public boolean removeTeamShare(long requestId) throws MyException{
        return shareDao.removeTeamShare(requestId);
    }
}
