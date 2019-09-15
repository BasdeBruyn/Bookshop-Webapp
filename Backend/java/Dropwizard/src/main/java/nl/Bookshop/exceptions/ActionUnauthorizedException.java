package nl.Bookshop.exceptions;

import javax.ws.rs.core.Response;

public class ActionUnauthorizedException extends ClientException {

    private final static int statusCode = Response.Status.FORBIDDEN.getStatusCode();

    public ActionUnauthorizedException() {
        super(statusCode, "This action is not allowed on other users by non admins");
    }
}
