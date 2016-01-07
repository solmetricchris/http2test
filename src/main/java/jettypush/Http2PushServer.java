package jettypush;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Http2PushServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		

		String s = "c:/Users/chris/workspace/Cobblestone/Annadel/Web/Orchard/";

		ServletContextHandler ctx = new ServletContextHandler();
		ctx.setContextPath("/*");
		ctx.addFilter(HttpPushFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));

		DefaultServlet defaultServlet = new DefaultServlet();
		ServletHolder defServletHolder = new ServletHolder("default",
				defaultServlet);
		defServletHolder.setInitParameter("resourceBase", s);

		ctx.addServlet(defServletHolder, "/*");

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { ctx });

		server.setHandler(contexts);

		HttpConfiguration config = new HttpConfiguration();
		// HTTP/1.1 support.
		HttpConnectionFactory http1 = new HttpConnectionFactory(config);

		// HTTP/2 cleartext support.
		HTTP2CServerConnectionFactory http2c = new HTTP2CServerConnectionFactory(config);

		ServerConnector connector = new ServerConnector(server,http1,  http2c);
		connector.setPort(8090);
		server.setConnectors(new Connector[] { connector });

		
		NCSARequestLog requestLog = new NCSARequestLog("jetty.log");
		requestLog.setAppend(true);
		requestLog.setExtended(false);
		requestLog.setLogTimeZone("GMT");

		server.setRequestLog(requestLog);

		server.start();
		System.out.println(server.dump());
		server.join();

	}
}
