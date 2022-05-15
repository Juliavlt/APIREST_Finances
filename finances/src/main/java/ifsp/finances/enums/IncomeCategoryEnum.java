package ifsp.finances.enums;

import lombok.Getter;

@Getter
public enum IncomeCategoryEnum {
    SALARIO(1,"Salario" ),
    RENDAEXTRA(2,"Renda extra"),
    OUTRO(3,"Outro");

    private final String description;
    private final int id;

    IncomeCategoryEnum(int id, String description) {
        this.description = description;
        this.id = id;

    }
}
