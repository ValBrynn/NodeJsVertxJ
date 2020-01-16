package data.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Chart")
public class ChartModel implements Serializable {

    @Column
    private Double x;

    @Column
    private Double y;

    @Column
    private Double z;

    @Column
    private String chart;
    public String getChart() {
        return chart;
    }
    public void setChart(String chart) {
        this.chart = chart;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "Owner")
    private UserModel owner;

    @Transient
    private String loggedInUser;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
