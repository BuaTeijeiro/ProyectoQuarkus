package edu.badpals;

import java.util.Optional;

import edu.badpals.domain.Usuaria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class Service {

    @PersistenceContext
    jakarta.persistence.EntityManager em;

    Service(){}

    /*public static Usuaria cargaUsuaria(String nombre){
        Optional<Usuaria> usuaria = 
    }*/
}
