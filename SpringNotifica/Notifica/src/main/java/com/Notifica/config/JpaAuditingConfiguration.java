package com.Notifica.config;

import com.Notifica.service.AuditorAwareImpl; // Vamos criar este serviço a seguir
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// Habilita a auditoria do JPA e informa qual bean é o nosso provedor de auditoria
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // Retorna a nossa implementação customizada que buscará o usuário do token
        return new AuditorAwareImpl();
    }
}