package utilities;


import business.controller.UserController;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new HashSet<>();

        System.out.println("REST configuration starting: getClasses()");
        resources.add(MOXyJsonProvider.class);
        //features
        //this will register MOXy JSON providers
        //resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        //we could also use this
        //resources.add(org.glassfish.jersey.moxy.xml.MoxyXmlFeature.class);
        //instead let's do it manually:
        resources.add(UserController.class);

        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        resources.add(utilities.JsonMoxyConfigurationContextResolver.class);
        //==> we could also choose packages, see below getProperties()

        System.out.println("REST configuration ended: successfully.");

        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        MOXyJsonProvider moxyJsonProvider = new MOXyJsonProvider();

        moxyJsonProvider.setAttributePrefix("@");
        moxyJsonProvider.setFormattedOutput(true);
        moxyJsonProvider.setIncludeRoot(true);
        moxyJsonProvider.setMarshalEmptyCollections(false);
        moxyJsonProvider.setValueWrapper("$");

        Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        namespacePrefixMapper.put("http://www.example.org/customer", "cust");
        moxyJsonProvider.setNamespacePrefixMapper(namespacePrefixMapper);
        moxyJsonProvider.setNamespaceSeparator(':');

        HashSet<Object> set = new HashSet<Object>(1);
        set.add(moxyJsonProvider);
        return set;
//        return Collections.emptySet();
    }

    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        //in Jersey WADL generation is enabled by default, but we don't
        //want to expose too much information about our apis.
        //therefore we want to disable wadl (http://localhost:8080/service/application.wadl should return http 404)
        //see https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e9020 for details
        properties.put("jersey.config.server.wadl.disableWadl", true);


        //we could also use something like this instead of adding each of our resources
        //explicitely in getClasses():
        //properties.put("jersey.config.server.provider.packages", "com.kth.hi1034.backend.resources");


        return properties;
    }

}