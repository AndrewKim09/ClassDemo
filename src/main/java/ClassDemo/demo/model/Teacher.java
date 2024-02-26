package ClassDemo.demo.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "teachers")
@Data
@NoArgsConstructor
public class Teacher {
    @Id
    private ObjectId id;

    @NonNull
    private String fName;
    @NonNull
    private String lName;

    private Double salary;

    @DBRef
    private String department;

    public Teacher(String fName, String lName, String department, double salary) {
        this.fName = fName;
        this.lName = lName;
        this.department = department;
        this.salary = salary;
    }

    public Teacher(String fName, String lName, String department) {
        this.fName = fName;
        this.lName = lName;
        this.department = department;
    }


    @Override
    public String toString() {
        return this.id.toString();
    }

    public ObjectId getId() {
        return id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }
}
