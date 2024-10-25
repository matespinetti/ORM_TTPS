package com.example.orm_ttps.clases_dao_impl_hibernate;

import com.example.orm_ttps.clases_dao.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;



public class GenericDAOHibernateJPA <T> implements GenericDAO<T> {
    protected Class<T> persistentClass;

    public GenericDAOHibernateJPA(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }



    @Override
    public T save(T entity) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        }
        catch (RuntimeException e) {
            if ( tx != null && tx.isActive() ) tx.rollback();
            throw e; // escribir en un log o mostrar un mensaje
        }
        finally {
            em.close();
        } return entity;
    }


    public T update(T entity) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        }
        catch (RuntimeException e) {
            if ( tx != null && tx.isActive() ) tx.rollback();
            throw e; // escribir en un log o mostrar un mensaje
        }
        finally {
            em.close();
        } return entity;
    }


    @Override
    public void delete(T entity) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e; // escribir en un log o mostrar un mensaje
        } finally {
            em.close();
        }
    }


    @Override
    public T delete(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        T entity = em.find(this.persistentClass, id);
        if (entity != null) {
            em.remove(entity);
        }
        em.close();
        return entity;
    }


    @Override
    public boolean exists(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        T entity = em.find(this.persistentClass, id);
        em.close();
        return entity != null;
    }


    @Override
    public T get(Serializable id) {

        EntityManager em = EMF.getEMF().createEntityManager();
        T entity = em.find(this.persistentClass, id);
        em.close();
        return entity;
    }


    @Override
    public List<T> getAll(String column) {
        EntityManager em = EMF.getEMF().createEntityManager();
            List<T> list = em.createQuery("SELECT e FROM " + this.persistentClass.getSimpleName() + " e ORDER BY e." + column).getResultList();
        em.close();
        return list;
    }
}
