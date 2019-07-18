package nl.Bookshop.exceptions;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ClientExceptionHandler implements ExceptionMapper<ClientException> {

    @Override
    public Response toResponse(ClientException exception) {
        return Response.status(exception.getCode())
                .entity(new ErrorMessage(exception.getCode(), exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
