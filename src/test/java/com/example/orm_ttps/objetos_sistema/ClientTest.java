package com.example.orm_ttps.objetos_sistema;

import com.example.orm_ttps.clases_dao.GenericDAO;
import com.example.orm_ttps.clases_dao_impl_hibernate.ClientDAOHibernateJPA;
import com.example.orm_ttps.clases_dao_impl_hibernate.GenericDAOHibernateJPA;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.util.List;

public class ClientTest {
    private ClientDAOHibernateJPA clientDAO;
    private GenericDAOHibernateJPA<Suggestion> suggestionDAO;
    private Client client;
    @BeforeEach
     void setUp() {
        clientDAO = new ClientDAOHibernateJPA();
        suggestionDAO = new GenericDAOHibernateJPA<Suggestion>(Suggestion.class);
        client = new Client();
        client.setDni("45284325");
        client.setName("Mateo");
        client.setSurname("Spinetti");
        client.setEmail("mateospinetti1@gmail.com");
        client.setPassword("123456");
        client.setPhoto_url("https://www.google.com");
        clientDAO.save(client);


    }

//    @AfterEach
//    void tearDown() {
//        if (client != null && client.getId() != null) {
//
//            for (Suggestion suggestion : clientDAO.getSuggestions(client)) {
//                suggestionDAO.delete(suggestion);
//            }
//            clientDAO.delete(client);
//
//        }
//    }


    @Test
    void testCreate(){


        Suggestion suggestion1 = new Suggestion();
        suggestion1.setType("Infraestructura");
        suggestion1.setSuggestion("Mejorar la iluminación del buffet");
        suggestion1.setClient(client);


        Suggestion suggestion2 = new Suggestion();
        suggestion2.setType("Comida");
        suggestion2.setSuggestion("Agregar más opciones vegetarianas");
        suggestion2.setClient(client);

        suggestionDAO.save(suggestion1);
        suggestionDAO.save(suggestion2);

        Assertions.assertNotNull(client.getId());
        Assertions.assertNotNull(suggestion1.getId());
        Assertions.assertNotNull(suggestion2.getId());


        List<Suggestion> suggestions = clientDAO.getSuggestions(client);
        Assertions.assertEquals(2, suggestions.size());



    }

    @Test
    void testRetrieve(){



        clientDAO.get(client.getId());
        Assertions.assertNotNull(client.getId());
        Assertions.assertEquals("45284325", client.getDni());
        Assertions.assertEquals("Mateo", client.getName());
        Assertions.assertEquals("Spinetti", client.getSurname());
        Assertions.assertEquals("mateospinetti1@gmail.com", client.getEmail());
        Assertions.assertEquals("123456", client.getPassword());
        Assertions.assertEquals("https://www.google.com", client.getPhoto_url());

    }


    @Test
    void testUpdate(){
        client.setName("Juan");
        client.setSurname("Perez");
        client.setEmail("juanperez@gmail.com");
        client.setPhoto_url("https://soyeldiablo.com");
        clientDAO.update(client);

        Client retrievedClient = clientDAO.get(client.getId());
        Assertions.assertEquals("Juan", retrievedClient.getName());
        Assertions.assertEquals("Perez", retrievedClient.getSurname());
        Assertions.assertEquals("juanperez@gmail.com", retrievedClient.getEmail());
        Assertions.assertEquals("https://soyeldiablo.com", retrievedClient.getPhoto_url());


    }

    @Test
    void testDelete(){
        Suggestion suggestion1 = new Suggestion();
        suggestion1.setType("Infraestructura");
        suggestion1.setSuggestion("Mejorar la iluminación del buffet");
        suggestion1.setClient(client);


        Suggestion suggestion2 = new Suggestion();
        suggestion2.setType("Comida");
        suggestion2.setSuggestion("Agregar más opciones vegetarianas");
        suggestion2.setClient(client);

        suggestionDAO.save(suggestion1);
        suggestionDAO.save(suggestion2);




        Client retrievedClient = clientDAO.get(client.getId());
        List <Suggestion> suggestions = clientDAO.getSuggestions(retrievedClient);

        for (Suggestion suggestion : suggestions) {
            suggestionDAO.delete(suggestion);
        }

        List<Suggestion> retrieved_suggestions = clientDAO.getSuggestions(client);

        Assertions.assertEquals(0, retrieved_suggestions.size());

        long id = client.getId();
        clientDAO.delete(client);

        client = clientDAO.get(id);

        Assertions.assertNull(client);





    }






}
