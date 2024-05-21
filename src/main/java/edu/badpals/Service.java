package edu.badpals;

import java.util.Optional;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import edu.badpals.repository.ItemRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Service {

    @PersistenceContext
    jakarta.persistence.EntityManager em;

    @Inject
    ItemRepository itemRepo;

    Service(){}

    public Usuaria cargaUsuaria(String nombre){
        Optional<Usuaria> usuaria = Usuaria.findByIdOptional(nombre);
        return usuaria.isPresent()? usuaria.get(): new Usuaria();
    }

    public Item cargaItem(String nombre){
        Optional<Item> item = itemRepo.findByIdOptional(nombre);
        return item.isPresent()? item.get(): new Item();
    }

    public List<Orden> cargaOrden(String name){
        return Orden.findbyUser(name);
    }

    @Transactional
    public Orden comanda(String user, String item){
        Orden orden = null;
        Optional<Usuaria> ordenUser = Usuaria.findByIdOptional(user);
        Optional<Item> ordenItem = Item.findByIdOptional(item);
        if (ordenUser.isPresent() && ordenItem.isPresent() 
            && ordenUser.get().getDestreza() >= ordenItem.get().getQuality()){
            orden = new Orden(user, item);
            orden.persist();
        }
        return orden;
    }

    @Transactional
    public List<Orden> comandaMultiple(String user, List<String> items) {
        List<Orden> ordenes = new ArrayList<>();
        for (String item: items){
            Orden thisOrden = comanda(user, item);
            if (thisOrden != null){
                ordenes.add(thisOrden);
            }
        }
        return ordenes;
    }

    
}
