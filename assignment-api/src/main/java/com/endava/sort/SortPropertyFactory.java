package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import com.endava.dto.author.AuthorResponse;

import java.util.HashMap;
import java.util.Map;

import static com.endava.constant.Constant.SORT_BY_ASSIGNMENT_DATE;
import static com.endava.constant.Constant.SORT_BY_AUTHOR_FIRST_NAME;
import static com.endava.constant.Constant.SORT_BY_AUTHOR_LAST_NAME;
import static com.endava.constant.Constant.SORT_BY_EMAIL;
import static com.endava.constant.Constant.SORT_BY_FULL_NAME;
import static com.endava.constant.Constant.SORT_BY_RETURN_DATE;
import static com.endava.constant.Constant.SORT_BY_TIME_LEFT_TILL_EXPIRATION;
import static com.endava.constant.Constant.SORT_BY_TITLE;

public class SortPropertyFactory implements SortProperty {

    private final Map<String, Command> commands = new HashMap<>();

    {
        commands.put(SORT_BY_RETURN_DATE, new SortByReturnDate(PageAssignmentResponse::getReturnDate));
        commands.put(SORT_BY_ASSIGNMENT_DATE, new SortByAssignmentDate(PageAssignmentResponse::getAssignmentDate));
        commands.put(SORT_BY_TIME_LEFT_TILL_EXPIRATION, new SortByTimeLeftTillExpiration());
        commands.put(SORT_BY_EMAIL, new SortByEmail(a -> a.getUserDetails().getEmail()));
        commands.put(SORT_BY_FULL_NAME, new SortByFullName(a -> a.getUserDetails().getFullName()));
        commands.put(SORT_BY_TITLE, new SortByTitle());
        commands.put(SORT_BY_AUTHOR_FIRST_NAME, new SortByAuthorName(AuthorResponse::getFirstName));
        commands.put(SORT_BY_AUTHOR_LAST_NAME, new SortByAuthorName(AuthorResponse::getLastName));
    }

    @Override
    public Command getSortCommand(String property) {
        return commands.get(property);
    }
}

