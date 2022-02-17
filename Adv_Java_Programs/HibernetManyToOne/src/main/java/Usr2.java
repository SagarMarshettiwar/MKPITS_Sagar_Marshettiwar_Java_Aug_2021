import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Usr2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String Uname;



    @OneToMany(mappedBy = "uob")
    private Collection<Vehi2> vob=new ArrayList<Vehi2>();

    public Collection<Vehi2> getVob() {
        return vob;
    }

    public void setVob(Collection<Vehi2> vob) {
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
