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
public class CantonDTO {
    
    private Long id; 
    private String nombreCanton;
    private Integer codigoCanton;
    private ProvinciaDTO provincia;
    
}

