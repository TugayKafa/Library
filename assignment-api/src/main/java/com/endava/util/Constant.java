package com.endava.util;

import java.time.LocalDate;

public class Constant {
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String ERRORS = "errors";
    public static final String USER_IS_INACTIVE = "This user is inactive at the moment.";
    public static final String BOOK_UNAVAILABLE = "One or more of the chosen books are out of stock at the moment.";
    public static final String BOOK_ALREADY_ASSIGNED = "Looks like you have already assigned one or more of these books.";
    public static final String MAX_FIVE_BOOKS_ASSIGNED = "You can assign up to 5 books.";
    public static final String BOOK_RECENTLY_ASSIGNED = "At least 14 days need to pass before you can assign the same book.";
    public static final String BOOK_OR_USER_NOT_FOUND = "The specified book and/or user was not found.";
    public static final String UNEXPECTED_EXCEPTION = "Oops, something unexpected happened.";
    public static final String INVALID_ASSIGNMENT = "Invalid Assignment.";
    public static final String ASSIGNMENT_DOESNT_EXIST = "Sorry, there is no such assignment.";
    public static final String MAXIMUM_ASSIGNMENT_PERIOD = "An assignment duration cannot exceed 3 months.";
    public static final String SUCCESSFULLY_EXTENDED_ASSIGNMENT = "Successfully extended assignment by %s day/s.";
    public static final int MAXIMUM_ASSIGNMENTS = 5;
    public static final LocalDate TODAY_DATE = LocalDate.now();
    public static final LocalDate FIFTEEN_DAYS_BEFORE_TODAY_DATE = LocalDate.now().minusDays(15);

}
