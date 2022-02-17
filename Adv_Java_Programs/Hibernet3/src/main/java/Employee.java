import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee {

    private Integer id;
    private String name;

   // @Embedded //Entity Class

    @EmbeddedId
    private Address aob;

    public Address getAob() {
        return aob;
    }

    public void setAob(Address aob) {
        this.aob = aob;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}