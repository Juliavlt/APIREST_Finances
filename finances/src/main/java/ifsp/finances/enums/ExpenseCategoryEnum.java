package ifsp.finances.enums;

import lombok.Getter;

@Getter
public enum ExpenseCategoryEnum {
    SAUDE(1,"Saúde" ),
    TRANSPORTE(2,"Transporte"),
    RESTAURANTE(3,"Restaurante"),
    EDUCACAO(4,"Educação"),
    OUTRO(5,"Outros");

    private final String description;
    private final int id;

    ExpenseCategoryEnum(int id, String description) {
        this.description = description;
        this.id = id;

    }
}
