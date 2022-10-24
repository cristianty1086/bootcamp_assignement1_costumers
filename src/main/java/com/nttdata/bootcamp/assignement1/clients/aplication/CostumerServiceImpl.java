package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.infraestructure.CostumerRepository;
import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CostumerServiceImpl implementa las operacion CRUD
 */
@Service
public class CostumerServiceImpl implements CostumerService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CostumerServiceImpl.class);

    @Autowired
    CostumerRepository costumerRepository;

    @Override
    public Mono<Costumer> createCostumer(Mono<Costumer> costumer) {
        if( costumer == null ) {
            LOGGER.error("Error en: solicitud realizada para crear cliente, datos enviados");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no envio ningun dato", null);
        }
        LOGGER.info("Solicitud realizada para crear cliente");
        return costumer.flatMap(costumerRepository::insert);
    }

    @Override
    public Mono<Costumer> readCostumer(Integer costumerId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un cliente");
        return costumerRepository.findById(costumerId);
    }

    @Override
    public Mono<Costumer> updateCostumer(Costumer costumer) {
        LOGGER.info("Solicitud realizada para actualizar al cliente");
        return costumerRepository.save(costumer);
    }

    @Override
    public Mono<Void> deleteCostumer(Integer costumerId) {
        LOGGER.info("Solicitud realizada para crear cliente");
        return costumerRepository.deleteById(costumerId);
    }

    @Override
    public Flux<Costumer> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los clientes");
        return costumerRepository.findAll();
    }
}
