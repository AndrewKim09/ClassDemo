package ClassDemo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    public String name;
    @NonNull
    private String field;

    @NonNull
    private String location;

    @DBRef
    private List<Teacher> teachers;

    public Department(String name, String field, String location) {
        this.name = name;
        this.field = field;
        this.location = location;
    }
    @Override
    public String toString(){
        return(this.name);
    }

    public List<Teacher> getTeachers(){
        return teachers;
    }

    public String getField() {
        return field;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setTeachers(List<Teacher> list){
        this.teachers = list;
    }
}
