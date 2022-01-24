package be.ehb.eindproject_lorenzo_williquet.controller;

import be.ehb.eindproject_lorenzo_williquet.model.entities.Role;
import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// Deze klasse representeert een geverifieerde user
// Deze klasse gebruikt ook een interface genaamd UserDetails
public class CustomUserDetails implements UserDetails{

    private User user;

    // Constructor
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    // De password is het password van de user
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    // De username is het emailadres van de user
    @Override
    public String getUsername() {
        return user.getEmail();
    }
    // Account is niet expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // Account is niet gelocked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // Credentials zijn niet expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // Is geactiveerd
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return this.user;
    }
}
