package ifsp.finances.service;

import ifsp.finances.enums.CategoryEnum;
import ifsp.finances.enums.TypeEnum;
import ifsp.finances.model.Finance;
import ifsp.finances.model.User;
import ifsp.finances.model.dto.ReceitasDespesasResponseDTO;
import ifsp.finances.model.dto.FinanceRequestDTO;
import ifsp.finances.model.dto.FinanceResponseDTO;
import ifsp.finances.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialService{

    @Autowired private FinanceRepository repository;

    public FinanceResponseDTO create(FinanceRequestDTO requestDTO) {

        Finance finance = Finance.builder()
                .idUser(requestDTO.getIdUser())
                .descricao(requestDTO.getDescricao())
                .tipo(this.getTipo(requestDTO.getTipo()))
                .categoria(this.getCategoria(requestDTO.getCategoria()))
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
                .descricao(finance.getDescricao())
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
                .categoria(this.getCategoria(requestDTO.getCategoria()))
                .descricao(requestDTO.getDescricao())
                .valor(requestDTO.getValor())
                .createdAt(finance.getCreatedAt())
                .modifiedAt(LocalDateTime.now()).build());

        return FinanceResponseDTO.builder()
                .id(finance.getId())
                .idUser(finance.getIdUser())
                .tipo(this.getTipo(requestDTO.getTipo()))
                .categoria(this.getCategoria(requestDTO.getCategoria()))
                .descricao(requestDTO.getDescricao())
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

        return buildExpenseIncomeList(accountingList, accountingList.get(0).getId());
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
                .filter(accounting -> accounting.getTipo().equals(type.toString()))
                .mapToLong(Finance::getValor)
                .sum();
    }

    private String getCategoria(int id){
        String categoria="";
        if(CategoryEnum.PAGAMENTO.getId() == 1){
            categoria =  CategoryEnum.PAGAMENTO.getDescription();
        } else if (CategoryEnum.RENDIMENTO.getId() == id){
            categoria = CategoryEnum.RENDIMENTO.getDescription();
        } else if (CategoryEnum.SAUDE.getId() == id){
            categoria = CategoryEnum.SAUDE.getDescription();
        } else if (CategoryEnum.TRANSPORTE.getId() == id) {
            categoria = CategoryEnum.TRANSPORTE.getDescription();
        } else {
            categoria = CategoryEnum.RESTAURANTE.getDescription();
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