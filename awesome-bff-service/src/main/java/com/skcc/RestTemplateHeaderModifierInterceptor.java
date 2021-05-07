package com.skcc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * filter implementation for RestTemplate
 */
public class RestTemplateHeaderModifierInterceptor
  implements ClientHttpRequestInterceptor {
 
    private static final Logger log = LoggerFactory.getLogger(RestTemplateHeaderModifierInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
      HttpRequest request, 
      byte[] body, 
      ClientHttpRequestExecution execution) throws IOException {
  
        UUID uuid = UUID.randomUUID();
		String txId = String.format("%s-%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), uuid.toString());
        
        request.getHeaders().set("X-TXID", txId);
        log.info(String.format("rt/txid: %s (%s)", txId, request.getURI()));

        ClientHttpResponse response = execution.execute(request, body);
        // response.getHeaders().add("Foo", "bar");
        return response;
    }
}