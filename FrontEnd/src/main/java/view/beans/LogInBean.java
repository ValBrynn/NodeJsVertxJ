package view.beans;

import view.http.RestClient;
import view.model.UserModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
@ManagedBean(name = "logIn")
@SessionScoped
public class LogInBean {

    private RestClient client = new RestClient("user");

    private String userName;
    private String password;
    private boolean inLogged;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInLogged() {
        return inLogged;
    }

    public String login() {

        if (userName == null || password == null) return "logIn";
        if (userName.length() < 1 || password.length() < 1) return "logIn";

        UserModel m = new UserModel();
        m.setUserName(this.userName);
        m.setPassword(this.password);
        Response resp = client.getUserLogIn(userName, password);
        if (resp.getStatus() == 201 || resp.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage("LoggedUser",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Logging user succeeded", "Logging user succeeded!"));
            inLogged=true;
            return "/secured/userProfile";
        }
        FacesContext.getCurrentInstance().addMessage("failedLoggingUser",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logging user failed", "Logging user failed, try again"));
        inLogged=false;
        return "/secured/userProfile";
    }

    public String logOut(){

        userName=null;
        password=null;
        inLogged=false;
        return "logIn";
    }


}

