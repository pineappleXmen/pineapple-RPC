
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HTTPTransportServer implements TransportServer{
    private RequestHandler handler;
    private Server server;
    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.handler=requestHandler;
        this.server = new Server(port);
        ServletContextHandler slthandler = new ServletContextHandler();
        server.setHandler(slthandler);
        ServletHolder servletHolder = new ServletHolder(new RequestServlet());
        slthandler.addServlet(servletHolder,"/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
           log.error(e.getMessage(),e);
        }
    }
    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();
            if(handler!=null){
                handler.onRequest(in,out);
            }
            out.flush();
        }
    }
}
