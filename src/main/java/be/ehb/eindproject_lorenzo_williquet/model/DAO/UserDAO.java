package be.ehb.eindproject_lorenzo_williquet.model.DAO;

import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// Repo voor uitvoeren van db operations
// Extends van de JpaRepository interface
public interface UserDAO extends JpaRepository<User,Integer> {

    // Het vinden van een user door de email
    // ?1 is de eerste parameter en die wordt dan genomen
    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);
}
