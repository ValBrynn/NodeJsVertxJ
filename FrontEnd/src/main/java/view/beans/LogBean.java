package view.beans;

import view.http.RestClient;
import view.model.LogModel;
import view.model.UserModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="log")


public class LogBean {

    private RestClient client = new RestClient("log");

    private int id;
    private String message;
    private String date;
    private String postMessage;
    @ManagedProperty(value = "#{logIn.userName}")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public void clearPostMessage(){

        this.postMessage="";
    }

  public String writeLog(){


        Date currentDate=new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      LogModel log= new LogModel();
      log.setMessage(this.message);
      log.setDate(dateFormat.format(currentDate));
      log.setUserName(this.userName);

      Response resp = client.addLog(log);
      if (resp.getStatus() == 201 || resp.getStatus() == 200) {
          FacesContext.getCurrentInstance().addMessage("createdLog",
                  new FacesMessage(FacesMessage.SEVERITY_INFO, "Creating log succeeded", "Creating log succeeded!"));
          return "success";
      }
      FacesContext.getCurrentInstance().addMessage("failedLog",
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creating Log failed", "Creating Log  failed, try again"));
      return "failed";
  }

    public List<LogBean> getLogs() {
        List<LogModel> list = client.getLogs(userName);
        List<LogBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        LogBean tb;
        for(LogModel m : list){
            tb = new LogBean();
            tb.setMessage(m.getMessage());
            tb.setDate(m.getDate());
            res.add(tb);
        }
        return res;
    }

}
