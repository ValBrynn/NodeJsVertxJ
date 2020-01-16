package business.verticle;
import business.model.ChartModel;
import business.service.ChartService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import java.util.HashSet;
import java.util.Set;

public class ServerVerticle extends AbstractVerticle {
    public ServerVerticle() {
        super();
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {


        // Create a router object.
        Router router = Router.router(vertx);

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("Access-Control-Request-Headers");
        allowedHeaders.add("Access-Control-Request-Method");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("Referer");
        allowedHeaders.add("User-Agent");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.PUT);



        router.route().handler(CorsHandler.create(".*.")
                .allowedHeaders(allowedHeaders)
                .allowedMethods(allowedMethods));
        // Bind "/" to our hello message - so we are still compatible.
        router.get("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });



        router.route("/diagram/add*").handler(BodyHandler.create());
        router.post("/diagram/add/:userName").handler(this::addDiagram);

        router.get("/diagram/get/:username").handler(this::getAll);

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 9000), result -> {
                            if (result.succeeded()) {
                                startPromise.complete();
                            } else {
                                startPromise.fail(result.cause());
                            }
                        });


    }


    private void addDiagram(RoutingContext routingContext) {

        String username = routingContext.request().getParam("userName");
        JsonObject diagramJson = routingContext.getBodyAsJson();
        ChartModel chartM = parseJsonObject(diagramJson);



                vertx.executeBlocking(promise -> {
                    // Call some blocking API that takes a significant amount of time to return
                    //TOO HEAVY, USE EXECUTE BLOCKING AND PUT RESULT IN FUTURE
                    boolean success = ChartService.add(chartM, username);
                    promise.complete(success);
                }, resAdd -> {
                    System.out.println("database added");
                    int statuscode;
                    String statusMessage;
                    boolean resultAdd = (boolean) resAdd.result();

                    if(resultAdd){
                        statuscode = 201;
                        statusMessage = "Successfully added to database";
                    }else {
                        statuscode = 400;
                        statusMessage = "Error, not added to database";
                    }

                    routingContext.response()
                            .setStatusCode(statuscode).setStatusMessage(statusMessage).end();
                    System.out.println("The result is: " + resAdd.result());
                });






    }


    private void getAll(RoutingContext routingContext) {

        String username = routingContext.request().getParam("username");

                vertx.executeBlocking(promise -> {
                    // Call some blocking API that takes a significant amount of time to return
                    //TOO HEAVY, USE EXECUTE BLOCKING AND PUT RESULT IN FUTURE

                    ChartModel owner1 =  ChartService.getChart(username);
                    promise.complete(owner1);
                }, getRes -> {
                    int statuscode;
                    String statusMessage;
                    ChartModel getResult = (ChartModel) getRes.result();

                    if(getResult != null){


                        statuscode = 200;
                        statusMessage = "Diagram Successfully fetched from database";

                        routingContext.response()
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .setStatusCode(statuscode)
                                .setStatusMessage(statusMessage)
                                .end(Json.encodePrettily(getResult));



                    }else {
                        statuscode = 400;
                        statusMessage = "You have no diagram";
                        routingContext.response()
                                .setStatusCode(statuscode).setStatusMessage(statusMessage).end();
                    }


                });



    }




    private ChartModel parseJsonObject(JsonObject jsonObject){
        System.out.println("PARSING");
        ChartModel chartM = new ChartModel();
        String xs = jsonObject.getString("x");
        String ys = jsonObject.getString("y");
        String zs = jsonObject.getString("z");
        String chartType = jsonObject.getString("type");

        Double x= Double.parseDouble(xs);
        Double y = Double.parseDouble(ys);
        Double z = Double.parseDouble(zs);
        chartM.setX(x);
        chartM.setY(y);
        chartM.setZ(z);
        chartM.setChart(chartType);
        return chartM;
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {

    }
}
