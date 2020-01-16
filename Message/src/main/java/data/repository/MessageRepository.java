package data.repository;


import data.model.MessageModel;
import data.model.UserModel;
import utilities.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class MessageRepository {
    private String unitName;
    private JPAUtil jpaUtil;

    public MessageRepository() {
        unitName = "hibernate-unit";
        jpaUtil = new JPAUtil();
    }

    public MessageRepository(String unitName) {
        this.unitName = unitName;
        jpaUtil = new JPAUtil();
    }

    public boolean add(MessageModel entity) {
        EntityManager em = null;

        try{

            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            em.getTransaction().begin();

            UserModel sender=(UserModel)em.createQuery("SELECT u FROM UserModel u WHERE u.userName = :userName")
                    .setParameter("userName", entity.getSenderUser()).getSingleResult();

            UserModel receiver=(UserModel)em.createQuery("SELECT u FROM UserModel u WHERE u.userName = :userName")
                    .setParameter("userName", entity.getReceiverUser()).getSingleResult();
            entity.setSender(sender);
            if(receiver==null) return false;
            entity.setReceiver(receiver);
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            System.out.println("Exception in MessageRepository.add: " + e.getMessage());

            if(em != null){
                em.getTransaction().rollback();
                em.close();
            }
        }
        return false;
    }

    public List<MessageModel> getReceivedMessages(String username) {
        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();

            UserModel loggedInUser=(UserModel)em.createQuery("SELECT u FROM UserModel u WHERE u.userName = :userName")
                    .setParameter("userName", username).getSingleResult();


            List<MessageModel> result = em.createQuery("SELECT m FROM MessageModel m WHERE m.receiver = :receiver")
                    .setParameter("receiver", loggedInUser)
                    .getResultList();
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in MessageRepository.getReceivedMessages: " + e.getMessage());
            e.printStackTrace();
            if(em != null) em.close();
        }

        return null;
    }

    public List<MessageModel> getSentMessages(String user) {
        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();

            UserModel userName=(UserModel) em.createQuery("SELECT t FROM UserModel t where t.userName=:user").setParameter("user",user).getSingleResult();

            if(userName==null) return null;

            List<MessageModel> result = em.createQuery("SELECT t FROM MessageModel t where t.sender=:ownerId").setParameter("ownerId",userName).getResultList();
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in MessageRepository.getAll: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }


}
