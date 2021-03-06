package agency.illiaderhun.com.github.controller.filters;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter before order creating.
 * It checks two main fields: name and description by patterns.
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class AddOrderFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AddOrderFilter.class.getSimpleName());

    private Pattern namePattern;
    private Pattern descriptionPattern;
    private Matcher matcher;

    private static final String NAME_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9 ]+$";
    private static final String DESCRIPTION_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9.\\- ]+$";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init param start");

        namePattern = Pattern.compile(NAME_PATTERN);
        descriptionPattern = Pattern.compile(DESCRIPTION_PATTERN);

        LOGGER.info("init param end");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("doFilter start");
        try {
            String theCommand = request.getParameter("command");
            LOGGER.info("LoginFilter doFilter, the commend: " + theCommand);
            if (theCommand == null){
                theCommand = "INDEX";
            }

            // route to the appropriate method
            switch (theCommand){
                case "ADD_ORDER":{
                    if(checkFields(request, response)){
                        chain.doFilter(request, response);
                    } else {
                        request.setAttribute("err", "regExp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/userPage.jsp");
                        dispatcher.forward(request, response);
                    }
                };break;
                default:{
                    chain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.warning("caught Exception");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessDenied.jsp");
            dispatcher.forward(request, response);
            throw new ServletException(e);
        }
    }

    /**
     *
     * @param request standart request from user with "name" and "description"
     * @param response standart HttpServlet response
     * @return true in case all fields are OK
     */
    private boolean checkFields(ServletRequest request, ServletResponse response) {
        boolean result = false;

        String deviceName = request.getParameter("deviceName").trim();
        String description = request.getParameter("description").trim();

        if (checkName(deviceName) && checkDescription(description)){
            result = true;
        }

        return result;
    }

    /**
     * Check description by descriptionPattern
     *
     * @param description text description from user
     * @return true in case description is OK
     */
    private boolean checkDescription(String description) {
        matcher = descriptionPattern.matcher(description);

        return matcher.matches();
    }

    /**
     * Check name by namePattern
     *
     * @param deviceName name from user
     * @return true in case deviceName is OK
     */
    private boolean checkName(final String deviceName) {
        matcher = namePattern.matcher(deviceName);

        return matcher.matches();
    }

    @Override
    public void destroy() {

    }
}
