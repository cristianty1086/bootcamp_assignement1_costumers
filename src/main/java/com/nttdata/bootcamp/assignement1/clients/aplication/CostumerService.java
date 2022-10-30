package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CostumerService {

    // crear
    Mono<Costumer> createCostumer(Mono<Costumer> costumer);
    // leer
    Mono<Costumer> readCostumer(BigInteger costumerId);
    // actualizar
    Mono<Costumer> updateCostumer(Costumer costumer);
    // delete
    Mono<Void> deleteCostumer(BigInteger costumerId);
    // leer todas
    Flux<Costumer> listarTodos();
    Mono<String> getBalance(BigInteger costumerId);
}
