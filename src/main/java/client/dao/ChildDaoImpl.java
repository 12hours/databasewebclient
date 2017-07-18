package client.dao;

import client.domain.Child;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.RollbackException;
import java.util.List;

@Repository(value = "childDaoWork")
public class ChildDaoImpl implements ChildDao{

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Override
    public Child getChildById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Child child = em.find(Child.class, id);
        return child;
    }

    @Override
    public void saveChild(Child child) {
        System.err.println("saving child");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(child);
        em.getTransaction().commit();
        em.close();
    }

    public void saveShildren(List<Child> children){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        for (Child child : children){
            em.persist(child);
        }
        try {
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            em.close();
        }
    }

    @Override
    public List<Child> getAllChildren() {
        return null;
    }
}
