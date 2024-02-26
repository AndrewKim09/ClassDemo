package ClassDemo.demo.service;

import ClassDemo.demo.model.Department;
import ClassDemo.demo.model.Teacher;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ClassDemo.demo.repositories.DepartmentRepository;
import ClassDemo.demo.repositories.TeachersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Teacher> findAll() {
        return teachersRepository.findAll();
    }

    public Teacher addTeacher(String fName, String lName, String department){

        Teacher teacher = teachersRepository.insert(new Teacher(fName, lName, department));

        mongoTemplate.update(Department.class)
                .matching(Criteria.where("name").is(department))
                .apply(new Update().push("teachers").value(teacher.getId()))
                .first();

        return teacher;
    }

    public Optional<Teacher> findTeacherById(ObjectId id) {
        return teachersRepository.findTeacherById(id);
    }

    public List<Teacher> findTeacherByField(String field){
        return teachersRepository.findTeacherByField(field);
    }

    public List<Teacher> findTeacherByDepartment(String department){
        return teachersRepository.findTeacherByDepartment(department);

    }
}
