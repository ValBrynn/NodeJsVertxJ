package business.service;

import business.model.LogModel;
import data.repository.LogRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LogService {

    private static LogRepository repo = new LogRepository();


    public static boolean add(LogModel log) throws ParseException {

        Date currentDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(log.getDate());
        data.model.LogModel entity = new data.model.LogModel();
        entity.setId(log.getId());
        entity.setMessage(log.getMessage());
        entity.setDate(currentDate);
        entity.setUser(log.getUserName());
        return repo.add(entity);

    }

    public static List<LogModel> getAll(String userName) {

        List<data.model.LogModel> list = repo.getAll(userName);

        if (list == null || list.size() == 0) return null;

        List<LogModel> result = new ArrayList<>();
        LogModel model;
        for(data.model.LogModel t : list){
            model = new LogModel();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp=dateFormat.format(t.getDate());
            model.setDate(timeStamp);
            model.setMessage(t.getMessage());
            model.setId(t.getId());
            result.add(model);
        }
        return result;

    }

}
