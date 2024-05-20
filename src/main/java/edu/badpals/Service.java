package edu.badpals;

import java.util.Optional;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class Service {

    @PersistenceContext
    jakarta.persistence.EntityManager em;

    Service(){}

    public static Usuaria cargaUsuaria(String nombre){
        Optional<Usuaria> usuaria = Usuaria.findByIdOptional(nombre);
        return usuaria.isPresent()? usuaria.get(): new Usuaria();
    }

    public static Item cargaItem(String nombre){
        Optional<Item> item = Item.findByIdOptional(nombre);
        return item.isPresent()? item.get(): new Item();
    }

    public static List<Orden> cargaOrden(String name){
        return Orden.findbyUser(name);
    }
}
