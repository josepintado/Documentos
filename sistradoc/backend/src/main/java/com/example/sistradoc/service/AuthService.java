package com.example.sistradoc.service;

import com.example.sistradoc.dto.AuthRequest;
import com.example.sistradoc.model.Usuario;
import com.example.sistradoc.model.enums.UsuarioRol;
import com.example.sistradoc.repository.UsuarioRepository;
import com.example.sistradoc.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    public Usuario register(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // By default, new users are USUARIO_REGISTRADOR
        if (usuario.getRol() == null) {
            usuario.setRol(UsuarioRol.USUARIO_REGISTRADOR);
        }
        return usuarioRepository.save(usuario);
    }
}
