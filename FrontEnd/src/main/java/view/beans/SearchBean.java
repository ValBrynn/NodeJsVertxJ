package view.beans;

import view.http.RestClient;
import view.model.LogModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "search")
@SessionScoped
public class SearchBean {

    private RestClient client = new RestClient("user");
    private RestClient client2 = new RestClient("log");

    private String findUser;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getFindUser() {
        return findUser;
    }

    public void setFindUser(String findUser) {
        this.findUser = findUser;
    }

    public String findUser(){

        if(findUser==null) return "search";

        Response resp = client.findUser(findUser);

        if (resp.getStatus() == 201 || resp.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage("foundUser",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "finding user succeeded", "finding user succeeded!"));
            return "findUserSuccess";
        }
        FacesContext.getCurrentInstance().addMessage("failedfindingUser",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "finding user failed", "finding user failed, try again"));

        return "findUserFailed";


    }

    public List<LogBean> getLogList(){

        List<LogModel> list = client2.getLogList(userName,findUser);

        List<LogBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        LogBean logbean;
        for(LogModel m : list){
            logbean = new LogBean();
            logbean.setId(m.getId());
            logbean.setMessage(m.getMessage());
            logbean.setDate(m.getDate());
            logbean.setUserName(m.getUserName());
            res.add(logbean);
        }
        return res;


    }

}
