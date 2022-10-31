package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequestMapping("costumer")
public class CostumerController {

    @Autowired
    CostumerService costumerService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Costumer> createCostumer(@RequestBody Costumer costumer){
        System.out.println(costumer);
        return costumerService.createCostumer(Mono.just(costumer));
    }

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Costumer> getCostumerById(@PathVariable("id") BigInteger id){
        return costumerService.readCostumer(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<Costumer> updateCostumer(@RequestBody Costumer costumer){
        return costumerService.updateCostumer(costumer);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCostumerById(@PathVariable("id") BigInteger id){
        return costumerService.deleteCostumer(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<Costumer> listarTodos(){
        return costumerService.listarTodos();
    }

    @GetMapping(value = "getSaldos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<String> getBalance(@PathVariable("id") BigInteger id){
        return costumerService.getBalance(id);
    }

    @GetMapping(value = "getConsolidadoCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<String> getConsolidadoCliente(@PathVariable("id") BigInteger id){
        return costumerService.getConsolidadoCliente(id);
    }
}
