import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable

//VALUE OBJECT
public class Address implements Serializable {
    private static final long serialVersionUID = 3598949303430179531L;
    private String city;
    private String state;
    private int pincode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
