package com.rramalho.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderREST extends RouteBuilder{
	
    @Override
    public void configure() {
        // A first route generates some orders and queue them in DB
//        from("timer:new-order?delay=1s&period={{quickstart.generateOrderPeriod:2s}}")
//            .routeId("generate-order")
//            .bean("orderService", "generateOrder")
//            .to("sql:insert into orders (id, item, amount, description, processed) values " +
//                "(:#${body.id} , :#${body.item}, :#${body.amount}, :#${body.description}, false)?" +
//                "dataSource=dataSource")
//            .log("Inserted new order ${body.id}");
//
//        // A second route polls the DB for new orders and processes them
//        from("sql:select * from orders where processed = false?" +
//            "consumer.onConsume=update orders set processed = true where id = :#id&" +
//            "consumer.delay={{quickstart.processOrderPeriod:5s}}&" +
//            "dataSource=dataSource")
//            .routeId("process-order")
//            .bean("orderService", "rowToOrder")
//            .log("Processed order #id ${body.id} with ${body.amount} copies of the «${body.description}» book");
        
    	onException(java.sql.SQLIntegrityConstraintViolationException.class)
	    	.handled(true)
	    	.setBody(simple("Order id already registered"));
    	
    	rest("/orders").description("Orders service")

	    	.get("/").description("List orders")
				.route().routeId("orders-list")
				.to("sql:select * from orders?" +
						"dataSource=dataSource")
				.endRest()
			
			.get("order/{id}").description("Details of an order by id")
				.route().routeId("order-api")
				.to("sql:select * from orders where id = :#${header.id}?" +
						"dataSource=dataSource&" +
						"outputClass=com.rramalho.rest.Order")
				.endRest()

    		.post("/").type(Order.class).description("Create a new Book")
    			.route().routeId("insert-order")
    			.log("inserting new order ${body}")
    			.to("sql:insert into orders (id, item, amount, description, processed) values " +
    	                "(:#${body.id} , :#${body.item}, :#${body.amount}, :#${body.description}, false)?" +
    	                "dataSource=dataSource")
    			.log("Inserted new order ${body.id}")
    			.endRest()
    			
			.put("/").type(Order.class).description("Update a order")
	    		.route().routeId("update-order")
				.log("Updating order ${body}")
				.to("sql:update orders set "
						+ "item = :#${body.item}, "
						+ "amount = :#${body.amount}, "
						+ "description = :#${body.description}, "
						+ "processed = :#${body.processed} " +
						"where id = :#${body.id}?" +
		                "dataSource=dataSource").endRest()
    			
    		.delete("/{id}").description("Delete a order")
	    		.route().routeId("delete-order")
				.log("deleting order ${header.id}")
				.to("sql:delete from orders where id = :#${header.id}?"+
					"dataSource=dataSource")
				.endRest();
	}
}

//JSON to TEST POST
//{
//	"id": 0,
//	"item": "Inserting",
//	"amount": 2,
//	"description": "Testing book insert",
//	"processed": true
//}
