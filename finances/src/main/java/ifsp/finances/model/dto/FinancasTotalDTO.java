package ifsp.finances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancasTotalDTO {

    private double totalDespesas;

    private double totalReceitas;

    private double total;
}
