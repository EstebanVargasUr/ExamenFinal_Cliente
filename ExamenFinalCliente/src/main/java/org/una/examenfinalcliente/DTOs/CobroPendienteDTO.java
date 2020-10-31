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
public class CobroPendienteDTO {

    private Long id;
    private Integer periodo;
    private Date fechaVencimiento;
    private MembresiaDTO membresia;

}
