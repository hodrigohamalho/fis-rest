package com.rramalho.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RestSetup extends RouteBuilder{

	@Value("${apidoc.host}")
	private String apiDocHost;

	@Value("${apidoc.port}")
	private String apiDocPort;

	@Value("${apiContext}")
	private String apiContext;

	@Bean
	ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(
				new CamelHttpTransportServlet(), apiContext+"/*");
		servlet.setName("CamelServlet");
		return servlet;
	}

	@Override
	public void configure() {
		restConfiguration()
			.contextPath(apiContext).apiContextPath("/api-doc")
			.host("")
			.port(apiDocPort)	
			.apiProperty("api.title", "Order REST API")
			.apiProperty("api.version", "1.0")
			.apiProperty("cors", "true")
			.apiContextRouteId("doc-api")
			.component("servlet")
			.enableCORS(true)
			.bindingMode(RestBindingMode.json);
	}
	
}
