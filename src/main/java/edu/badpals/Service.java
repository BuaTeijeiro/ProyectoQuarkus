package edu.badpals;

import java.util.Optional;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import edu.badpals.repository.ItemRepository;
import edu.badpals.repository.OrdenRepository;
import edu.badpals.repository.UsuariaRepository;
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

    @Inject
    UsuariaRepository usuariaRepo;

    @Inject
    OrdenRepository ordenRepo;

    Service(){}

    public Usuaria cargaUsuaria(String nombre){
        Optional<Usuaria> usuaria = usuariaRepo.findByIdOptional(nombre);
        return usuaria.isPresent()? usuaria.get(): new Usuaria();
    }

    public Item cargaItem(String nombre){
        Optional<Item> item = itemRepo.findByIdOptional(nombre);
        return item.isPresent()? item.get(): new Item();
    }

    public List<Orden> cargaOrden(String name){
        return ordenRepo.findbyUser(name);
    }

    @Transactional
    public Orden comanda(String user, String item){
        Orden orden = null;
        Optional<Usuaria> ordenUser = usuariaRepo.findByIdOptional(user);
        Optional<Item> ordenItem = itemRepo.findByIdOptional(item);
        if (ordenUser.isPresent() && ordenItem.isPresent() 
            && ordenUser.get().getDestreza() >= ordenItem.get().getQuality()){
            orden = new Orden(user, item);
            ordenRepo.persist(orden);
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
