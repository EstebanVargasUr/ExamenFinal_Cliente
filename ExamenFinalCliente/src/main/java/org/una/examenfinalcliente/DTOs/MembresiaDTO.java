package org.una.examenfinalcliente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MembresiaDTO {

    private Long id;
    private Integer periodicidad;
    private Float monto;
    private String descripcion;
    private Date fechaRegistro;
    private boolean estado;
    private ClienteTipoServicioDTO clienteTipoServicio;

}
