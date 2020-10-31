package org.una.examenfinalcliente.DTOs;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Esteban Vargas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor 
@ToString
public class ProyectoDTO {
    
    private Long id; 
    private String nombreProyecto;
    private String encargado;
    private Date fechaInicio; 
    private Date fechaFinalizacion; 
    private Boolean estado;
    private Integer porcentajeAvance;
}
