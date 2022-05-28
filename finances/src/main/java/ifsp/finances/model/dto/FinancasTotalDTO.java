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

    private long totalDespesas;

    private long totalReceitas;

    private long total;
}
