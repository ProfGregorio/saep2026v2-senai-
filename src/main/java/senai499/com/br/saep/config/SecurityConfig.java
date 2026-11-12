
package senai499.com.br.saep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ==========================================
    // CRIPTOGRAFIA DE SENHAS
    // ==========================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ==========================================
    // CONFIGURAÇÃO DO SPRING SECURITY
    // ==========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LoginSuccessHandler loginSuccessHandler)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // ==========================================
                        // PÁGINAS PÚBLICAS
                        // ==========================================

                        .requestMatchers(
                                "/",
                                "/index",
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()

                        // ==========================================
                        // ÁREA EXCLUSIVA DO ADMINISTRADOR
                        // ==========================================

                        .requestMatchers(
                                "/dashboard",
                                "/dashboard/**",
                                "/admin/funcionarios",
                                "/admin/funcionarios/**"
                        ).hasRole("ADMIN")

                        // ==========================================
                        // ÁREA DO FUNCIONÁRIO
                        // ADMIN E FUNCIONARIO
                        // ==========================================

                        .requestMatchers(
                                "/funcionario",
                                "/funcionario/**"
                        ).hasAnyRole("ADMIN", "FUNCIONARIO")

                        // ==========================================
                        // GESTÃO DE TUTORES
                        // ==========================================

                        .requestMatchers(
                                "/tutores",
                                "/tutores/**"
                        ).hasAnyRole("ADMIN", "FUNCIONARIO")

                        // ==========================================
                        // GESTÃO DE PETS
                        // ==========================================

                        .requestMatchers(
                                "/pets",
                                "/pets/**"
                        ).hasAnyRole("ADMIN", "FUNCIONARIO")

                        // ==========================================
                        // AGENDAMENTOS E ATENDIMENTOS
                        // ==========================================

                        .requestMatchers(
                                "/agendamentos",
                                "/agendamentos/**",
                                "/atendimentos",
                                "/atendimentos/**"
                        ).hasAnyRole("ADMIN", "FUNCIONARIO")

                        // ==========================================
                        // SERVIÇOS DA CLÍNICA
                        // ==========================================

                        .requestMatchers(
                                "/servicos",
                                "/servicos/**"
                        ).hasAnyRole("ADMIN", "FUNCIONARIO")

                        // ==========================================
                        // PERFIL E ALTERAÇÃO DE SENHA
                        // ==========================================

                        .requestMatchers(
                                "/perfil",
                                "/perfil/**"
                        ).authenticated()

                        // ==========================================
                        // DEMAIS ROTAS
                        // ==========================================

                        .anyRequest().denyAll()
                )

                // ==========================================
                // LOGIN PERSONALIZADO
                // ==========================================

                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .usernameParameter("email")

                        .passwordParameter("senha")

                        .failureUrl("/login?erro")

                        .successHandler(loginSuccessHandler)

                        .permitAll()
                )

                // ==========================================
                // LOGOUT
                // ==========================================

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?sair")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                )

                // ==========================================
                // ACESSO NEGADO
                // ==========================================

                .exceptionHandling(exception -> exception

                        .accessDeniedPage("/acesso-negado")
                );

        return http.build();
    }
}
