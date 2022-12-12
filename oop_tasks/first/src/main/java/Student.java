import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    private String name;
    private int age;
    private String nameOfUni;

    public Student(String name, int age, String nameOfUni) {
        this.name = name;
        this.age = age;
        this.nameOfUni = nameOfUni;
    }

    public Student() {}

    @Override
    public String toString() {
        return "Student " +
                "is " + name +
                ", the age is " + age +
                " and the name of university is " + nameOfUni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name) &&
                Objects.equals(nameOfUni, student.nameOfUni);
    }


}
