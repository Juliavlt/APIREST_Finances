package ifsp.finances.controller;

import ifsp.finances.model.Category;
import ifsp.finances.model.dto.*;
import ifsp.finances.repository.CategoryRepository;
import ifsp.finances.service.CategoryService;
import ifsp.finances.service.FinancialService;
import ifsp.finances.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/SC3008487")
public class FinancialController {

    //USERS
    @Autowired private UserService userService;

    @Autowired private FinancialService financialService;

    @Autowired private CategoryService categoryService;


    @GetMapping("/user/authenticate")
    @Transactional
    public ResponseEntity<String> getUserById(
            @RequestParam(value = "user", required = true) String user,
            @RequestParam(value = "pass", required = true) String pass) {

        UserResponseDTO response = userService.authenticate(user,pass);
        if (response.getErro() == null) {
            return ResponseEntity.ok(String.valueOf(response.getId()));
        }
        return ResponseEntity.badRequest().body(response.getErro());
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity<String> createUser(
            @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO saveUser = userService.create(userRequestDTO);

        if(saveUser.getErro()==null){
            return ResponseEntity.ok(String.valueOf(saveUser.getId()));
        }
        return ResponseEntity.badRequest().body(saveUser.getErro());
    }

    @DeleteMapping("/user/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    @Transactional
    public ResponseEntity<UserResponseDTO> getUser(
            @PathVariable String id) {
        boolean isNumeric =  id.matches("[+-]?\\d*(\\.\\d+)?");
        if (!isNumeric){
            return ResponseEntity.badRequest().body(UserResponseDTO.builder().erro("Ação não permitida!").build());
        }
        long idUser = Long.parseLong(id);
        UserResponseDTO user = userService.getUserById(idUser);
        if(user.getErro()==null){
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().body(user);
    }

    @GetMapping("/users")
    @Transactional
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }


    //FINANCES
    @PostMapping("/financial")
    @Transactional
    public ResponseEntity<FinanceResponseDTO> createFinance(
            @RequestBody FinanceRequestDTO financial) {
        FinanceResponseDTO response = financialService.create(financial);

        if (response.getErro()==null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/financial/{id}")
    @Transactional
    public ResponseEntity<FinanceResponseDTO> updateFinance(
            @PathVariable String id, @RequestBody FinanceRequestDTO financeRequest) {

        boolean isNumeric =  id.matches("[+-]?\\d*(\\.\\d+)?");
        if (!isNumeric){
            return ResponseEntity.badRequest().body(FinanceResponseDTO.builder().erro("Ação não permitida!").build());
        }
        long idUser = Long.parseLong(id);

        FinanceResponseDTO response = financialService.update(idUser,financeRequest);
        if (response.getErro()==null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/financial/{id}")
    @Transactional
    public ResponseEntity<FinanceResponseDTO> getFinanceById(@PathVariable String id) {

        boolean isNumeric =  id.matches("[+-]?\\d*(\\.\\d+)?");
        if (!isNumeric){
            return ResponseEntity.badRequest().body(FinanceResponseDTO.builder().erro("Ação não permitida!").build());
        }
        long idFinance = Long.parseLong(id);

        FinanceResponseDTO response = financialService.getFinanceById(idFinance);
        if (response.getErro()==null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/financial/{id}")
    @Transactional
    public ResponseEntity deleteFinance(@PathVariable Long id) {
        if (financialService.delete(id)){
            return ResponseEntity.ok().build();
        };
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/category")
    @Transactional
    public ResponseEntity<String> createCategoria(
            @RequestParam(value = "categoria", required = true) String categoria,
            @RequestParam(value = "idUser", required = true) String idUser,
            @RequestParam(value = "tipo", required = true) long tipo) {
        long id = Long.parseLong(idUser);
        CategoryResponseDTO response =  userService.createCategoria(categoria, id, tipo);

        if(response.getErro()==null){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(response.getErro());
    }

    @GetMapping("/category")
    @Transactional
    public ResponseEntity<CategoryResponseDTO> getCategories(
            @RequestParam(value = "idUser", required = true) int idUser,
            @RequestParam(value = "tipo", required = true) int tipo
    ) {
        CategoryResponseDTO response= categoryService.getAllCategories(tipo, idUser);
        if(response.getErro()==null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

}