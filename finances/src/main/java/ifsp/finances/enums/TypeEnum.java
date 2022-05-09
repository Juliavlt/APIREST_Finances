package ifsp.finances.enums;

import lombok.Getter;

@Getter
public enum TypeEnum {
    DESPESA(1,"depesa"),
    RECEITA(2,"receita");

    private final String description;
    private final int id;

    TypeEnum(int id, String description) {
        this.description = description;
        this.id = id;
    }

}
