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

    private void popularCategories(){
        Category d1 = Category.builder().idUser(0).categoria("ALIMENTAÇÃO").tipo(1).build();
        Category d2 = Category.builder().idUser(0).categoria("TRANSPORTE").tipo(1).build();
        Category d3 = Category.builder().idUser(0).categoria("SAÚDE").tipo(1).build();
        Category d4 = Category.builder().idUser(0).categoria("OUTROS").tipo(1).build();
        Category r1 = Category.builder().idUser(0).categoria("SALÁRIO").tipo(2).build();
        Category r2 = Category.builder().idUser(0).categoria("RENDA EXTRA").tipo(2).build();
        Category r3 = Category.builder().idUser(0).categoria("OUTRO").tipo(2).build();

        repository.save(d1);
        repository.save(d2);
        repository.save(d3);
        repository.save(d4);
        repository.save(r1);
        repository.save(r2);
        repository.save(r3);
    }

}
