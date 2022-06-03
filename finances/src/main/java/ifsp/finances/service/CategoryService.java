package ifsp.finances.service;

import ifsp.finances.model.Category;
import ifsp.finances.model.dto.CategoryResponseDTO;
import ifsp.finances.repository.CategoryRepository;
import ifsp.finances.repository.FinanceRepository;
import ifsp.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public CategoryResponseDTO getAllCategories(int tipo, int idUser) {
        List<Category> categoriasDoUsuario = new ArrayList<>();
        List<Category> categoriasGlobais= new ArrayList<>();

        List<Category> categoriesList = repository.findAll();
        List<Category> categoriesUser = repository.findByIdUser(idUser);

        for (int i = 0; i < categoriesList.size(); i++) {
            if (categoriesList.get(i).getTipo() == tipo) {
                categoriasGlobais.add(categoriesList.get(i));
            }
        }
        for (int i = 0; i < categoriesUser.size(); i++) {
            if (categoriesList.get(i).getTipo() == tipo) {
                categoriasDoUsuario.add(categoriesUser.get(i));
            }
        }

        return CategoryResponseDTO.builder()
                .categoriasDoUsuario(categoriasDoUsuario)
                .categoriasGlobais(categoriasGlobais)
                .build();

    }

}
