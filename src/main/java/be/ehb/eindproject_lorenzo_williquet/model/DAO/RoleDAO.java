package be.ehb.eindproject_lorenzo_williquet.model.DAO;

import be.ehb.eindproject_lorenzo_williquet.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
// Repo voor uitvoeren van db operations
// Extends van de JpaRepository interface
public interface RoleDAO extends JpaRepository<Role,Integer> {

    // Query for het zoeken van een bepaalde rol in de db
    @Query("SELECT r from Role r WHERE r.name = ?1")
    Role findByName(String name);
}
