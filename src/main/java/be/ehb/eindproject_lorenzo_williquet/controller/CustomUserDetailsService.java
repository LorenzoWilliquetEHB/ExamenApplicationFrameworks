package be.ehb.eindproject_lorenzo_williquet.controller;

import be.ehb.eindproject_lorenzo_williquet.model.DAO.UserDAO;
import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO dao;

    // Wordt gebruikt door spring security voor authentication uit te voeren
    // Voor het vinden van de user door de email
    // Als de user niet bestaat of gevonden is dan wordt er een UsernameNotFoundException gegooid
    // We retourneren een nieuwe CustomUserDetails object met een user die meegegeven wordt
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = dao.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Gebruiker niet gevonden");
        }
        return new CustomUserDetails(user);
    }

}
