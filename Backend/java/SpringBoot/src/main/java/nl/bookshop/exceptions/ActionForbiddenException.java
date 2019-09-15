package nl.bookshop.exceptions;

import static nl.bookshop.exceptions.ExceptionStrings.*;

public class ActionForbiddenException extends ForbiddenException {

    public ActionForbiddenException() {
        super(ACTION_NOT_ALLOWED_ON_OTHER_USERS);
    }
}
