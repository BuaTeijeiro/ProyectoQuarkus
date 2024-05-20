package edu.badpals;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@QuarkusTest
public class ServiceTest {

    @PersistenceContext
    jakarta.persistence.EntityManager em;

    @Inject
    Service servicio;
    
    @Test
    public void test_mapping_normalItem() {
        Item elixir = em.find(Item.class, "Elixir of the Mongoose");
        Assertions.assertThat(elixir).isNotNull();
        Assertions.assertThat(elixir.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(elixir.getQuality()).isEqualTo(7);
        Assertions.assertThat(elixir.getTipo()).isEqualTo("NormalItem");
    }

    

	
	@Test
	public void test_mapping_usuaria() {
		Usuaria elfo = em.find(Usuaria.class, "Doobey");
        Assertions.assertThat(elfo).isNotNull();
        Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}

    
	@Test 
	public void test_mapping_orden() {
		Orden pedido = em.find(Orden.class, 100L);
        Assertions.assertThat(pedido).isNotNull();
        Assertions.assertThat(pedido.getUser()).isEqualTo("Doobey");
		Assertions.assertThat(pedido.getItem()).isEqualToIgnoringCase("Elixir of the Mongoose");
	}

	
    
    @Test
	public void test_inyeccion_servicio() {
		Assertions.assertThat(servicio).isNotNull();
	}

	
	@Test
	public void test_carga_usuaria() {
		Assertions.assertThat(servicio).isNotNull();
		Usuaria elfo = servicio.cargaUsuaria("Doobey");
		Assertions.assertThat(elfo).isNotNull();
		Assertions.assertThat(elfo.getNombre()).isEqualTo("Doobey");
        Assertions.assertThat(elfo.getDestreza()).isEqualTo(15);
	}

    
    
    @Test
	public void test_carga_usuaria_no_existe() {
		Assertions.assertThat(servicio).isNotNull();
		Usuaria profesor = servicio.cargaUsuaria("Severus");
		Assertions.assertThat(profesor).isNotNull();
		Assertions.assertThat(profesor.getNombre()).isEmpty();
        Assertions.assertThat(profesor.getDestreza()).isZero();
	}

    
    
    @Test
	public void test_carga_item() {
        Assertions.assertThat(servicio).isNotNull();
		Item item = servicio.cargaItem("Elixir of the Mongoose");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEqualTo("Elixir of the Mongoose");
		Assertions.assertThat(item.getQuality()).isEqualTo(7);
	}

    
    
    @Test
	public void test_carga_item_no_existe() {
        Assertions.assertThat(servicio).isNotNull();
		Item item = servicio.cargaItem("Reliquias de la muerte");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEmpty();
		Assertions.assertThat(item.getQuality()).isZero();
	}

	
    
    @Test
	public void test_carga_orden() {
        Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.cargaOrden("Hermione");
		Assertions.assertThat(ordenes).isNotNull();
		Assertions.assertThat(ordenes).hasSize(1);
		Assertions.assertThat(ordenes.get(0).getUser()).isEqualToIgnoringCase("Hermione");
		Assertions.assertThat(ordenes.get(0).getItem()).isEqualTo("+5 Dexterity Vest");
	}
    
    @Test
	public void test_carga_orden_no_existe() {
        Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.cargaOrden("Severus");
		Assertions.assertThat(ordenes).isNotNull();
		Assertions.assertThat(ordenes).isEmpty();
	}

    
	@Test
	@Transactional
	public void test_comanda_ok() {
        Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Hermione", "AgedBrie");
		Assertions.assertThat(orden).isNotNull();
		Assertions.assertThat(orden.getId()).isNotZero();
		Assertions.assertThat(orden.getUser()).isEqualTo("Hermione");
		Assertions.assertThat(orden.getItem()).isEqualTo("AgedBrie");

		TypedQuery<Orden> query = em.createQuery("select orden from Orden orden where orden.user = 'Hermione'", Orden.class);
		List<Orden> pedidos = query.getResultList();
		
        Assertions.assertThat(pedidos).isNotNull();
		Assertions.assertThat(pedidos).hasSize(2);
        Assertions.assertThat(pedidos.get(0).getUser()).isEqualTo("Hermione");
		Assertions.assertThat(pedidos.get(0).getItem()).isEqualToIgnoringCase("AgedBrie");
		em.find(Orden.class, pedidos.get(0).getId()).delete();
	}
    
	@Test
	public void test_comanda_no_user() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Severus", "+5 Dexterity Vest");
		Assertions.assertThat(orden).isNull();
		Usuaria profesor = servicio.cargaUsuaria("Severus");
		Assertions.assertThat(profesor).isNotNull();
		Assertions.assertThat(profesor.getNombre()).isEmpty();
        Assertions.assertThat(profesor.getDestreza()).isZero();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}
    
	@Test
	public void test_comanda_no_item() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Hermione", "Reliquias de la muerte");
		Assertions.assertThat(orden).isNull();
		Item item = (Item) servicio.cargaItem("Reliquias de la muerte");
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getNombre()).isEmpty();
		Assertions.assertThat(item.getQuality()).isZero();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}

    
	@Test
	public void test_comanda_item_sin_pro() {
		Assertions.assertThat(servicio).isNotNull();
		Orden orden = servicio.comanda("Doobey", "+5 Dexterity Vest");
		Assertions.assertThat(orden).isNull();

		Orden pedido = em.find(Orden.class, 3L);
        Assertions.assertThat(pedido).isNull();
	}

   
	@Test
	@Transactional
	public void test_ordenar_multiples_items_ok() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Hermione", Arrays.asList("AgedBrie", "Elixir of the Mongoose"));
		Assertions.assertThat(ordenes).isNotEmpty();
		Assertions.assertThat(ordenes).size().isEqualTo(2);

		TypedQuery<Orden> query = em.createQuery("select orden from Orden orden where orden.user = 'Hermione'", Orden.class);
		List<Orden> pedidos = query.getResultList();
		
        Assertions.assertThat(pedidos).isNotNull();
		Assertions.assertThat(pedidos).hasSize(3);
        Assertions.assertThat(pedidos.get(0).getUser()).isEqualTo("Hermione");
		Assertions.assertThat(pedidos.get(0).getItem()).isEqualToIgnoringCase("AgedBrie");
		Assertions.assertThat(pedidos.get(1).getItem()).isEqualToIgnoringCase("Elixir of the Mongoose");
		Assertions.assertThat(pedidos.get(2).getItem()).isEqualToIgnoringCase("+5 Dexterity Vest");
		em.find(Orden.class, pedidos.get(1).getId()).delete();
		em.find(Orden.class, pedidos.get(0).getId()).delete();
	}

    
	@Test
	@Transactional
	public void test_ordenar_multiples_items_no_user() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Severus", Arrays.asList("+5 Dexterity Vest", "Elixir of the Mongoose"));
		Assertions.assertThat(ordenes).isEmpty();
	}

   
	@Test
	@Transactional
	public void test_ordenar_multiples_items_no_item() {
		Assertions.assertThat(servicio).isNotNull();
		List<Orden> ordenes = servicio.comandaMultiple("Hermione", Arrays.asList("Guardapelo Salazar", "Reliquias de la Muerte"));
		Assertions.assertThat(ordenes).isEmpty();

	}
}
