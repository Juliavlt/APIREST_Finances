package ifsp.finances.service;

import ifsp.finances.enums.ExpenseCategoryEnum;
import ifsp.finances.enums.IncomeCategoryEnum;
import ifsp.finances.enums.TypeEnum;
import ifsp.finances.model.Finance;
import ifsp.finances.model.dto.ReceitasDespesasResponseDTO;
import ifsp.finances.model.dto.FinanceRequestDTO;
import ifsp.finances.model.dto.FinanceResponseDTO;
import ifsp.finances.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialService{

    @Autowired private FinanceRepository repository;

    public FinanceResponseDTO create(FinanceRequestDTO requestDTO) {

        Finance finance = Finance.builder()
                .idUser(requestDTO.getIdUser())
                .tipo(this.getTipo(requestDTO.getTipo()))
                .categoria(this.getCategoria(requestDTO.getCategoria(), requestDTO.getTipo()))
                .valor(requestDTO.getValor())
                .createdAt(requestDTO.getDataMovimentacao())
                .modifiedAt(null)
                .build();

        repository.save(finance);

        return FinanceResponseDTO.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(finance.getTipo())
                .categoria(finance.getCategoria())
                .valor(requestDTO.getValor())
                .createdAt(requestDTO.getDataMovimentacao())
                .modifiedAt(null).build();
    }

    public FinanceResponseDTO update(Long id,FinanceRequestDTO requestDTO) {
        Finance finance = repository.findById(id).orElseThrow(() -> new Error("Not Found"));

        repository.save(Finance.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(this.getTipo(requestDTO.getTipo()))
                .categoria(this.getCategoria(requestDTO.getCategoria(), requestDTO.getTipo()))
                .valor(requestDTO.getValor())
                .createdAt(finance.getCreatedAt())
                .modifiedAt(LocalDateTime.now()).build());

        return FinanceResponseDTO.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(this.getTipo(requestDTO.getTipo()))
                .categoria(this.getCategoria(requestDTO.getCategoria(),requestDTO.getTipo()))
                .valor(requestDTO.getValor())
                .createdAt(finance.getCreatedAt())
                .modifiedAt(LocalDateTime.now()).build();
    }

    public ReceitasDespesasResponseDTO getFinanceByUserId(long userId) {
        List<Finance> financesList = repository.findByIdUser(userId);

        return buildExpenseIncomeList(financesList, userId);
    }

    public ReceitasDespesasResponseDTO getAllFinances() {
        List<Finance> accountingList = repository.findAll();
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

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ReceitasDespesasResponseDTO buildExpenseIncomeList(List<Finance> accounts, long idUser) {

        List<Finance> financialExpensesResponseList;
        financialExpensesResponseList =
                accounts.stream()
                        .filter(accounting -> accounting.getTipo().equals(TypeEnum.DESPESA.getDescription()))
                        .collect(Collectors.toList());

        List<Finance> financialIncomeResponseList;
        financialIncomeResponseList =
                accounts.stream()
                        .filter(accounting -> accounting.getTipo().equals(TypeEnum.RECEITA.getDescription()))
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
                .filter(accounting -> accounting.getTipo().equals(type.getDescription()))
                .mapToLong(Finance::getValor)
                .sum();
    }

    private String getCategoria(int id, int tipo){
        String categoria="";
        if (tipo == 1){
            if (ExpenseCategoryEnum.SAUDE.getId() == id){
                categoria = ExpenseCategoryEnum.SAUDE.getDescription();
            } else if (ExpenseCategoryEnum.TRANSPORTE.getId() == id) {
                categoria = ExpenseCategoryEnum.TRANSPORTE.getDescription();
            }else if (ExpenseCategoryEnum.EDUCACAO.getId() == id) {
                categoria = ExpenseCategoryEnum.EDUCACAO.getDescription();
            }else if (ExpenseCategoryEnum.OUTRO.getId() == id) {
                categoria = ExpenseCategoryEnum.OUTRO.getDescription();
            } else {
                categoria = ExpenseCategoryEnum.RESTAURANTE.getDescription();
            }
        } else {
            if (IncomeCategoryEnum.SALARIO.getId() == id) {
                categoria = IncomeCategoryEnum.SALARIO.getDescription();
            }else if (IncomeCategoryEnum.RENDAEXTRA.getId() == id) {
                categoria = IncomeCategoryEnum.RENDAEXTRA.getDescription();
            } else {
                categoria = IncomeCategoryEnum.OUTRO.getDescription();
            }
        }

        return categoria;
    }

    private String getTipo(int id){
        String tipo="";
        if(TypeEnum.DESPESA.getId() == id){
            tipo =  TypeEnum.DESPESA.getDescription();
        } else if(TypeEnum.RECEITA.getId() == id){
            tipo =  TypeEnum.RECEITA.getDescription();
        }

        return tipo;
    }


}