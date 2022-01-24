package be.ehb.eindproject_lorenzo_williquet.seeder;

import be.ehb.eindproject_lorenzo_williquet.model.DAO.ProductDAO;
import be.ehb.eindproject_lorenzo_williquet.model.DAO.RoleDAO;
import be.ehb.eindproject_lorenzo_williquet.model.DAO.UserDAO;
import be.ehb.eindproject_lorenzo_williquet.model.entities.Product;
import be.ehb.eindproject_lorenzo_williquet.model.entities.Role;
import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
// Wordt gebruikt om automatisch data op te vullen in de db als de db leeg is
// Wordt uitgevoerd bij het opstarten
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    ProductDAO productDAO;

    @Override
    public void run(String... args) throws Exception {
        loadProductData();
        loadRoleData();
        loadUserData();
    }

    private void loadProductData(){
        if (productDAO.count() == 0){
            Product p1 = new Product("Voeding","Whiskas","Voeding voor katten",6.00);
            Product p2 = new Product("Speelgoed","Tennisballenwerper", "Speelgoed voor honden",2.99);
            Product p3 = new Product("Habitat","Aquarium", "Habitat voor de vissen",99.99);
            Product p4 = new Product("Habitat","Honden hok", "Habitat voor de honden",199.99);
            Product p5 = new Product("Voeding","Vissen eten", "Voeding voor de vissen",199.99);
            productDAO.save(p1);
            productDAO.save(p2);
            productDAO.save(p3);
            productDAO.save(p4);
            productDAO.save(p5);
        }
    }

    private void loadRoleData(){
        if(roleDAO.count() == 0){
            Role r1 = new Role("ADMIN");
            Role r2 = new Role("USER");
            roleDAO.save(r1);
            roleDAO.save(r2);
        }
    }

    private void loadUserData() {
        if (userDAO.count() == 0){
            Role roleAdmin = roleDAO.findByName("ADMIN");

            User admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword("Student1");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.addRole(roleAdmin);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPasswordAdmin = encoder.encode(admin.getPassword());
            admin.setPassword(encodedPasswordAdmin);
            userDAO.save(admin);

            Role roleUser = roleDAO.findByName("USER");

            User user = new User();
            user.setEmail("user@user.com");
            user.setPassword("Student1");
            user.setFirstName("user");
            user.setLastName("user");
            user.addRole(roleUser);

            String encodedPasswordUser = encoder.encode(user.getPassword());
            user.setPassword(encodedPasswordUser);
            userDAO.save(user);
        }
    }
}
