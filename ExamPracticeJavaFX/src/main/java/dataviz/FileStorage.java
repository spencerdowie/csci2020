package dataviz;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileStorage
{
    private EntityManager entityManager;
    private Properties props;
    public FileStorage()
    {
        this.props = new Properties();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExamTest", props);
        this.entityManager = emf.createEntityManager();
    }

    public void deleteAllData()
    {
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from YearPrice y").executeUpdate();
        entityManager.getTransaction().commit();
    }

    private void createYearPrice(String year, List<Integer> prices)
    {
        List<String> strPrice = new ArrayList<>();
        for(Integer p : prices)
        {
            strPrice.add(p.toString());
        }

        entityManager.getTransaction().begin();
        YearPrice yearPrice = new YearPrice(year, strPrice);
        entityManager.persist(yearPrice);
        entityManager.getTransaction().commit();
    }

    public void saveData(Map<String, List<Integer>> map)
    {
        String[] keys = map.keySet().toArray(new String[map.size()]);
        for(String year : keys)
        {
            createYearPrice(year, map.get(year));
        }
    }

    public void destroy()
    {
        entityManager.close();
    }
}
