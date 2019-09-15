package nl.bookshop.validators;

public final class ValidationStrings {
    //custom messages for existing validators
    public static final String NEGATIVE_TOTAL_PRICE = "Total price can't be negative.";
    public static final String NEGATIVE_PRICE = "Price can't be negative.";

    //regex strings for custom validators
    public static final String HOUSE_NR_REGEX = "^([1-9][0-9]{0,2} ?[A-Za-z]?)$";
    public static final String NAME_REGEX = "[a-zA-Z][a-zA-Z \\-.']*";
    public static final String PHONE_NR_REGEX = "^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|" +
            "((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$|^(((\\\\+31|0|0031)6){1}[1-9]{1}[0-9]{7})$";
    public static final String POSTAL_CODE_REGEX = "^([1-9][0-9]{3} ?[A-Za-z]{2})$";

    //custom validator messages
    public static final String ITEM_DOES_NOT_EXIST = "Item doesn't exist.";
    public static final String USER_DOES_NOT_EXIST = "User doesn't exist.";
    public static final String INVALID_POSTAL_CODE = "Invalid postal code.";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number.";
    public static final String INVALID_NAME = "Invalid name.";
    public static final String INVALID_HOUSE_NUMBER = "Invalid house number.";
}
