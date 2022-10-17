package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.infraestructure.CostumerRepository;
import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
        costumerRepository.findById(costumer.getId())
                .map( currCostumer -> {
                    LOGGER.info("Cliente encontrado para el id: " + costumer.getId());
                    currCostumer.setName(costumer.getName());
                    currCostumer.setLastname(costumer.getLastname());
                    currCostumer.setDocumentType(costumer.getDocumentType());
                    currCostumer.setDocumentNumber(costumer.getDocumentNumber());
                    currCostumer.setCostumerType(costumer.getCostumerType());
                    return costumerRepository.save(currCostumer);
                });

        return Mono.just(costumer);
    }

    @Override
    public Mono<Void> deleteCostumer(Integer costumerId) {
        LOGGER.info("Solicitud realizada para crear cliente");
        costumerRepository.deleteById(costumerId);
        return null;
    }

    @Override
    public Flux<Costumer> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los clientes");
        return costumerRepository.findAll();
    }
}
