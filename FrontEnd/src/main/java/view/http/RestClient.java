package view.http;

import io.vertx.core.Vertx;
import org.glassfish.jersey.client.ClientConfig;
import view.model.LogModel;
import view.model.MessageModel;
import view.model.TestModel;
import view.model.UserModel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class RestClient {

    private Client client = ClientBuilder.newClient(new ClientConfig());


    private  String REST_URI_TEST;
    private  String REST_URI_USER;
    private  String REST_URI_LOG;
    private  String REST_URI_MESSAGE;



    public RestClient(String domain) {
        client.register(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        REST_URI_TEST = "http://"+ domain + ":8080/serverLAB1-2/rest/test";
        REST_URI_USER = "http://"+ domain + ":8080/User/rest/user";
        REST_URI_LOG = "http://"+ domain + ":8080/Log/rest/log";
        REST_URI_MESSAGE = "http://"+ domain + ":8080/Message/rest/message";

    }

    //MARK: Http Rest for Client
    public TestModel getTest(int id) {
        return client.target(REST_URI_TEST).path(""+id).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(TestModel.class);
    }

    public UserModel getUser(int id) {
        return client.target(REST_URI_USER).path(""+id).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(UserModel.class);
    }

    public UserModel getUserbyUserName(String userName) {
        return client.target(REST_URI_USER).path(""+userName).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(UserModel.class);
    }
    public Response getUserLogIn(String userName, String password) {
        return client.target(REST_URI_USER).path(""+userName+"/"+password).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
    }
    public List<TestModel> getTests() {
        Response response = client.target(REST_URI_TEST).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getTests resp.status: " + response.getStatus());
        System.out.println("getTests resp.entity: " + response.getEntity());
        System.out.println("getTests resp.length: " + response.getLength());
        System.out.println("getTests resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        return response.readEntity(new GenericType<List<TestModel>>() {});
    }

    public List<UserModel> getUsers() {
        Response response = client.target(REST_URI_USER).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getUsers resp.status: " + response.getStatus());
        System.out.println("getUsers resp.entity: " + response.getEntity());
        System.out.println("getUsers resp.length: " + response.getLength());
        System.out.println("getUsers resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        return response.readEntity(new GenericType<List<UserModel>>() {});
    }

    public List<LogModel> getLogs(String userName) {
        Response response = client.target(REST_URI_LOG).path(""+userName).request(MediaType.APPLICATION_JSON).accept(userName,MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getLogs resp.status: " + response.getStatus());
        System.out.println("getLogs resp.entity: " + response.getEntity());
        System.out.println("getLogs resp.length: " + response.getLength());
        System.out.println("getLogs resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        return response.readEntity(new GenericType<List<LogModel>>() {});
    }

    public Response addTest(TestModel test) {
        return client.target(REST_URI_TEST).request(MediaType.APPLICATION_JSON).post(Entity.entity(test, MediaType.APPLICATION_JSON));
    }

    public Response addUser(UserModel user) {

        return client.target(REST_URI_USER).request(MediaType.APPLICATION_JSON).post(Entity.entity(user,MediaType.APPLICATION_JSON));
    }

    public Response addLog(LogModel log) {

        return client.target(REST_URI_LOG).request(MediaType.APPLICATION_JSON).post(Entity.entity(log,MediaType.APPLICATION_JSON));
    }

    public Response addMessage(MessageModel msg) {

        return client.target(REST_URI_MESSAGE).request(MediaType.APPLICATION_JSON).post(Entity.entity(msg,MediaType.APPLICATION_JSON));
    }
    public Response findUser(String findUser) {

        return client.target(REST_URI_USER).path(""+findUser).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
    }

    public List<LogModel> getLogList(String userName, String findUser){

        Response response = client.target(REST_URI_LOG).path(""+userName+"/"+findUser).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getLogList resp.status: " + response.getStatus());
        System.out.println("getLogList resp.entity: " + response.getEntity());
        System.out.println("getLogList resp.length: " + response.getLength());
        System.out.println("getLogList resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        if(response.getStatus()==401) return null;
        return response.readEntity(new GenericType<List<LogModel>>() {});
    }

    public List<MessageModel> getReceivedMessages(String userName){

        Response response = client.target(REST_URI_MESSAGE).path(""+userName).request(MediaType.APPLICATION_JSON).accept(userName,MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getReceivedMessages resp.status: " + response.getStatus());
        System.out.println("getReceivedMessages  resp.entity: " + response.getEntity());
        System.out.println("getReceivedMessages  resp.length: " + response.getLength());
        System.out.println("getReceivedMessages  resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        if(response.getStatus()==401) return null;
        return response.readEntity(new GenericType<List<MessageModel>>() {});
    }

    public List<MessageModel> getSentMessages(String userName){

        Response response = client.target(REST_URI_MESSAGE).path("sentMsg"+"/"+userName).request(MediaType.APPLICATION_JSON).accept(userName,MediaType.APPLICATION_JSON).get(Response.class);
        System.out.println("getSentMessages resp.status: " + response.getStatus());
        System.out.println("getSentMessages  resp.entity: " + response.getEntity());
        System.out.println("getSentMessages  resp.length: " + response.getLength());
        System.out.println("getSentMessages  resp.hasEntity: " + response.hasEntity());
        if (response.getLength() < 0) return null;
        if(response.getStatus()==401) return null;
        return response.readEntity(new GenericType<List<MessageModel>>() {});
    }



}