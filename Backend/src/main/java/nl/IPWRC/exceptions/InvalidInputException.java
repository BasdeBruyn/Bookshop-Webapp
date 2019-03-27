package nl.IPWRC.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.dropwizard.jackson.Jackson;

import java.util.List;

public class InvalidInputException extends ClientException {
    public InvalidInputException(List<String> errors) {
        super(409, convertToJson(errors));
    }

    private static String convertToJson(List<String> errors){
        try {
            return Jackson.newObjectMapper().writeValueAsString(errors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
