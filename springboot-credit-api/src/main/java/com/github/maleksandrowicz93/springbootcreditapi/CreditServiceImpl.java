package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Value("${CUSTOMER_API}")
    private String CUSTOMER_API;
    @Value("${PRODUCT_API}")
    private String PRODUCT_API;

    @Autowired
    private CreditRepo creditRepo;
    @Autowired
    private RestService restService;
    @Autowired
    private ApiDataGetter apiDataGetter;

    @Override
    public int createCredit(CreditApplicationDto creditApplicationDto) {

        if(!checkIfApplicationIsCorrect(creditApplicationDto)) {
            log.info("Credit application is incorrect!");
            return 0;
        }

        Integer creditId = createCreditFromApplication(creditApplicationDto).getId();
        ProductDto productDto = creditApplicationDto.getProductDto();
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();

        String productUrl = "http://" + PRODUCT_API + ":8020/product";
        String productIdUrl = productUrl.concat("/creditId");
        log.info("Sending credit id for new product request...");
        restService.post(productIdUrl, creditId);
        log.info("Sending add new product request...");
        restService.post(productUrl, productDto);

        String customerUrl = "http://" + CUSTOMER_API + ":8010/customer";
        String customerIdUrl = customerUrl.concat("/creditId");
        log.info("Sending credit id for new customer request...");
        restService.post(customerIdUrl, creditId);
        log.info("Sending add new customer request...");
        restService.post(customerUrl, customerDto);

        log.info("Returning new credit id...");
        return creditId;

    }

    @Override
    public List<CreditApplicationDto> getCreditReport() {

        List<Credit> credits = getCredits();
        Object[] creditIds = getCreditIds(credits).toArray();
        String customerUrl = "http://" + CUSTOMER_API + ":8010/customer/ids";
        log.info("Sending request to set required credit ids for customers...");
        restService.post(customerUrl, creditIds);
        String productUrl = "http://" + PRODUCT_API + ":8020/product/ids";
        log.info("Sending request to set required credit ids for products...");
        restService.post(productUrl, creditIds);

        List<CreditApplicationDto> creditReport = new ArrayList<>();
        if (!credits.isEmpty()) {
            List<CustomerDto> customerDtoList = apiDataGetter.getCustomers();
            List<ProductDto> productDtoList = apiDataGetter.getProducts();
            creditReport.addAll(createCreditReport(credits, customerDtoList, productDtoList));
        }
        return creditReport;
    }

    private boolean checkIfApplicationIsCorrect(CreditApplicationDto creditApplicationDto) {

        trimApplicationValues(creditApplicationDto);
        CustomerDto customer = creditApplicationDto.getCustomerDto();
        ProductDto product = creditApplicationDto.getProductDto();
        String pesel = customer.getPesel();

        if (product.getValue() < 100 || pesel.length() != 11)
            return false;

        try {
            Integer.parseInt(pesel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        List<String> commonStrings = new ArrayList<>();
        commonStrings.add(creditApplicationDto.getCreditName());
        commonStrings.add(customer.getFirstName());
        commonStrings.add(customer.getSurname());

        for (String s : commonStrings) {
            if (s == null || s.length() < 2)
                return false;
            try {
                Integer.parseInt(s);
                return false;
            } catch (Exception ignored) {
            }
        }

        return true;

    }

    private void trimApplicationValues(CreditApplicationDto creditApplicationDto) {
        creditApplicationDto.setCreditName(creditApplicationDto.getCreditName().trim());
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();
        customerDto.setFirstName(customerDto.getFirstName().trim());
        customerDto.setSurname(customerDto.getSurname().trim());
        customerDto.setPesel(customerDto.getPesel().trim());
        creditApplicationDto.setCustomerDto(customerDto);
    }

    private Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto) {
        log.info("Getting credit name from credit application...");
        Credit credit = new Credit(creditApplicationDto.getCreditName());
        log.info("Adding new credit...");
        creditRepo.save(credit);
        log.info("Getting new credit id...");
        List<Credit> allCredits = creditRepo.findAll();
        allCredits.sort(Comparator.comparing(Credit::getId));
        credit.setId(allCredits.get(allCredits.size()-1).getId());
        return credit;
    }

    private List<Credit> getCredits() {
        log.info("Getting credits...");
        return creditRepo.findAll();
    }

    private List<Integer> getCreditIds(List<Credit> credits) {
        log.info("Getting ids from credits...");
        List<Integer> list = new ArrayList<>();
        credits.iterator().forEachRemaining(c -> list.add(c.getId()));
        return list;
    }

    private List<CreditApplicationDto> createCreditReport(
            List<Credit> credits,
            List<CustomerDto> customerDtoList,
            List<ProductDto> productDtoList
    ) {
        log.info("Preparing credit report...");
        List<CreditApplicationDto> report = new ArrayList<>();
        credits.sort(Comparator.comparing(Credit::getId));
        for (int i = 0; i < credits.size(); i++) {
            log.info("Adding credit report entry...");
            Credit c = credits.get(i);
            CustomerDto customerDto = customerDtoList.get(i);
            ProductDto productDto = productDtoList.get(i);
            CreditApplicationDto entry = new CreditApplicationDto();
            entry.setCreditName(c.getCreditName());
            entry.setCustomerDto(new CustomerDto(customerDto.getFirstName(), customerDto.getSurname(), customerDto.getPesel()));
            entry.setProductDto(new ProductDto(productDto.getProductName(), productDto.getValue()));
            report.add(entry);
        }
        log.info("Returning credit report...");
        return report;
    }

}
