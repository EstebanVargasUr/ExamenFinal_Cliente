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
public class UnidadDTO {
    
    private Long id; 
    private String nombreUnidad;
    private Integer codigoUnidad;
    private String tipoUnidad;
    private Double areaEnMetrosCuadrados;
    private Long cantidadPoblacion;
    private DistritoDTO distrito;
    private TipoDeUnidadDTO tipoDeUnidad;
}

