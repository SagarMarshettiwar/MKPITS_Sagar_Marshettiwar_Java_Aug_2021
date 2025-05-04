import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Vehi3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String vname;



    @ManyToMany
    private Collection <Usr3> uob = new ArrayList<Usr3>();

    public Collection<Usr3> getUob() {
        return uob;
    }

    public void setUob(Collection<Usr3> uob) {
        this.uob = uob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }


}


