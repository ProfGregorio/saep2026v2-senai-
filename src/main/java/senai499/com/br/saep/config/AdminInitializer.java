
package senai499.com.br.saep.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import senai499.com.br.saep.model.PerfilUsuario;
import senai499.com.br.saep.model.UsuariosModel;
import senai499.com.br.saep.repository.UsuariosRepository;
import senai499.com.br.saep.service.UsuariosService;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner criarAdministrador(
            UsuariosRepository usuariosRepository,
            UsuariosService usuariosService) {

        return args -> {

            String email = "admin@gmail.com";

            if (usuariosRepository.existsByEmailIgnoreCase(email)) {
                return;
            }

            UsuariosModel admin = new UsuariosModel();

            admin.setEmail(email);
            admin.setSenha("123456789");
            admin.setPerfil(PerfilUsuario.ADMIN);
            admin.setAtivo(true);

            usuariosService.cadastrar(admin);

            System.out.println(
                    "Administrador inicial cadastrado."
            );
        };
    }
}
