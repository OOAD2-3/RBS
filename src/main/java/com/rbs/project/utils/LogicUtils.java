package com.rbs.project.utils;

import com.rbs.project.dao.CourseDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;


/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:00 2018/12/19
 */
public class LogicUtils {

    private static CourseDao courseDao = new CourseDao();

    /**
     * Description: 判断当前team是否合法
     *
     * @Author: 17Wang
     * @Time: 19:33 2018/12/19
     */
    public static boolean teamIsValid(Team team) throws MyException {
        Course course = team.getCourse();
        //人数策略
        int peopleNum = team.getStudents().size() + 1;
        if (peopleNum < course.getCourseMemberLimitStrategy().getMinMember() || peopleNum > course.getCourseMemberLimitStrategy().getMaxMember()) {
            return false;
        }
        //其他策略
        return true;
    }

    /**
     * Description: 计算一次讨论课成绩的总分
     *
     * @Author: 17Wang
     * @Time: 20:38 2018/12/22
     */
    public static double calculateSeminarTotalScore(SeminarScore seminarScore, Course course) throws MyException {
        if (course == null) {
            throw new MyException("计算一次讨论课成绩的总分出错！传入课程不存在", MyException.ERROR);
        }
        double presentationPercentage = course.getPresentationPercentage() / 100.0;
        double questionPercentage = course.getQuestionPercentage() / 100.0;
        double reportPercentage = course.getReportPercentage() / 100.0;
        return seminarScore.getPresentationScore() * presentationPercentage +
                seminarScore.getQuestionScore() * questionPercentage +
                seminarScore.getReportScore() * reportPercentage;
    }

    /**
     * Description: 计算一次轮次课成绩的总分
     *
     * @Author: 17Wang
     * @Time: 21:11 2018/12/22
     */
    public static RoundScore calculateRoundScore(long roundId, long teamId) {
        return null;
    }




}
