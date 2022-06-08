package ifsp.finances.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @OneToMany(targetEntity = Finance.class, cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH}, orphanRemoval=true)
    @JoinColumn(name = "finance_fk", referencedColumnName = "id")
    private List<Finance> despesas;

    @OneToMany(targetEntity = Finance.class, cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH}, orphanRemoval=true)
    @JoinColumn(name = "finance_fk", referencedColumnName = "id")
    private List<Finance> receitas;

    @Column(name = "totalDespesas", nullable = false)
    private double totalDespesas;

    @Column(name = "totalReceitas", nullable = false)
    private double totalReceitas;

    @Column(name = "total", nullable = false)
    private double total;
}