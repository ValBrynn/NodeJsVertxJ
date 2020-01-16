package data.repository;

import data.model.ChartModel;
import data.model.UserModel;
import utilities.JPAUtil;
import javax.persistence.EntityManager;


public class ChartRepository  {
    private String unitName;
    private JPAUtil jpaUtil;

    public ChartRepository() {
        unitName = "hibernate-unit";
        jpaUtil = new JPAUtil();
    }



    public ChartModel getAll(String username) {
        EntityManager em = null;

        try{
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();

            UserModel loggedInUser=(UserModel)em.createQuery("SELECT u FROM UserModel u WHERE u.userName = :userName")
                    .setParameter("userName", username).getSingleResult();

            if(loggedInUser==null) return null;

            ChartModel result = (ChartModel) em.createQuery("SELECT l FROM ChartModel l WHERE l.owner = :owner")
                    .setParameter("owner", loggedInUser).getSingleResult();
            em.close();
            return result;
        } catch (Exception e){
            System.out.println("Exception in TestRepository.getAll: " + e.getMessage());
            e.printStackTrace();
            if(em != null) em.close();
        }
        return null;
    }



    public boolean add(ChartModel entity) {
        EntityManager em = null;

        try{


            System.out.println("a");
            em = jpaUtil.getEntityManagerFactory(unitName).createEntityManager();
            em.getTransaction().begin();
            UserModel result=(UserModel)em.createQuery("SELECT u FROM UserModel u WHERE u.userName = :userName")
                    .setParameter("userName", entity.getLoggedInUser()).getSingleResult();
            entity.setOwner(result);


            System.out.println("b");
            int id = result.getId();
            ChartModel chartM = em.find(ChartModel.class, id);
            if(chartM==null){
                em.persist(entity);
                em.getTransaction().commit();
                return true;
            }
            chartM.setX(entity.getX());
            chartM.setY(entity.getY());
            chartM.setZ(entity.getZ());
            chartM.setChart(entity.getChart());
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            System.out.println("Exception in DiagramRepository.add: " + e.getMessage());

            if(em != null){
                em.getTransaction().rollback();
                em.close();
            }
        }

        return false;

    }
}

