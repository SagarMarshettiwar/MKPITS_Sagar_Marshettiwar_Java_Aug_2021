import javax.persistence.*;

@Entity
@DiscriminatorValue(value="TWOW")
public class TwoWheel extends Vehi{
    private String sthandle;

    public String getSthandle() {
        return sthandle;
    }

    public void setSthandle(String sthandle) {
        this.sthandle = sthandle;
    }
}
