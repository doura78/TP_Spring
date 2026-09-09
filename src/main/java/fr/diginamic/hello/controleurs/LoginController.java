package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.LoginRequestDto;
import fr.diginamic.hello.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto req) throws Exception {

        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        return jwtUtil.generateToken(req.getUsername());
    }
}

