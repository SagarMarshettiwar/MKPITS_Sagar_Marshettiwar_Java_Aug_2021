import javax.persistence.*;

@Entity
//@DiscriminatorColumn(name="vtype",discriminatorType = DiscriminatorType.STRING)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance( strategy = InheritanceType.JOINED)
public class Vehi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vid", nullable = false)
    private Integer vid;

    private String vname;

    public Integer getVid() {
        return vid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }


}
