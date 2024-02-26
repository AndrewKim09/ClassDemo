package ClassDemo.demo.controller;

import ClassDemo.demo.model.Department;
import ClassDemo.demo.model.Teacher;
import ClassDemo.demo.service.DepartmentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ClassDemo.demo.service.TeacherService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public List<Teacher> allTeachers() {
        return teacherService.findAll();
    }
    @GetMapping("/id/{id}")

    public ResponseEntity<Optional<Teacher>> getTeacherById(@PathVariable(value = "id") ObjectId id){
        return new ResponseEntity<Optional<Teacher>>(teacherService.findTeacherById(id), HttpStatus.OK);
    }

    @GetMapping("/field/{field}")
    public ResponseEntity<List<Teacher>> getTeachersByField(@PathVariable(value = "field")String department){
        Optional<Department> existingDepartment = departmentService.findDepartmentByName(department);

        if(existingDepartment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            List<Teacher> existingTeachers = teacherService.findTeacherByField(department);

            if(existingTeachers.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<List<Teacher>>(existingTeachers, HttpStatus.OK);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Teacher>> getTeachersByDepartment(@PathVariable(value = "department") String department){
        return new ResponseEntity<List<Teacher>>(teacherService.findTeacherByDepartment(department), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Map<String, String> teacherInfo){

        Optional<Department> existingDepartment = departmentService.findDepartmentByName(teacherInfo.get("department"));

        if (teacherInfo.get("fName") == null || teacherInfo.get("fName").isEmpty() ||
                teacherInfo.get("lName") == null || teacherInfo.get("lName").isEmpty() ||
                teacherInfo.get("department") == null || teacherInfo.get("department").isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(existingDepartment.isPresent())
            return new ResponseEntity<Teacher>(teacherService.addTeacher(teacherInfo.get("fName"), teacherInfo.get("lName"), teacherInfo.get("department")), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
