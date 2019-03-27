package nl.IPWRC.services;

import nl.IPWRC.exceptions.InvalidInputException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    private static final int minPasswordLength = 5;
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String phoneNrRegex = "^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$|^(((\\\\+31|0|0031)6){1}[1-9]{1}[0-9]{7})$";
    private static final int minNameLength = 2;
    private static final String nameRegex = String.format("^[a-zA-Z ]{%d,}", minNameLength);
    public static final String postalCodeRegex = "^[1-9][0-9]{3} ?[A-Z]{2}$";
    public static final String houseNrRegex = "[1-9][0-9]{0,2}[A-Za-z]?";

    public static void validate(Validator validator) throws InvalidInputException {
        List<String> errors = new ArrayList<>();

        validator.validate(errors);

        if (errors.size() > 0) {
            throw new InvalidInputException(errors);
        }
    }

    public static boolean isValidName(String name) {
        return matchesRegex(name, nameRegex);
    }

    public static boolean isValidPassword(String password) {
        return password.length() > minPasswordLength && isNotEmpty(password);
    }

    public static boolean isValidEmail(String email) {
        return matchesRegex(email, emailRegex);
    }

    public static boolean isValidPhoneNr(String phoneNumber) {
        return matchesRegex(phoneNumber, phoneNrRegex);
    }

    public static boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPostalCode(String postalCode) {
        return matchesRegex(postalCode, postalCodeRegex);
    }

    public static boolean isValidHoueNr(String houseNr) {
        return matchesRegex(houseNr, houseNrRegex);
    }

    private static boolean isNotEmpty(String string) {
        return !string.trim().isEmpty();
    }

    private static boolean matchesRegex(String string, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
