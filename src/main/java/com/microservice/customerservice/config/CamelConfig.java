
package com.microservice.customerservice.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:customer-created")
                .routeId("customer-created-route")
                .log("Customer created event received: ${body}")
                .to("direct:customer-event-processed");

        from("direct:customer-update")
                .routeId("customer-update-route")
                .log("Customer updated event received: ${body}");

        from("direct:customer-delete")
                .routeId("customer-delete-route")
                .log("Customer deleted event received: ${body}");

        from("direct:customer-event-processed")
                .routeId("customer-event-processed-route")
                .log("Customer event successfully processed: ${body}");
    }

    @Bean
    public ApplicationRunner verifyCamelRoutes(
            CamelContext camelContext) {

        return args -> {
            System.out.println("Registered Camel route IDs:");

            camelContext.getRoutes()
                    .forEach(route ->
                            System.out.println(" - " + route.getId())
                    );
        };
    }
}
