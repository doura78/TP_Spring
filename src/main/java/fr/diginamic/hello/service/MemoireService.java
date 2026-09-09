package fr.diginamic.hello.service;

import fr.diginamic.hello.entities.Role;
import fr.diginamic.hello.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemoireService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("user")) {
            return new Utilisateur("user", passwordEncoder.encode("1234"), new Role("ROLE_USER"));
        }
        else if (username.equals("admin")) {
            return new Utilisateur("admin", passwordEncoder.encode("5678"), new Role("ROLE_ADMIN"));
        }
        else {
            throw new UsernameNotFoundException("Vos paramèrtes d'identification sont éronnés");
        }
    }
}