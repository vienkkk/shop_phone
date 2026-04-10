package ra.phonestore.util;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean requireNotEmpty(String input, String fieldName) {
        if (isEmpty(input)) {
            System.out.println(fieldName + " không được để trống!");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    public static boolean requireValidEmail(String email) {
        if (!isValidEmail(email)) {
            System.out.println("Email không hợp lệ!");
            return false;
        }
        return true;
    }

    public static boolean isValidPhone(String phone) {
        return Pattern.matches("^0[0-9]{9}$", phone);
    }

    public static boolean requireValidPhone(String phone) {
        if (!isValidPhone(phone)) {
            System.out.println("Số điện thoại không hợp lệ (phải 10 số, bắt đầu bằng 0)!");
            return false;
        }
        return true;
    }
}