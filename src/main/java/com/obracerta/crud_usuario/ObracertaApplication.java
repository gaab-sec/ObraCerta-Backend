package com.obracerta.crud_usuario; // (Mantenha o seu package original)

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// --- ADICIONE ESSES IMPORTS ---
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
// ------------------------------

@SpringBootApplication
public class ObracertaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObracertaApplication.class, args);
	}

    // --- ADICIONE ESTE BLOCO MÁGICO ---
    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig() {
        return context -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            // Isso diz: "Pode enviar o cookie mesmo se o site for diferente (Vercel -> Render)"
            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
            context.setCookieProcessor(cookieProcessor);
        };
    }
    // ----------------------------------
}