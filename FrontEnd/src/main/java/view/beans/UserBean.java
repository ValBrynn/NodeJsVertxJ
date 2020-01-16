package view.beans;


import view.model.UserModel;
import view.http.RestClient;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
@ManagedBean(name="user")

public class UserBean {

    private int id;
    private String userName;
    private String email;
    private String name;
    private String lastName;
    private String password;

    private RestClient client = new RestClient("user");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBean getUser(int id) {
        UserModel tm = client.getUser(id);
        UserBean res = new UserBean();
        if (tm == null) return res;
        res.setId(tm.getId());
        res.setUserName(tm.getUserName());
        res.setEmail(tm.getEmail());
        res.setName(tm.getName());
        res.setLastName(tm.getLastName());
        res.setPassword(tm.getPassword());

        return res;
    }

    public List<UserBean> getUsers() {
        List<UserModel> list = client.getUsers();
        List<UserBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        UserBean tb;
        for(UserModel m : list){
            tb = new UserBean();
            tb.setId(m.getId());
            tb.setUserName(m.getUserName());
            tb.setEmail(m.getEmail());
            tb.setName(m.getName());
            tb.setLastName(m.getLastName());
            tb.setPassword(m.getPassword());
            res.add(tb);
        }
        return res;
    }

    public String create() {

        if(userName == null || email==null || name==null || lastName==null || password==null ) return "create";
        if(userName.length() < 1 || email.length()<1 || name.length()<1 || lastName.length()<1 || password.length()<1) return "create";

        UserModel m = new UserModel();
        m.setUserName(this.userName);
        m.setEmail(this.email);
        m.setName(this.name);
        m.setLastName(this.lastName);
        m.setPassword(this.password);
        Response resp = client.addUser(m);
        if (resp.getStatus() == 201 || resp.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage("createdUser",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Creating user succeeded", "Creating user succeeded!"));
            return "success";
        }
        FacesContext.getCurrentInstance().addMessage("failedCreatingUser",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creating user failed", "Creating user failed, try again"));
        return "failed";
    }



}