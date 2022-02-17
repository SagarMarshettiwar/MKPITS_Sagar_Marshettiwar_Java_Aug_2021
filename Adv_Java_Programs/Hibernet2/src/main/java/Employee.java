import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    //@Transient //it used to hide the column in database table
    @Lob // it is basically used to extend the size of a Specific column
    private String address;
//    private static String address; //if we don't want to show any specific column in table use [Static key]
    @Temporal(TemporalType.DATE) //it is used for Date Modification for showing  a specific Format of date
    private Date joining;

    public Date getJoining() {
        return joining;
    }

    public void setJoining(Date joining) {
        this.joining = joining;
    }

    public Integer getId() {
        return id;
    }

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

    public void setId(Integer id) {
        this.id = id;
    }
}
