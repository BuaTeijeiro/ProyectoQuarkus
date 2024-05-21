package edu.badpals.repository;

import jakarta.enterprise.context.ApplicationScoped;
import edu.badpals.domain.Usuaria;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuariaRepository implements PanacheRepositoryBase<Usuaria,String>{

}
