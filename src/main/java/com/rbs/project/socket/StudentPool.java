package com.rbs.project.socket;

import com.rbs.project.pojo.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 14:55 2018/12/25
 */
@Component
public class StudentPool {
    private Map<Long, List<Student>> map = new ConcurrentHashMap<>();

    private Random random = new Random();

    public void put(Long attendanceId, Student student) throws Exception {
        List<Student> studentList = map.getOrDefault(attendanceId, new CopyOnWriteArrayList<>());
        for (Student s : studentList) {
            //如果存在该学生
            if (s.getId() == student.getId()) {
                throw new Exception();
            }
        }
        studentList.add(student);
        map.put(attendanceId, studentList);
    }

    public Student pick(Long attendanceId) {
        List<Student> studentList = map.get(attendanceId);
        if (studentList == null) {
            return null;
        }
        int randomIndex = random.nextInt(studentList.size());
        Student student = studentList.get(randomIndex);

        // 从map中移除
        studentList.remove(student);
        return student;
    }

    public int size(Long attendanceId) {
        return map.getOrDefault(attendanceId, new CopyOnWriteArrayList<>()).size();
    }

    /**
     * Description: 清空一个展示的所有提问
     *
     * @Author: WinstonDeng
     * @Date: 10:36 2018/12/29
     */
    public boolean clearAll(Long attendanceId) {
        List<Student> students = map.get(attendanceId);
        if (students == null) {
            return true;
        }
        map.remove(attendanceId);
        return true;
    }
}
