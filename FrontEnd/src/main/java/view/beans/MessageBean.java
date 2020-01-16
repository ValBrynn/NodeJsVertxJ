package view.beans;

import view.http.RestClient;
import view.model.LogModel;
import view.model.MessageModel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
@ManagedBean(name="message")

public class MessageBean {

    private RestClient client = new RestClient("message");


    private int id;
    private  String receiver;
    private  String message;
    private String date;

    @ManagedProperty(value = "#{logIn.userName}")
    private  String sender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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


    public String sendMessage(){

        if(message == null || receiver == null) return "createdMessage";
        if(message.length()<1 || receiver.length()<1) return "createdMessage";

        Date currentDate=new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        MessageModel m= new MessageModel();
        m.setMessage(this.message);
        m.setDate(dateFormat.format(currentDate));
        m.setReceiver(this.receiver);
        m.setSender(this.sender);
        Response resp = client.addMessage(m);
        if (resp.getStatus() == 201 || resp.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage("createdMessage",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Creating message succeeded", "Creating message succeeded!"));
            return "success";
        }
        FacesContext.getCurrentInstance().addMessage("failedMessage",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creating Message failed", "Creating Message  failed, try again"));
        return "failed";
    }


    public List<MessageBean> getReceivedMessages() {
        List<MessageModel> list = client.getReceivedMessages(sender);
        List<MessageBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        MessageBean mb;
        for(MessageModel m : list){
            mb = new MessageBean();
            mb.setId(m.getId());
            mb.setMessage(m.getMessage());
            mb.setDate(m.getDate());
            mb.setReceiver(m.getReceiver());
            mb.setSender(m.getSender());
            res.add(mb);
        }
        return res;
    }

    public List<MessageBean> getSentMessages() {
        List<MessageModel> list = client.getSentMessages(sender);
        List<MessageBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        MessageBean mb;
        for(MessageModel m : list){
            mb = new MessageBean();
            mb.setMessage(m.getMessage());
            mb.setDate(m.getDate());
            mb.setSender(m.getSender());
            res.add(mb);
        }
        return res;
    }

}




