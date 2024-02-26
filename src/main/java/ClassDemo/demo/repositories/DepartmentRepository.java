package ClassDemo.demo.repositories;

import ClassDemo.demo.model.Department;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, ObjectId> {

    @Query(value = "{'name':?0}")
    public Optional<Department> findByName(String name);

}
