package se.supportix.camelreboot;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.supportix.camelreboot.beans.FixerBean;
import se.supportix.camelreboot.messages.ABeanMessage;

/**
 * Hello world!
 * 
 */
public class App2 {
	private static final Logger logger = LoggerFactory.getLogger(App2.class);

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();

		ProducerTemplate t = context.createProducerTemplate();

		RouteBuilder routexml = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:objectinput").bean(FixerBean.class).marshal().xstream().to("stream:out");
			}
		};

		RouteBuilder route2 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:testing")
						.to("log:se.supportix?level=INFO&showAll=true")
						.transform().simple("Yo!");

			}
		};

		context.addRoutes(route2);
		context.addRoutes(routexml);

		context.start();
		
		ABeanMessage message = new ABeanMessage();
		message.setId(434);
		message.setNumber(545);
		message.setMessage("Detta kommer vara borta!");
		t.sendBody("direct:objectinput",message);
		
		System.in.read();

		context.stop();
	}
}
