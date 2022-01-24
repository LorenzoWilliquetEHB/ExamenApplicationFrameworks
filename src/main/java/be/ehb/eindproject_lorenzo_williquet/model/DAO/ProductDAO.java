package be.ehb.eindproject_lorenzo_williquet.model.DAO;

import be.ehb.eindproject_lorenzo_williquet.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
// Repo voor uitvoeren van db operations
// Extends van de JpaRepository interface en CrudRepository
public interface ProductDAO extends JpaRepository<Product,Integer>, CrudRepository<Product,Integer> {

    // Query voor het filteren op een category
    @Query("select p from Product p where p.category = ?1")
    List<Product> findProductByCategory(String category);

    @Query("select pr from Product pr where pr.productName = ?1")
    Product findProductByProductName(String productName);

    @Query("select pr from Product pr where pr.id = ?1")
    Product findProductById(int id);
}
