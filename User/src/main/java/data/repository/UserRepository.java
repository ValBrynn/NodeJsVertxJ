
package data.repository;

import data.model.UserModel;
import utilities.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;


public class UserRepository {

    private String unitName;
    private JPAUtil jpaUtil;

    public UserRepository() {
        unitName = "hibernate-unit";
        jpaUtil = new JPAUtil();
    }

    public UserRepository(String unitName) {
        this.unitName = unitName;
        jpaUtil = new JPAUtil();
    }


    public boolean add(UserModel entity) {

        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.add: " + e.getMessage());
            if(em != null) em.close();
        }

        return false;
    }


    public UserModel delete(int id) {
        return null;
    }


    public UserModel get(int id) {

        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            UserModel result = em.find(UserModel.class, id);
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.get: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }




    public List<UserModel> getAll() {
        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            List<UserModel> result = em.createQuery("SELECT t FROM UserModel t").getResultList();
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


    public UserModel getUser(UserModel user) {

        EntityManager em = null;


        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            UserModel result = (UserModel) em.createQuery("SELECT user FROM UserModel user WHERE user.userName=:username AND user.password=:password").setParameter("username",user.getUserName()).setParameter("password",user.getPassword()).getSingleResult();
            em.close();
            return result;//(result.getUserName().equals(user.getUserName())&& result.getPassword().equals(user.getPassword()));
            //return true;
        } catch (Exception e){
            System.out.println("Exception in UserRepository.getUser: " + e.getMessage());
            if(em != null) em.close();
        }

        return null;
    }

    public boolean findUser(String userName) {

        EntityManager em = null;

        System.out.println("Finduser 1111");

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            UserModel result = (UserModel) em.createQuery("SELECT user FROM UserModel user WHERE user.userName=:username").setParameter("username",userName).getSingleResult();
            em.close();
            //return result;//(result.getUserName().equals(user.getUserName())&& result.getPassword().equals(user.getPassword()));
            return result!=null;

        } catch (Exception e){
            System.out.println("Exception in UserRepository.findUser: " + e.getMessage());
            if(em != null) em.close();
        }
        System.out.println("finduser 2222");
        return false;
    }

}

