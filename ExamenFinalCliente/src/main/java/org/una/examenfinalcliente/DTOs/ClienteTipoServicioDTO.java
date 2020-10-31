package org.una.examenfinalcliente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClienteTipoServicioDTO {

    private Long id;
    private ClienteDTO cliente;
    private TipoServicioDTO tipoServicio;

}

