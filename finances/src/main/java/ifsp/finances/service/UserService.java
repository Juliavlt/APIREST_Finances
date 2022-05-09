package ifsp.finances.service;

import ifsp.finances.model.Finance;
import ifsp.finances.model.User;
import ifsp.finances.model.dto.UserRequestDTO;
import ifsp.finances.model.dto.UserResponseDTO;
import ifsp.finances.repository.FinanceRepository;
import ifsp.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private FinancialService financialService;

    public UserResponseDTO create(UserRequestDTO requestDTO) {

        User user = User.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .createdAt(LocalDateTime.now())
                .modifiedAt(null)
                .despesas(new ArrayList<>())
                .receitas(new ArrayList<>())
                .build();

        repository.save(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .createdDate(user.getCreatedAt())
                .updatedDate(user.getModifiedAt())
                .despesas(new ArrayList<>())
                .receitas(new ArrayList<>())
                .build();
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        User user = repository.findById(id).orElseThrow(() -> new Error("Not Found"));

        repository.save(User.builder()
                .id(user.getId())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .createdAt(user.getCreatedAt())
                .modifiedAt(LocalDateTime.now())
                .despesas(financialService.getFinanceByUserId(user.getId()).getDespesasResponseList())
                .receitas(financialService.getFinanceByUserId(user.getId()).getReceitasResponseList())
                .build());

        return UserResponseDTO.builder()
                .id(user.getId())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .createdDate(user.getCreatedAt())
                .updatedDate(LocalDateTime.now())
                .despesas(financialService.getFinanceByUserId(user.getId()).getDespesasResponseList())
                .receitas(financialService.getFinanceByUserId(user.getId()).getReceitasResponseList())
                .build();
    }

    public UserResponseDTO getUserById(Long userId) {
        Optional<User> user = repository.findById(userId);

        return UserResponseDTO.builder()
                .id(user.get().getId())
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .createdDate(user.get().getCreatedAt())
                .updatedDate(user.get().getModifiedAt())
                .despesas(financialService.getFinanceByUserId(user.get().getId()).getDespesasResponseList())
                .receitas(financialService.getFinanceByUserId(user.get().getId()).getReceitasResponseList())
                .build();
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> userList = repository.findAll();
        List<UserResponseDTO> userResponseList = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            userResponseList.add(UserResponseDTO.builder()
                    .id(userList.get(i).getId())
                    .username(userList.get(i).getUsername())
                    .password(userList.get(i).getPassword())
                    .createdDate(userList.get(i).getCreatedAt())
                    .updatedDate(userList.get(i).getModifiedAt())
                    .despesas(financialService.getFinanceByUserId(userList.get(i).getId()).getDespesasResponseList())
                    .receitas(financialService.getFinanceByUserId(userList.get(i).getId()).getReceitasResponseList())
                    .build());
        }
        return userResponseList;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean authenticate(String username, String password) {
        boolean userExists = false;
        List<User> users = repository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) &&
                    users.get(i).getPassword().equals(password)) {
                userExists = true;
            }
        }
        return userExists;
    }
}