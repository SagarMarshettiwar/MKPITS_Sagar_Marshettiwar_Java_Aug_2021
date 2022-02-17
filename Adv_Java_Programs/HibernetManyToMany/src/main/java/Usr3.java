import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Usr3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String Uname;

    @ManyToMany (mappedBy = "uob")
    private Collection<Vehi3> vob=new ArrayList<Vehi3>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public Collection<Vehi3> getVob() {
        return vob;
    }

    public void setVob(Collection<Vehi3> vob) {
        this.vob = vob;
    }
}