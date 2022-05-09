package ifsp.finances.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "finances")
@JsonIdentityInfo(scope = Finance.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idUser", nullable = false)
    private long idUser;

    @Column(name = "type", nullable = false)
    private String tipo;

    @Column(name = "category", nullable = false)
    private String categoria;

    @Column(name = "description", nullable = false)
    private String descricao;

    @Column(name = "valor", nullable = false)
    private long valor;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}