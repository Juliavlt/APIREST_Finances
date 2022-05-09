package ifsp.finances.model.dto;

import ifsp.finances.model.Finance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String username;

    private String password;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private List<Finance> despesas;

    private List<Finance> receitas;
}

