package ifsp.finances.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    SAUDE(1,"saude" ),
    TRANSPORTE(2,"transporte"),
    RESTAURANTE(3,"restaurante"),
    PAGAMENTO(4,"pagamento"),
    RENDIMENTO(5,"rendimento");

    private final String description;
    private final int id;

    CategoryEnum(int id, String description) {
        this.description = description;
        this.id = id;

    }
}
