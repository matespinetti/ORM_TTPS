package com.example.orm_ttps.clases_dao;

import com.example.orm_ttps.objetos_sistema.Client;
import com.example.orm_ttps.objetos_sistema.Suggestion;
import com.example.orm_ttps.objetos_sistema.User;

import java.util.List;

public interface ClientDAO extends GenericDAO<Client>{
    public List<Suggestion> getSuggestions(Client client);


}
