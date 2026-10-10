package com.microservice.customerservice.camel;

import org.apache.camel.builder.RouteBuilder;

public class CustomerEventRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:customer-created")
                .routeId("customer-created-route")
                .log("Customer created event received : ${body}")
                .to("direct:customer-event-processed");

        from("direct:customer-update")
                .routeId("customer-update-route")
                .log("Customer updated event received : ${body}");

        from("direct:customer-delete")
                .routeId("customer-delete-route")
                .log("Customer deleted event received : ${body}");

        from("direct:customer-event-processed")
                .routeId("customer-event-processed-route")
                .log("Customer event successfully processed");
    }
}
