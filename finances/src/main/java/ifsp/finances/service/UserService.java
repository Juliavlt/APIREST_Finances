package ifsp.finances.service;

import ifsp.finances.enums.ExpenseCategoryEnum;
import ifsp.finances.enums.IncomeCategoryEnum;
import ifsp.finances.enums.TypeEnum;
import ifsp.finances.model.Category;
import ifsp.finances.model.Finance;
import ifsp.finances.model.User;
import ifsp.finances.model.dto.*;
import ifsp.finances.repository.CategoryRepository;
import ifsp.finances.repository.FinanceRepository;
import ifsp.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private CategoryRepository categoryRepository;

    public UserResponseDTO create(UserRequestDTO requestDTO) {

        List<UserResponseDTO> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(requestDTO.getUsername())
                    && users.get(i).getPassword().equals(requestDTO.getPassword())){
                return null;
            }
        }

        User user = User.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .createdAt(LocalDate.now())
                .modifiedAt(null)
                .despesas(new ArrayList<>())
                .receitas(new ArrayList<>())
                .totalDespesas(0)
                .totalReceitas(0)
                .total(0)
                .build();

        repository.save(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .despesas(new ArrayList<>())
                .receitas(new ArrayList<>())
                .totalReceitas(0)
                .totalDespesas(0)
                .total(0)
                .build();
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        User user = repository.findById(id).orElseThrow(() -> new Error("Not Found"));
        List<Finance> despesas = financialService.getFinanceByUserId(user.getId()).getDespesasResponseList();
        List<Finance> receitas = financialService.getFinanceByUserId(user.getId()).getReceitasResponseList();


        repository.save(User.builder()
                .id(user.getId())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .createdAt(user.getCreatedAt())
                .modifiedAt(LocalDate.now())
                .despesas(despesas)
                .receitas(receitas)
                .totalReceitas(finances(despesas,receitas).getTotalReceitas())
                .totalDespesas(finances(despesas,receitas).getTotalDespesas())
                .total(finances(despesas,receitas).getTotal())
                .build());

        return UserResponseDTO.builder()
                .id(user.getId())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .despesas(despesas)
                .receitas(receitas)
                .totalReceitas(finances(despesas,receitas).getTotalReceitas())
                .totalDespesas(finances(despesas,receitas).getTotalDespesas())
                .total(finances(despesas,receitas).getTotal())
                .build();
    }

    public UserResponseDTO getUserById(Long userId) {

        Optional<User> user = repository.findById(userId);
        if (user.isPresent()){
            List<Finance> despesas = financialService.getFinanceByUserId(user.get().getId()).getDespesasResponseList();
            List<Finance> receitas = financialService.getFinanceByUserId(user.get().getId()).getReceitasResponseList();

            return UserResponseDTO.builder()
                    .id(user.get().getId())
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .despesas(despesas)
                    .receitas(receitas)
                    .totalReceitas(finances(despesas,receitas).getTotalReceitas())
                    .totalDespesas(finances(despesas,receitas).getTotalDespesas())
                    .total(finances(despesas,receitas).getTotal())
                    .build();
        }
        return null;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> userList = repository.findAll();
        List<UserResponseDTO> userResponseList = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {

            List<Finance> despesas = financialService.getFinanceByUserId(userList.get(i).getId()).getDespesasResponseList();
            List<Finance> receitas = financialService.getFinanceByUserId(userList.get(i).getId()).getReceitasResponseList();

            userResponseList.add(UserResponseDTO.builder()
                    .id(userList.get(i).getId())
                    .username(userList.get(i).getUsername())
                    .password(userList.get(i).getPassword())
                    .despesas(despesas)
                    .receitas(receitas)
                    .totalReceitas(finances(despesas,receitas).getTotalReceitas())
                    .totalDespesas(finances(despesas,receitas).getTotalDespesas())
                    .total(finances(despesas,receitas).getTotal())
                    .build());
        }
        return userResponseList;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public UserResponseDTO authenticate(String username, String password) {
        UserResponseDTO user = null;
        List<User> users = repository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) &&
                    users.get(i).getPassword().equals(password)) {
                user = getUserById(users.get(i).getId());
            }
        }
        return user;
    }

    public FinancasTotalDTO finances(List<Finance> despesas, List<Finance> receitas) {

        long totalDespesas = 0;
        long totalReceitas = 0;
        long total = 0;

        for (int i = 0; i < despesas.size(); i++) {
            totalDespesas = despesas.get(i).getValor();
        }

        for (int i = 0; i < receitas.size(); i++) {
            totalReceitas = receitas.get(i).getValor();
        }

        if (totalDespesas>totalReceitas){
            total=totalDespesas-totalReceitas;
        } else{
            total = totalReceitas-totalDespesas;
        }

        return FinancasTotalDTO.builder()
                .totalDespesas(totalDespesas)
                .totalReceitas(totalReceitas)
                .total(total)
                .build();

    }

    public void createCategoria(String categoria, long userId, long tipo){
        Category category = Category.builder().tipo(tipo).idUser(userId).categoria(categoria).build();
        categoryRepository.save(category);
        Category categoryUser = Category.builder()
                .idUser(userId)
                .categoria(categoria)
                .tipo(tipo)
                .build();
        List<Category> categories = categoryRepository.findByIdUser(userId);
        categories.add(categoryUser);
    }
}