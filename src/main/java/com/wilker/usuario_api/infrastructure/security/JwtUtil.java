package com.wilker.usuario_api.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {

    // Chave secreta usada para assinar e verificar tokens JWT
    @Value("${chave.secreta}")
    private String secretKey;

    // Converte a chave em Base64 para um objeto SecretKey adequado ao algoritmo HMAC
    private SecretKey getSecretKey(){
        byte[] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    // Gera um token JWT com o nome de usuário e validade de 1 hora
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Define o nome de usuário como o "subject" do token
                .setIssuedAt(new Date()) // Data/hora em que o token foi emitido
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira em 1 hora
                .signWith(getSecretKey()) // Assina o token com a chave secreta
                .compact(); // Finaliza e retorna o token JWT
    }

    // Extrai as claims do token JWT (payload com informações do usuário e metadata)
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey()) // Usa a chave secreta para validar a assinatura do token
                .build()
                .parseClaimsJws(token) // Analisa e valida o token JWT
                .getBody(); // Retorna as claims (informações do token)
    }

    // Extrai o nome de usuário (subject) do token JWT
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Verifica se o token JWT está expirado
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Valida o token JWT verificando se o username bate e se o token não está expirado
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}


