package ifsp.finances.service;

import ifsp.finances.enums.TypeEnum;
import ifsp.finances.model.Finance;
import ifsp.finances.model.dto.ReceitasDespesasResponseDTO;
import ifsp.finances.model.dto.FinanceRequestDTO;
import ifsp.finances.model.dto.FinanceResponseDTO;
import ifsp.finances.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FinancialService{

    @Autowired private FinanceRepository repository;

    public FinanceResponseDTO create(FinanceRequestDTO requestDTO) {

        Finance finance = Finance.builder()
                .idUser(requestDTO.getIdUser())
                .tipo(requestDTO.getTipo())
                .categoria(requestDTO.getCategoria())
                .valor(requestDTO.getValor())
                .createdAt(requestDTO.getDataMovimentacao())
                .modifiedAt(null)
                .build();

        repository.save(finance);

        return FinanceResponseDTO.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(finance.getTipo())
                .categoria(finance.getCategoria().toUpperCase(Locale.ROOT))
                .valor(requestDTO.getValor())
                .dataMovimentacao(finance.getModifiedAt() == null ? finance.getCreatedAt().toString() : finance.getModifiedAt().toString())
                .build();

    }

    public FinanceResponseDTO update(Long id,FinanceRequestDTO requestDTO) {
        Finance finance = repository.findById(id).get();
        if(finance==null){
            FinanceResponseDTO.builder().erro("Finança não encontrada").build();
        }

        repository.save(Finance.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(requestDTO.getTipo())
                .categoria(requestDTO.getCategoria())
                .valor(requestDTO.getValor())
                .createdAt(finance.getCreatedAt())
                .modifiedAt(LocalDate.now()).build());

        return FinanceResponseDTO.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(finance.getTipo())
                .categoria(finance.getCategoria().toUpperCase(Locale.ROOT))
                .valor(requestDTO.getValor())
                .dataMovimentacao(finance.getModifiedAt() == null ? finance.getCreatedAt().toString() : finance.getModifiedAt().toString())
                .build();

    }

    public ReceitasDespesasResponseDTO getFinanceByUserId(long userId) {
        List<Finance> financesList = repository.findByIdUser(userId);

        return buildExpenseIncomeList(financesList, userId);
    }

    public FinanceResponseDTO getFinanceById(long id) {
        Optional<Finance> finance = repository.findById(id);

        if(finance.isPresent()){
            return FinanceResponseDTO.builder()
                    .id(finance.get().getId())
                    .idUser(finance.get().getIdUser())
                    .tipo(finance.get().getTipo())
                    .categoria(finance.get().getCategoria().toUpperCase(Locale.ROOT))
                    .valor(finance.get().getValor())
                    .dataMovimentacao(finance.get().getModifiedAt() == null ? finance.get().getCreatedAt().toString() : finance.get().getModifiedAt().toString())
                .build();
        }
        return FinanceResponseDTO.builder().erro("Finança não encontrada!").build();
    }

    public ReceitasDespesasResponseDTO getAllFinances(long idUser) {
        List<Finance> accountingList = repository.findByIdUser(idUser);
        if(accountingList.isEmpty()){
            return ReceitasDespesasResponseDTO.builder()
                    .idUser(1)
                    .totalIncome(0)
                    .totalExpense(0)
                    .despesasResponseList(new ArrayList<>())
                    .receitasResponseList(new ArrayList<>())
            .build();
        } else{
            return buildExpenseIncomeList(accountingList, accountingList.get(0).getId());

        }
    }

    public boolean delete(Long id) {
        if(repository.getById(id)!=null){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public ReceitasDespesasResponseDTO buildExpenseIncomeList(List<Finance> accounts, long idUser) {

        List<Finance> financialExpensesResponseList;
        financialExpensesResponseList =
                accounts.stream()
                        .filter(accounting -> accounting.getTipo() == TypeEnum.DESPESA.getId())
                        .collect(Collectors.toList());

        List<Finance> financialIncomeResponseList;
        financialIncomeResponseList =
                accounts.stream()
                        .filter(accounting -> accounting.getTipo() == TypeEnum.RECEITA.getId())
                        .collect(Collectors.toList());

        return ReceitasDespesasResponseDTO.builder()
                .idUser(idUser)
                .totalExpense(getValorTotalFinancas(accounts, TypeEnum.DESPESA))
                .totalIncome(getValorTotalFinancas(accounts, TypeEnum.RECEITA))
                .receitasResponseList(financialIncomeResponseList)
                .despesasResponseList(financialExpensesResponseList)
                .build();
    }

    public long getValorTotalFinancas(List<Finance> accounts, TypeEnum type) {
        return accounts.stream()
                .filter(accounting -> accounting.getTipo()==type.getId())
                .mapToLong(Finance::getValor)
                .sum();
    }


}