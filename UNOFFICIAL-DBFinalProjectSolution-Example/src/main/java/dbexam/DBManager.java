package dbexam;

import entities.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Properties;

public class DBManager {

    private EntityManager entityManager;

    private static DBManager instance;
	
    private DBManager() {
		//Initialize your entityManager here.
        Properties props = new Properties();

        //Need to make sure that the persistence unit name matches up with persistence.xml.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "jpa_exam",
                props);

        entityManager = emf.createEntityManager();
        System.out.println("Initialized EntityManager!");
    }

    public static DBManager getInstance() {
        if(null == instance) {
            instance = new DBManager();
        }

        return instance;
    }

    public List<Product> findProducts(String nameContains) {
        //Case-insensitive comparison on Product's name attribute.
        TypedQuery<Product> query = entityManager.createQuery(
                "select p from Product p where lower(p.name) " +
                        "like lower(concat('%', :keyword, '%'))",
                Product.class);
        query.setParameter("keyword", nameContains);
        return query.getResultList();
    }

    public void clearProducts() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Product p").executeUpdate();
        entityManager.getTransaction().commit();

        System.out.println("Cleared products table.");
    }

    public void saveProduct(Product p) {
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();

        System.out.println("Saved product with UPC " + p.getUpc());
    }

    public void destroy() {
        //Clean up your entityManager here.
        entityManager.close();
        System.out.println("Cleaned up EntityManager!");
    }
}
