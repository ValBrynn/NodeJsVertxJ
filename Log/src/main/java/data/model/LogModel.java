package data.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Log")
public class LogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String message;
    @Column (name="date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name="OwnerID")
    private  UserModel ownerID;

    @Transient
    private String user;

    public UserModel getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(UserModel ownerID) {
        this.ownerID = ownerID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
