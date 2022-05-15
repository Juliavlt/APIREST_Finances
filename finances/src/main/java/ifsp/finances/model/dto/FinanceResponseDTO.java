package ifsp.finances.model.dto;

import lombok.*;

import java.io.Serializable;
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

    private String tipo;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String categoria;

    private long valor;
}