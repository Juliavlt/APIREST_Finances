package ifsp.finances.model.dto;

import ifsp.finances.enums.CategoryEnum;
import ifsp.finances.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long idUser;

    private int tipo;

    private LocalDateTime dataMovimentacao;

    private int categoria;

    private String descricao;

    private long valor;
}