package business.service;


import business.model.MessageModel;
import data.repository.MessageRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageService {

    private static MessageRepository repo = new MessageRepository();


    public static boolean add(MessageModel m) throws ParseException {

        Date currentDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m.getDate());
        data.model.MessageModel entity = new data.model.MessageModel();
        entity.setId(m.getId());
        entity.setMessage(m.getMessage());
        entity.setDate(currentDate);
        entity.setSenderUser(m.getSender());
        entity.setReceiverUser(m.getReceiver());
        return repo.add(entity);

    }

    public static List<MessageModel> getReceivedMessages(String userName) {

        List<data.model.MessageModel> list = repo.getReceivedMessages(userName);

        if (list == null || list.size() == 0) return null;

        List<MessageModel> result = new ArrayList<>();
        MessageModel m;
        for(data.model.MessageModel t : list){
            m = new MessageModel();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp=dateFormat.format(t.getDate());
            m.setDate(timeStamp);
            m.setMessage(t.getMessage());
            m.setId(t.getId());
            m.setReceiver(t.getReceiver().getUserName());
            m.setSender(t.getSender().getUserName());
            result.add(m);
        }
        return result;

    }

    public static List<MessageModel> getSentMessages(String userName) {

        List<data.model.MessageModel> list = repo.getSentMessages(userName);
        if (list == null || list.size() == 0) return null;

        List<MessageModel> result = new ArrayList<>();
        MessageModel m;
        for(data.model.MessageModel t : list){
            m = new MessageModel();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp=dateFormat.format(t.getDate());
            m.setDate(timeStamp);
            m.setMessage(t.getMessage());
            m.setSender(t.getSender().getUserName());
            result.add(m);
        }
        return result;

    }
}
