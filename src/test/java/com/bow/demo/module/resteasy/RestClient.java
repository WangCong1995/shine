package com.bow.demo.module.resteasy;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;


/**
 * http://blog.csdn.net/chs007chs/article/details/55099656
 * 还有一种生成代理然后调用的方式
 * @author vv
 * @since 2018/3/5.
 */
public class RestClient {

	public void demo() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(20);
		connectionManager.setDefaultMaxPerRoute(20);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).build();
		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
					public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
						HeaderElementIterator it = new BasicHeaderElementIterator(
								response.headerIterator(HTTP.CONN_KEEP_ALIVE));
						while (it.hasNext()) {
							HeaderElement he = it.nextElement();
							String param = he.getName();
							String value = he.getValue();
							if (value != null && param.equalsIgnoreCase("timeout")) {
								return Long.parseLong(value) * 1000;
							}
						}
						return 30 * 1000;
					}
				}).setDefaultRequestConfig(requestConfig).setDefaultSocketConfig(socketConfig).build();

		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
        ResteasyClient client =  new ResteasyClientBuilder().httpEngine(engine).build();
        client.register(ClientTraceFilter.class);
        ResteasyWebTarget target = client.target("http://127.0.0.1:8080/cal/3.json");
        Response response = target.request().get();
        Integer value = response.readEntity(Integer.class);
        System.out.println(value);
        response.close();  // You should close connections!
	}

    public static void main(String[] args) throws IOException {
        RestClient demo = new RestClient();
        demo.demo();
    }
}
