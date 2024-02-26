package ClassDemo.demo.controller;

import ClassDemo.demo.model.Department;
import ClassDemo.demo.model.Teacher;
import ClassDemo.demo.service.TeacherService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ClassDemo.demo.service.DepartmentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Map<String, String> info) {
        if(info.get("name").isEmpty() || info.get("field").isEmpty() || info.get("location").isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {
            return new ResponseEntity<Department>(departmentService.addDepartment(info.get("name"), info.get("field"), info.get("location")), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteDepartment(@PathVariable(value = "name") String name){
        Optional<Department> existingDepartment = departmentService.findDepartmentByName(name);

        if(existingDepartment.isEmpty())
            return new ResponseEntity<String>("department with name does not exist", HttpStatus.BAD_REQUEST);
        else {
            if(existingDepartment.get().getTeachers() != null || existingDepartment.get().getTeachers().isEmpty()){
                return new ResponseEntity<String>("Teachers should be reassigned or removed from the teachers list!", HttpStatus.BAD_REQUEST);
            }
            departmentService.deleteDepartment(existingDepartment.get());
            return new ResponseEntity<String>("department has been deleted", HttpStatus.OK);
        }
    }

    @PutMapping("/removeTeachers/{name}")
    public ResponseEntity<String> deleteTeachersFromDepartment(@PathVariable(value = "name") String name){
        Optional<Department> existingDepartment = departmentService.findDepartmentByName(name);

        if(existingDepartment.isEmpty())
            return new ResponseEntity<String>("department with name does not exist", HttpStatus.BAD_REQUEST);
        if(existingDepartment.get().getTeachers() == null){
            return new ResponseEntity<String>("There were no teachers to be deleted", HttpStatus.OK);
        }
        else{
            departmentService.deleteTeachers(name);
            return new ResponseEntity<String>("Teachers have been deleted", HttpStatus.OK);
        }
    }

    @PutMapping("/raise/{name}/{amount}")
    public ResponseEntity<String> updateSalary(@PathVariable(value="name") String name, @PathVariable(value = "amount") double amount){
        Optional<Department> existingDepartment = departmentService.findDepartmentByName(name);

        if(existingDepartment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            existingDepartment.get().getTeachers().forEach(teacher -> {
                teacher.setSalary(teacher.getSalary() + amount);
            });
             return new ResponseEntity<String>("teacher salaries have been updated", HttpStatus.OK);
        }
    }


    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(){
        return new ResponseEntity<List<Department>>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable(value="name")String name){
        Optional<Department> existingDepartment = departmentService.findDepartmentByName(name);

        if(existingDepartment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<Department>(existingDepartment.get(), HttpStatus.OK);
        }
    }
    @GetMapping("/test")
    public List<Department> printDepartments(){
        System.out.println(departmentService.getAllDepartments());
        return departmentService.getAllDepartments();
    }

    @GetMapping("/teachers/{name}")
    public Optional<List<Teacher>> getTeachers(@PathVariable(value = "name") String name){
        Optional<List<Teacher>> teachers = Optional.ofNullable(departmentService.findDepartmentByName(name).get().getTeachers());
        System.out.println(teachers);

        return teachers;
    }


}
