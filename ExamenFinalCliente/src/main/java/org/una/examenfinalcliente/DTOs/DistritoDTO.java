package org.una.examenfinalcliente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Adrian
 */

@Data
@AllArgsConstructor
@NoArgsConstructor 
@ToString
public class DistritoDTO {
    
    private Long id; 
    private String nombreDistrito;
    private Integer codigoDistrito;
    private CantonDTO canton;
    
}
