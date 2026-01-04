package com.kahve.service;

import com.kahve.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final String issuer;
    private final Long accessMinutes;

    public TokenService(JwtEncoder encoder,
                        @Value("${app.jwt.issuer}") String issuer,
                        @Value("${app.jwt.access-token-minutes}") Long accessMinutes) {
        this.encoder = encoder;
        this.issuer = issuer;
        this.accessMinutes = accessMinutes;
    }


    public TokenResult createAccessToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessMinutes * 60);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(exp)
                .subject(user.getId())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream().toList())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        return new TokenResult(token, exp.getEpochSecond() - now.getEpochSecond());
    }

    public record TokenResult(String token, long expiresInSeconds) {}
}
