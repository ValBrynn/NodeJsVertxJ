package business.service;

//import business.model.TestModel;
import business.model.UserModel;
import data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static UserRepository repo = new UserRepository();

    public static UserModel get(int id) {



        data.model.UserModel entity = repo.get(id);

        if (entity == null) return null;

        UserModel result = new UserModel();
        result.setId(entity.getId());
        result.setUserName(entity.getUserName());
        result.setEmail(entity.getEmail());
        result.setName(entity.getName());
        result.setLastName(entity.getLastName());
        result.setPassword(entity.getPassword());
        return result;
    }

    public static UserModel getUser(String userName, String password) {

        data.model.UserModel newU= new data.model.UserModel();

        newU.setUserName(userName);
        newU.setPassword(password);
        data.model.UserModel entity = repo.getUser(newU);

        if (entity == null) return null;
        UserModel result = new UserModel();
        result.setUserName(entity.getUserName());
        result.setPassword(entity.getPassword());
        return result;
    }

    public static boolean findUser(String userName) {

        return repo.findUser(userName);
    }

    public static List<UserModel> getAll() {

        List<data.model.UserModel> list = repo.getAll();

        if (list == null || list.size() == 0) return null;

        List<UserModel> result = new ArrayList<>();
        UserModel model;
        for(data.model.UserModel t : list){
            model = new UserModel();
            model.setId(t.getId());
            model.setUserName(t.getUserName());
            model.setEmail(t.getEmail());
            model.setName(t.getName());
            model.setLastName(t.getLastName());
            model.setPassword(t.getPassword());
            result.add(model);
        }
        return result;

    }

    public static boolean add(UserModel model) {

        data.model.UserModel entity = new data.model.UserModel();
        entity.setId(model.getId());
        entity.setUserName(model.getUserName());
        entity.setEmail(model.getEmail());
        entity.setName(model.getName());
        entity.setLastName(model.getLastName());
        entity.setPassword(model.getPassword());
        return repo.add(entity);

    }

}