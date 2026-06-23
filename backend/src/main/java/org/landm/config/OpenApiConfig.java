package org.landm.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shipmentTrackingOpenApi() {

        Info info = new Info()
                .title("Shipment Tracking API")
                .version("1.0.0")
                .description("REST API za upravljanje i pracenje posiljki - kreiranje, status, istorija, filteri, " +
                        "import iz CSV/XLSX.")
                .contact(new Contact()
                        .name("Dimitrije Mitic")
                        .email("dimitrijemitic112@gmail.com"));

        return new OpenAPI().info(info);
    }
}
