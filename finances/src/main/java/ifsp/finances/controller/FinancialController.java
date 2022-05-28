package ifsp.finances.controller;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/SC3008487")
public class FinancialController {

    //USERS
    @Autowired private UserService userService;

    @Autowired private FinancialService financialService;

    @Autowired private CategoryService categoryService;


    @PostMapping("/user")
    @Transactional
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO saveUser = userService.create(userRequestDTO);

        if(saveUser!=null){

            URI locationResource =
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(saveUser.getId())
                            .toUri();
            return ResponseEntity.created(locationResource).body(saveUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/user/{id}")
    @Transactional
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userUpdate = userService.update(id, userRequestDTO);
        return ResponseEntity.ok(userUpdate);
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
            @PathVariable Long id) {
        UserResponseDTO userUpdate = userService.getUserById(id);
        return ResponseEntity.ok(userUpdate);
    }

    @GetMapping("/user/authenticate")
    @Transactional
    public ResponseEntity<UserResponseDTO> getUserById(
            @RequestParam(value = "user", required = true) String user,
            @RequestParam(value = "pass", required = true) String pass) {

        UserResponseDTO response = userService.authenticate(user,pass);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
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
        FinanceResponseDTO saveFinancial = financialService.create(financial);
        URI locationResource =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveFinancial.getId())
                        .toUri();

        return ResponseEntity.created(locationResource).body(saveFinancial);
    }

    @PutMapping("/financial/{id}")
    @Transactional
    public ResponseEntity<FinanceResponseDTO> updateFinance(
            @PathVariable Long id, @RequestBody FinanceRequestDTO financeRequest) {
        FinanceResponseDTO financeUpdate = financialService.update(id,financeRequest);
        return ResponseEntity.ok(financeUpdate);
    }

    @GetMapping("/financial/{id}")
    @Transactional
    public ResponseEntity<FinanceResponseDTO> getFinanceById(@PathVariable long id) {

        FinanceResponseDTO response = financialService.getFinanceById(id);
        if (response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/financial")
    @Transactional
    public ResponseEntity<ReceitasDespesasResponseDTO> getAllFinances(int idUser) {

        ReceitasDespesasResponseDTO response = financialService.getAllFinances(idUser);

        return ResponseEntity.ok(response);
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
    public ResponseEntity createCategoria(
            @RequestParam(value = "categoria", required = true) String categoria,
            @RequestParam(value = "idUser", required = true) long idUser,
            @RequestParam(value = "tipo", required = true) long tipo) {

        userService.createCategoria(categoria, idUser, tipo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    @Transactional
    public ResponseEntity<CategoryResponseDTO> getCategories(
            @RequestParam(value = "idUser", required = true) int idUser,
            @RequestParam(value = "tipo", required = true) int tipo
    ) {
        CategoryResponseDTO categories = categoryService.getAllCategories(tipo, idUser);
        return ResponseEntity.ok(categories);
    }

}