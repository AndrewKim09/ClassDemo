package ClassDemo.demo.repositories;

import ClassDemo.demo.model.Teacher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TeachersRepository extends MongoRepository<Teacher, ObjectId> {
    Optional<Teacher> findTeacherById(ObjectId id);

    @Query(value = "{'department':?0}" )
    List<Teacher> findTeacherByDepartment(String department);

    @Query(value = "('field': :?0)")
    List<Teacher> findTeacherByField(String field);
}
