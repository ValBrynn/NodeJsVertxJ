package view.beans;

import view.model.TestModel;
import view.http.RestClient;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TestBean {

    private int id;

    private String value;

    private RestClient client = new RestClient("app");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TestBean getTest(int id) {
        TestModel tm = client.getTest(id);
        TestBean res = new TestBean();
        if (tm == null) return res;
        res.setId(tm.getId());
        res.setValue(tm.getValue());
        return res;
    }

    public List<TestBean> getTests() {
        List<TestModel> list = client.getTests();
        List<TestBean> res = new ArrayList<>();
        if (list == null) return res;
        if (list.isEmpty()) return res;
        TestBean tb;
        for(TestModel m : list){
            tb = new TestBean();
            tb.setId(m.getId());
            tb.setValue(m.getValue());
            res.add(tb);
        }
        return res;
    }

    public String create() {
        if(value == null) return "create";
        if(value.length() < 1) return "create";
        TestModel m = new TestModel();
        m.setValue(this.value);
        Response resp = client.addTest(m);
        if (resp.getStatus() == 201 || resp.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage("createdTest",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Creating test succeded", "Creating test succeded!"));
            return "success";
        }
        FacesContext.getCurrentInstance().addMessage("failedCreatingTest",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creating test failed", "Creating test failed, try again"));
        return "failed";
    }

}