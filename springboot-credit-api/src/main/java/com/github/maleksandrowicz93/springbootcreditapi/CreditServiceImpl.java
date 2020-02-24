package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Autowired
    CreditRepo creditRepo;

    @Override
    public Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto) {
        Credit credit = new Credit(creditApplicationDto.getCreditName());
        creditRepo.save(credit);
        List<Credit> allCredits = creditRepo.findAll();
        allCredits.sort(Comparator.comparing(Credit::getId));
        credit.setId(allCredits.get(allCredits.size()-1).getId());
        return credit;
    }

    @Override
    public List<Credit> getCredits() {
        return creditRepo.findAll();
    }

    @Override
    public List<Integer> getCreditIds(List<Credit> credits) {
        List<Integer> list = new ArrayList<>();
        credits.iterator().forEachRemaining(c -> list.add(c.getId()));
        return list;
    }

    @Override
    public List<CreditApplicationDto> createCreditReport(
            List<Credit> credits,
            List<CustomerDto> customerDtoList,
            List<ProductDto> productDtoList
    ) {
        List<CreditApplicationDto> report = new ArrayList<>();
        credits.sort(Comparator.comparing(Credit::getId));
        customerDtoList.sort(Comparator.comparing(CustomerDto::getCreditId));
        productDtoList.sort(Comparator.comparing(ProductDto::getCreditId));
        for (int i = 0; i < credits.size(); i++) {
            Credit c = credits.get(i);
            CustomerDto customerDto = customerDtoList.get(i);
            ProductDto productDto = productDtoList.get(i);
            CreditApplicationDto entry = new CreditApplicationDto();
            entry.setCreditName(c.getCreditName());
            entry.setCustomerDto(new CustomerDto(customerDto.getFirstName(), customerDto.getSurname(), customerDto.getPesel()));
            entry.setProductDto(new ProductDto(productDto.getProductName(), productDto.getValue()));
            report.add(entry);
        }
        return report;
    }

}
