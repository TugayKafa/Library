package com.endava.exception.validator;

import com.endava.util.Constant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Map;

public class EgnValidator implements ConstraintValidator<Egn, String> {

    private static final Map<Integer, Integer> positionAndWeight;

    static {
        positionAndWeight = Map.ofEntries(
                Map.entry(1, 2),
                Map.entry(2, 4),
                Map.entry(3, 8),
                Map.entry(4, 5),
                Map.entry(5, 10),
                Map.entry(6, 9),
                Map.entry(7, 7),
                Map.entry(8, 3),
                Map.entry(9, 6)
        );
    }

    @Override
    public boolean isValid(String egn, ConstraintValidatorContext context) {

        if (!isDateAndMonthValid(egn)) {
            return false;
        }
        if (!isLastDigitValid(egn)) {
            return false;
        }
        return true;
    }

    // This method validate last digit of egn
    private boolean isLastDigitValid(String egn) {
        int digit, sum = 0;
        for (int i = 0; i < 9; i++) {
            digit = Integer.parseInt(egn.substring(i, i + 1));
            sum += digit * positionAndWeight.get(i + 1);
        }

        int lastDigit = Integer.parseInt(egn.substring(9));
        return ((sum % Constant.DIVIDER == lastDigit) || ((sum % Constant.DIVIDER == 0 || sum % Constant.DIVIDER == 10) && lastDigit == 0));
    }

    // Validate whether date is in range of current month and month is in range of 1 to 12
    private boolean isDateAndMonthValid(String egn) {
        String yearString = egn.substring(0, 2);
        int year = Integer.parseInt(yearString);
        if ((egn.charAt(2) == '2') || (egn.charAt(2) == '3')) {
            year += 1800;
        } else if ((egn.charAt(2) == '4') || (egn.charAt(2) == '5')) {
            year += 2000;
        } else {
            year += 1900;
        }
        String monthString = egn.substring(2, 4);
        int month = Integer.parseInt(monthString) % 20;
        if (month > Constant.MAX_MONTH || month < Constant.MIN_MONTH) {
            return false;
        }
        String dateString = egn.substring(4, 6);
        int date = Integer.parseInt(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(year, month - 1, date);
        try {
            return (date > calendar.getActualMinimum(Calendar.MONTH) && date <= calendar.get(Calendar.DATE));
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
