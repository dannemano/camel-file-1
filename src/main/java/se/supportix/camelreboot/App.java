package se.supportix.camelreboot;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        
        RouteBuilder route1 = new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:inbox?delay=2000").to("log:se.supportix?level=INFO&showAll=true").to("file:outbox");
				
			}
		};
        
		context.addRoutes(route1);
		
		context.start();

		
		System.in.read();
        
        context.stop();
    }
}
