package com.github.maleksandrowicz93.springbootcreditapi;

import java.util.ArrayList;
import java.util.List;

public class CreditApplicationValidator {

    public boolean checkIfApplicationIsCorrect(CreditApplicationDto creditApplicationDto) {

        CustomerDto customer = creditApplicationDto.getCustomerDto();
        ProductDto product = creditApplicationDto.getProductDto();
        String pesel = customer.getPesel();
        Integer value = product.getValue();

        if (value == null || value < 100 || pesel == null || (pesel = pesel.trim()).length() != 11 || product.getProductName() == null)
            return false;

        try {
            Long.parseLong(pesel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        List<String> commonStrings = new ArrayList<>();
        commonStrings.add(creditApplicationDto.getCreditName());
        commonStrings.add(customer.getFirstName());
        commonStrings.add(customer.getSurname());

        for (String s : commonStrings) {
            if (s == null || s.trim().length() < 2)
                return false;
            try {
                Integer.parseInt(s);
                return false;
            } catch (Exception ignored) {
            }
        }

        return true;

    }

}
