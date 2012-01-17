package com.khmelyuk.dib;

import com.khmelyuk.dib.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Dispatch servlet to execute API calls.
 * Accepts only GET and POST calls.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiDispatchServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ApiDispatchServlet.class);

    private ApiDispatcher dispatcher;

    @Override
    public void init() throws ServletException {
        super.init();

        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        dispatcher = context.getBean(ApiDispatcher.class);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleCall(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleCall(req, resp);
    }

    /**
     * Handles an API action call.
     *
     * @param request  the servlet request.
     * @param response the servlet response.
     * @throws IOException error to write a response.
     */
    private void handleCall(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            dispatcher.dispatch(request, response);
        }
        catch (IOException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error to handle an api request: " + request.getPathInfo(), e);

            sendInternalError(response);
        }
    }

    /**
     * Send response with information about internal error.
     *
     * @param response the servlet response.
     * @throws IOException error to write a response.
     */
    private void sendInternalError(HttpServletResponse response) throws IOException {
        try {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            writer.write("{status: " + ResponseStatus.InternalError
                    + ", error: 'Unexpected internal error.'}");
        }
        catch (Exception e) {
            response.sendError(500, "unexpected error");
        }

    }
}
