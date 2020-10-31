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
public class TareaDTO {
    
    private Long id; 
    private String descripcion;
    private Date fechaInicio; 
    private Date fechaFinalizacion; 
    private Short importancia;
    private Short urgencia;
    private Integer prioridad;
    private Integer porcentajeAvance;
    private ProyectoDTO proyecto;
}
