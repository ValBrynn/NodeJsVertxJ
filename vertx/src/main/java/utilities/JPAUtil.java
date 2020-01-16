package utilities;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private EntityManagerFactory ENTITY_MANAGER_FACTORY_TEST;
    private String persistenceUnitName;

    public JPAUtil(){
        persistenceUnitName = null;
        ENTITY_MANAGER_FACTORY_TEST = null;
    };

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate-unit");
        } catch (Exception e) {
            System.out.println("Exception while trying to load Entity Manager Factory.. -> " + e.getMessage() + "\n" + e.getLocalizedMessage() + "\n");
            e.printStackTrace();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public EntityManagerFactory getEntityManagerFactory(String unit) {
        try {

            if (ENTITY_MANAGER_FACTORY_TEST == null || persistenceUnitName.equals(unit) == false) {
                persistenceUnitName = unit;
                return ENTITY_MANAGER_FACTORY_TEST = Persistence.createEntityManagerFactory(unit);
            }
            return ENTITY_MANAGER_FACTORY_TEST;

        } catch (Exception e) {
            System.out.println("Exception while trying to load Entity Manager Factory Test.. -> " + e.getMessage() + "\n" + e.getLocalizedMessage() + "\n");
            e.printStackTrace();
            throw new javax.persistence.PersistenceException();
        }
    }
}