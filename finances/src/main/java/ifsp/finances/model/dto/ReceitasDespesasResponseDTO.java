package ifsp.finances.model.dto;

import ifsp.finances.model.Finance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasDespesasResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idUser;

    private double totalExpense;

    private double totalIncome;

    private List<Finance> receitasResponseList;

    private List<Finance> despesasResponseList;
}