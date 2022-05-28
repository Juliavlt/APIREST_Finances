package ifsp.finances.repository;
import ifsp.finances.model.Category;
import ifsp.finances.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIdUser(long idUser);

}
