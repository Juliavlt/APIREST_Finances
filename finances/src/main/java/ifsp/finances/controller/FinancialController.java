package ifsp.finances.controller;

import ifsp.finances.model.dto.*;
import ifsp.finances.service.FinancialService;
import ifsp.finances.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/SC3008487")
public class FinancialController {

    //USERS
    @Autowired private UserService userService;

    @Autowired private FinancialService financialService;


    @PostMapping("/user")
    @Transactional
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO saveUser = userService.create(userRequestDTO);
        URI locationResource =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveUser.getId())
                        .toUri();
        return ResponseEntity.created(locationResource).body(saveUser);
    }

    @PutMapping("/user/{id}")
    @Transactional
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id, @RequestBody UserRequestDTO appArsenal) {
        UserResponseDTO userUpdate = userService.update(id, appArsenal);
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
    public ResponseEntity<String> getById(
            @RequestParam(value = "user", required = true) String user,
            @RequestParam(value = "pass", required = true) String pass) {
        if (userService.authenticate(user,pass)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
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
    public ResponseEntity<ReceitasDespesasResponseDTO> getFinanceById(@PathVariable long id) {

        ReceitasDespesasResponseDTO response = financialService.getFinanceByUserId(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/financial")
    @Transactional
    public ResponseEntity<ReceitasDespesasResponseDTO> getAllFinances() {

        ReceitasDespesasResponseDTO response = financialService.getAllFinances();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/financial/{id}")
    @Transactional
    public ResponseEntity deleteFinance(@PathVariable long id) {
        financialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}