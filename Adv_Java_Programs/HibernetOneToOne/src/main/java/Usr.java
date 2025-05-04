import javax.persistence.*;

@Entity
public class Usr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_1", nullable = false)
    private Integer id1;
    private String uname;
    @OneToOne
    @JoinColumn(name ="vid")
    private Vehi vo;

    public Vehi getVo() {
        return vo;
    }

    public void setVo(Vehi vo) {
        this.vo = vo;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }
}
