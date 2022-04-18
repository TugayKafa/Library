package com.endava.util;

public class Constants {
    public static final String ISBN_NOT_EXISTS = "ISBN not exists!";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_JPEG = ".jpeg";
    public static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/library-d872d.appspot.com/o/%s?alt=media";
    public static final String POINT_SEPARATOR = ".";
    public static final String FIREBASE_BUCKET_NAME = "library-d872d.appspot.com";
    public static final String FIREBASE_CONTENT_TYPE = "media";
    public static final String EMPTY_COVER_IMAGE = "Cover image cannot be empty";
    public static final String LAST_NAME_COMPOSITION_MESSAGE = "The last name must be capitalized. Last name is composed of letters from the Latin alphabet. '-' is accepted. Not blank space allowed before and after a first name Mononymous(Single word names) names are not accepted.";
    public static final String FIRST_NAME_COMPOSITION_MESSAGE = "The first name must be capitalized. First name is composed of letters from the Latin alphabet. '-' is accepted. Not blank space allowed before and after a first name Mononymous(Single word names) names are not accepted.";
    public static final String REGEX_FOR_NAMES = "^[A-Z](([-][a-zA-Z])?[a-z]){1,251}$";
    public static final String ISBN_10_REGEX = "^[0-9]{9}[0-9X]$";
    public static final String ISBN_13_REGEX = "^[0-9]{13}$";
    public static final String INVALID_ISBN_DEFAULT_MESSAGE = "Invalid ISBN!";
    public static final char ISBN_CLEANUP_MATCHER = '-';
    public static final String INVALID_LANGUAGE_DEFAULT_MESSAGE = "Invalid language";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_ALREADY_DEACTIVATED = "The book already deactivated! Reason: %s. Description: %s Date of deactivation %s";
    public static final String SUCCESSFULLY_DEACTIVATED =
            "Book with %s isbn successfully deactivated. Reason: %s with description: %s Date of deactivation %s";
    public static final String BOOK_ACTIVE_ASSIGNMENT = "Book have active assignments. Cannot be deactivated!";
    public static final String COVER_IMAGE_ALREADY_IN_USE = "Cover image already in use!";
    public static final long MAX_FILE_SIZE = 5242880L;
    public static final String COVER_IMAGE_VALIDATOR_MESSAGE = "Book cover is required and must be a maximum of 5 megabytes.";
    public static final String THE_OLD_IMAGE_HAS_NOT_BEEN_DELETED = "The old image has not been deleted!";
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String EMPTY_STRING = "";
    public static final String PLEASE_PROVIDE_REASON_OF_DEACTIVATION = "Please provide reason of deactivation!";
    public static final String INVALID_REASON_DEFAULT_MESSAGE = "Invalid reason";
    public static final String TIMESTAMP = "timestamp";
    public static final String ERRORS = "errors";
    public static final String STATUS = "status";
    public static final String FAIL_EXPORT_DATA_TO_CSV = "Fail to export data to CSV file";
    public static final String INVALID_YEAR_EXCEPTION_MESSAGE = "Year should be after 1000 year and before present year!";
}
