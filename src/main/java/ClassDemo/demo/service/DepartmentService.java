package ClassDemo.demo.service;

import ClassDemo.demo.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ClassDemo.demo.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addDepartment(String name, String field, String location){
        return departmentRepository.insert(new Department(name, field, location));
    }

    public void deleteDepartment(Department department){
        departmentRepository.delete(department);
    }

    public void deleteTeachers(String name){
        Optional<Department> optionalDepartment = departmentRepository.findByName(name);

        optionalDepartment.ifPresent(department -> {
            department.setTeachers(null);
        });
    }


    public Optional<Department> findDepartmentByName(String name){
        return departmentRepository.findByName(name);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

}
