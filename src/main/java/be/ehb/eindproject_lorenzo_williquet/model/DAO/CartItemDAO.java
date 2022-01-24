package be.ehb.eindproject_lorenzo_williquet.model.DAO;

import be.ehb.eindproject_lorenzo_williquet.model.entities.CartItem;
import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Repo voor uitvoeren van db operations
// Extends van de JpaRepository interface en CrudRepository
public interface CartItemDAO extends JpaRepository<CartItem,Integer>, CrudRepository<CartItem,Integer> {

    @Query("select c from CartItem c where c.user.id = ?1")
    List<CartItem> findByUser(int id);

    @Query("delete from CartItem c where c.user.id = ?1")
    CartItem deleteCartItemByUserId(int id);
}
