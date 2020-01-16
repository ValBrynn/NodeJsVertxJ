package data.repository;
import data.model.LogModel;
import data.model.UserModel;
import utilities.JPAUtil;
import javax.persistence.EntityManager;
import java.util.List;

public class LogRepository{


    private String unitName;
    private JPAUtil jpaUtil;

    public LogRepository() {
        unitName = "hibernate-unit";
        jpaUtil = new JPAUtil();
    }

    public LogRepository(String unitName) {
        this.unitName = unitName;
        jpaUtil = new JPAUtil();
    }

    public boolean add(LogModel entity) {

        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            em.getTransaction().begin();

            UserModel result =(UserModel) em.createQuery("SELECT t FROM UserModel t where t.userName=:u").setParameter("u",entity.getUser()).getSingleResult();
            entity.setOwnerID(result);
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            System.out.println("Exception in LogRepository.add: " + e.getMessage());
            if(em != null) em.close();
        }

        return false;
    }

    public LogModel delete(int id) {
        return null;
    }

    public LogModel get(int id) {

        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            LogModel result = em.find(LogModel.class, id);
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.get: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }

    public List<LogModel> getAll() {
        return null;
    }


    public List<LogModel> getAll(String user) {
        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();

            UserModel userName=(UserModel) em.createQuery("SELECT t FROM UserModel t where t.userName=:user").setParameter("user",user).getSingleResult();

            if(userName==null) return null;

            List<LogModel> result = em.createQuery("SELECT t FROM LogModel t where t.ownerID=:ownerId").setParameter("ownerId",userName).getResultList();
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.getAll: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }

    public boolean update() {
        return false;
    }


   /* public LogModel getLog(LogModel log) {

        EntityManager em = null;


        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            LogModel result = (LogModel) em.createQuery("SELECT user FROM UserModel user WHERE user.userName=:username AND user.password=:password").setParameter("username",user.getUserName()).setParameter("password",user.getPassword()).getSingleResult();
            em.close();
            return result;//(result.getUserName().equals(user.getUserName())&& result.getPassword().equals(user.getPassword()));
            //return true;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.getUser: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }*/
}
