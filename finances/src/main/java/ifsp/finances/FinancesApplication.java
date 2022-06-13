package ifsp.finances;

import ifsp.finances.enums.TypeEnum;
import ifsp.finances.model.Finance;
import ifsp.finances.model.dto.FinanceRequestDTO;
import ifsp.finances.model.dto.UserRequestDTO;
import ifsp.finances.model.dto.UserResponseDTO;
import ifsp.finances.service.FinancialService;
import ifsp.finances.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class FinancesApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinancesApplication.class, args);
	}
}

