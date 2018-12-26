package com.rbs.project.socket;

import com.rbs.project.pojo.entity.Question;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 14:55 2018/12/25
 */
public class QuestionPool {
    private Map<Long, List<Question>> map = new HashMap<>();

    private Random random = new Random();

    public void put(Long attendanceId, Question question) {
        List<Question> questionList = map.getOrDefault(attendanceId, new LinkedList<>());
        questionList.add(question);
    }

    public Question pick(Long attendanceId) {
        List<Question> questionList = map.get(attendanceId);
        if (questionList == null) {
            return null;
        }
        int randomIndex = random.nextInt(questionList.size());
        Question question = questionList.get(randomIndex);

        // 从map中移除
        questionList.remove(question);
        return question;
    }

    public int size(Long attendanceId) {
        return map.getOrDefault(attendanceId, new LinkedList<>()).size();
    }
}
