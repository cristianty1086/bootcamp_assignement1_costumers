package com.nttdata.bootcamp.assignement1.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Maneja la informacion del cliente
 * costumerType indica el tipo de cliente: 1=persona, 2=empresa
 */
@Document(collection = "costumer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Costumer {
    @Id
    Integer id;
    String name;
    String lastname;
    String documentType;
    String documentNumber;
    CostumerType costumerType;
}
