package edu.badpals.repository;

import edu.badpals.domain.Orden;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrdenRepository implements PanacheRepositoryBase<Orden,Long>{
    
    public List<Orden> findbyUser(String name){
        List<Orden> ordenes = this.listAll();
        return ordenes.stream().filter(o -> o.getUser().equals(name)).toList();
    }

}
