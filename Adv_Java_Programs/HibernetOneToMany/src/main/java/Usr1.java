import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Usr1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String Uname;
    @OneToMany
    private Collection<Vehi1> vob=new ArrayList<Vehi1>();

    public Collection<Vehi1> getVob() {
        return vob;
    }

    public void setVob(Collection<Vehi1> vob) {
        this.vob = vob;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
