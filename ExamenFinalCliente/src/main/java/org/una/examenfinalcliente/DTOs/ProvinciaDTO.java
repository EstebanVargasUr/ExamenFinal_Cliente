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
public class ProvinciaDTO {
    
    private Long id; 
    private String nombreProvincia;
    private Integer codigoProvincia;
    
}
