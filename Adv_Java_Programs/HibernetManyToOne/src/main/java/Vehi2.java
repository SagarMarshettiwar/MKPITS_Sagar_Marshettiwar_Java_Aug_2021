import javax.persistence.*;

@Entity
public class Vehi2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String vname;
    @ManyToOne
    private Usr2 uob;

    public Usr2 getUob() {
        return uob;
    }

    public void setUob(Usr2 uob) {
        this.uob = uob;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
