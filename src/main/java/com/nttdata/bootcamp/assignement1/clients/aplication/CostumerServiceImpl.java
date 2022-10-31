package com.nttdata.bootcamp.assignement1.clients.aplication;

import com.nttdata.bootcamp.assignement1.clients.infraestructure.CostumerRepository;
import com.nttdata.bootcamp.assignement1.clients.model.Costumer;
import com.nttdata.bootcamp.assignement1.clients.model.CostumerType;
import com.nttdata.bootcamp.assignement1.clients.utilities.BuilderUrl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

/**
 * CostumerServiceImpl implementa las operacion CRUD
 */
@Service
public class CostumerServiceImpl implements CostumerService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(CostumerServiceImpl.class);

    @Autowired
    CostumerRepository costumerRepository;

    @Autowired
    private RestTemplate restTemplate;

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
    public Mono<Costumer> readCostumer(BigInteger costumerId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un cliente");
        return costumerRepository.findById(costumerId);
    }

    @Override
    public Mono<Costumer> updateCostumer(Costumer costumer) {
        LOGGER.info("Solicitud realizada para actualizar al cliente");

        // gestion del manejo de actualizacion de perfil a vip y pyme
        if(costumer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No envio ningun dato", null);
        }

        if(costumer.getCostumerType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No envio el tipo de cliente", null);
        }

        if(costumer.getCostumerType() == CostumerType.person) {
            if(costumer.getCostumerPersonType() != null) {
                // es un pase a vip, verificar que el cliente tiene una tarjeta de credito con el banco
                String url = BuilderUrl.buildCountCreditCards(costumer.getId());
                Long countCreditCard = restTemplate.getForObject(url, Long.class);
                if( countCreditCard == null || countCreditCard == 0 ) {
                    LOGGER.error("Error: credito no encontrado");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Necesita al menos una tarjeta de credito para ser un cliente vip", null);
                }
            }
        }

        if(costumer.getCostumerType() == CostumerType.enterprise) {
            if(costumer.getCostumerEnterpriseType() != null) {
                // es un pase a pyme, verificar que el cliente tiene una tarjeta de credito con el banco
                String url = BuilderUrl.buildCountCreditCards(costumer.getId());
                Long countCreditCard = restTemplate.getForObject(url, Long.class);
                if( countCreditCard == null || countCreditCard == 0 ) {
                    LOGGER.error("Error: credito no encontrado");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Necesita al menos una tarjeta de credito para ser un cliente pyme", null);
                }
            }
        }

        return costumerRepository.save(costumer);
    }

    @Override
    public Mono<Void> deleteCostumer(BigInteger costumerId) {
        LOGGER.info("Solicitud realizada para crear cliente");
        return costumerRepository.deleteById(costumerId);
    }

    @Override
    public Flux<Costumer> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los clientes");
        return costumerRepository.findAll();
    }
    @Override
    public Mono<String> getBalance(BigInteger costumerId){
        LOGGER.info("Solicitud para obtener el balance");

        // Obtener lista de clientes
        String url = BuilderUrl.buildGetCostumer(costumerId);
        String str_costumer = restTemplate.getForObject(url, String.class);

        // Obtener lista de creditos del cliente
        String url2 = BuilderUrl.buildGetCostumerCredits(costumerId);
        String str_creditos = restTemplate.getForObject(url2, String.class);

        // Obtener lista de cuentas bancarias del cliente
        String url3 = BuilderUrl.buildGetCostumerBankAccounts(costumerId);
        String str_bank_accounts = restTemplate.getForObject(url3, String.class);

        JSONObject jsonObjectCostumer = new JSONObject(str_costumer);
        JSONArray jsonObjectCreditos = new JSONArray(str_creditos);
        JSONArray jsonObjectBankAccounts = new JSONArray(str_bank_accounts);

        jsonObjectCreditos.forEach(it -> {
            JSONObject credito = (JSONObject) it;
            credito.put("creditCard", new JSONArray());
        });
        jsonObjectCostumer.put("credits", jsonObjectCreditos);
        jsonObjectCostumer.put("bank_accounts", jsonObjectBankAccounts);

        return Mono.just(jsonObjectCostumer.toString());
    }

    @Override
    public Mono<String> getConsolidadoCliente(BigInteger costumerId) {

        // Obtener lista de creditos del cliente
        String url2 = BuilderUrl.buildGetCostumerCredits(costumerId);
        String str_creditos = restTemplate.getForObject(url2, String.class);

        // Obtener lista de cuentas bancarias del cliente
        String url3 = BuilderUrl.buildGetCostumerBankAccounts(costumerId);
        String str_bank_accounts = restTemplate.getForObject(url3, String.class);

        JSONArray jsonObjectCreditos = new JSONArray(str_creditos);
        JSONArray jsonObjectBankAccounts = new JSONArray(str_bank_accounts);

        double[] deudaTotalCreditos = {0};
        double[] deudaTotalBankAccount = {0};
        jsonObjectCreditos.forEach(it -> {
            JSONObject credito = (JSONObject) it;
            deudaTotalCreditos[0] += credito.getDouble("currentBalance");
        });
        jsonObjectBankAccounts.forEach(it -> {
            JSONObject bankAccount = (JSONObject) it;
            deudaTotalBankAccount[0] += bankAccount.getDouble("balance");
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deuda_de_creditos", deudaTotalCreditos);
        jsonObject.put("saldo_cuentas_bancarias", deudaTotalBankAccount);
        return Mono.just(jsonObject.toString());
    }
}
