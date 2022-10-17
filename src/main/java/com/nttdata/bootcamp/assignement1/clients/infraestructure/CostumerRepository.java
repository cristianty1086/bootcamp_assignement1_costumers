package com.nttdata.bootcamp.assignement1.clients.infraestructure;

import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * ReactiveMongoRepository nos facilita operaciones CRUD
 * El segundo parametro es Integer porque la id de Costumer es de tipo Integer
 */
@Repository
public interface CostumerRepository extends ReactiveMongoRepository<Costumer, Integer> {

}
