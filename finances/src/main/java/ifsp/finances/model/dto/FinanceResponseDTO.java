package ifsp.finances.model.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long idUser;

    private int tipo;

    @CreatedDate
    private LocalDate dataMovimentacao;

    private String categoria;

    private long valor;

    private String erro;
}