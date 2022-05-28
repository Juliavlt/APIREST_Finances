package ifsp.finances.model.dto;

import ifsp.finances.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private List<Category> categoriasGlobais;

    private List<Category> categoriasDoUsuario;

}
