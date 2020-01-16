package business.service;
import business.model.ChartModel;
import data.repository.ChartRepository;

public class ChartService {

    private static ChartRepository repo = new ChartRepository();

    public static boolean add(ChartModel model, String userName) {

        data.model.ChartModel entity = new data.model.ChartModel();
        entity.setX(model.getX());
        entity.setY(model.getY());
        entity.setZ(model.getZ());
        entity.setLoggedInUser(userName);
        entity.setChart(model.getChart());
        return repo.add(entity);

    }

    public static ChartModel getChart( String username){
        data.model.ChartModel owner =  repo.getAll(username);
        ChartModel owner1 = new ChartModel();
        if(owner == null){
            return null;
        }
        owner1.setX(owner.getX());
        owner1.setY(owner.getY());
        owner1.setZ(owner.getZ());
        owner1.setChart(owner.getChart());

        return owner1;
    }



}

