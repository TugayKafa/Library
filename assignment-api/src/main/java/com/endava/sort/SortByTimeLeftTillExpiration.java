package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

public class SortByTimeLeftTillExpiration implements Command {

    @Override
    public void sort(List<PageAssignmentResponse> pageAssignmentResponses, Sort.Direction direction, Function<PageAssignmentResponse, String> function) {
        if (direction.isAscending()) {
            pageAssignmentResponses.sort(this::sortTimeLeftTillExpiration);
        } else if (direction.isDescending()) {
            pageAssignmentResponses.sort((asn2, asn1) -> sortTimeLeftTillExpiration(asn1, asn2));
        }
    }

    @Override
    public Function<PageAssignmentResponse, String> getFunction() {
        return null;
    }

    private int sortTimeLeftTillExpiration(PageAssignmentResponse asn1, PageAssignmentResponse asn2) {
        boolean isAssignment1Digit = Character.isDigit(asn1.getTimeLeftTillExpiration().charAt(0));
        boolean isAssignment2Digit = Character.isDigit(asn2.getTimeLeftTillExpiration().charAt(0));
        if (isAssignment1Digit && isAssignment2Digit) {
            int daysInNumber1 = Integer.parseInt(asn1.getTimeLeftTillExpiration()
                    .substring(0, asn1.getTimeLeftTillExpiration().indexOf(" ")));
            int daysInNumber2 = Integer.parseInt(asn2.getTimeLeftTillExpiration()
                    .substring(0, asn2.getTimeLeftTillExpiration().indexOf(" ")));
            return Integer.compare(daysInNumber1, daysInNumber2);
        }
        return asn2.getTimeLeftTillExpiration().compareTo(asn1.getTimeLeftTillExpiration());
    }

}
