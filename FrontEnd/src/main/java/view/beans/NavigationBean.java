package view.beans;

public class NavigationBean {

    public String toIndex () {
        return "index";
    }

    public String toCreate() {
        return "create";
    }

    public String toLogIn() {
        return "login";
    }

    public String toLog(){return "/secured/log";}

    public String toUserProfile(){return "/secured/userProfile";}

    public String toSearch(){return "/secured/search";}

    public String toMessage(){return "/secured/message";}
    public String toInbox(){return "/secured/inbox";}
    public String toSentMessages(){return "/secured/sentMessages";}
    public String toChart(){return "/secured/chart";}

}