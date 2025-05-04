import javax.persistence.*;

@Entity
@NamedQuery(name="stud",query="from Student where id>:Id")
@NamedNativeQuery(name = "Nstud",query="select * from Student where name='sagar'",resultClass = Student.class)
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}