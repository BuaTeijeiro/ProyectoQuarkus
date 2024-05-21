package edu.badpals.repository;

import jakarta.enterprise.context.ApplicationScoped;
import edu.badpals.domain.Item;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ItemRepository implements PanacheRepositoryBase<Item,String>{
}
