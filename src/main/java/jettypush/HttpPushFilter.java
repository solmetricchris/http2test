package jettypush;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

import org.eclipse.jetty.server.Request;


public class HttpPushFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
        chain.doFilter(req, res);
        Request jettyRequest = (Request) req;
        if(jettyRequest.isPushSupported()) {
    		PushBuilder pb = req.getPushBuilder();
    		
    		pb.path("/pictures/logo.jpg")
            .push();
		}		
    }

}
