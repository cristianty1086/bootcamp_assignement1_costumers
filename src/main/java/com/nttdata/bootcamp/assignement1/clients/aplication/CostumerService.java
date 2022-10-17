package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CostumerService {

    // crear
    Mono<Costumer> createCostumer(Mono<Costumer> costumer);
    // leer
    Mono<Costumer> readCostumer(Integer costumerId);
    // actualizar
    Mono<Costumer> updateCostumer(Costumer costumer);
    // delete
    Mono<Void> deleteCostumer(Integer costumerId);
    // leer todas
    Flux<Costumer> listarTodos();
}
