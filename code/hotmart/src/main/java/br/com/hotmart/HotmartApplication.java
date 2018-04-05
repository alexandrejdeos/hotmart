package br.com.hotmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


@Configuration // Define a classe como classe de configuração
@EnableSwagger //Habilita o Swagger
@SpringBootApplication
public class HotmartApplication {
	
    @Autowired
    private SpringSwaggerConfig swaggerConfig;

	public static void main(String[] args) {
		SpringApplication.run(HotmartApplication.class, args);
	}
	
	@Bean
    public SwaggerSpringMvcPlugin groupOnePlugin() {
       return new SwaggerSpringMvcPlugin(swaggerConfig)
           .includePatterns("/file.*?", "/login.*?")
           .swaggerGroup("admin");
    }
	
}
