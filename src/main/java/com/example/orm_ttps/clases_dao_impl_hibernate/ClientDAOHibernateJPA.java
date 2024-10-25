package com.example.orm_ttps.clases_dao_impl_hibernate;

import com.example.orm_ttps.clases_dao.ClientDAO;
import com.example.orm_ttps.objetos_sistema.Client;
import com.example.orm_ttps.objetos_sistema.Suggestion;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientDAOHibernateJPA extends GenericDAOHibernateJPA<Client> implements ClientDAO {
    public ClientDAOHibernateJPA() {
        super(Client.class);
    }
    public List<Suggestion> getSuggestions(Client client) {
        EntityManager em = EMF.getEMF().createEntityManager();
        List<Suggestion> suggestions = null;
        try {
            suggestions = em.createQuery("SELECT s FROM Suggestion s WHERE s.client = :client", Suggestion.class)
                    .setParameter("client", client)
                    .getResultList();
        } finally {
            em.close();
        }
        return suggestions;
    }
}
