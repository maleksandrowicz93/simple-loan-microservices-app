package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Value("${HOST_NAME}")
    private String HOST_NAME;

    @Autowired
    private CreditRepo creditRepo;
    @Autowired
    private RestService restService;


    @Override
    public int createCredit(CreditApplicationDto creditApplicationDto) {
        int creditId = createCreditFromApplication(creditApplicationDto).getId();
        ProductDto productDto = creditApplicationDto.getProductDto();
        productDto.setCreditId(creditId);
        String productUrl = "http://" + HOST_NAME + ":8020/product";
        log.info("Sending add new product request...");
        restService.post(productUrl, productDto);
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();
        customerDto.setCreditId(creditId);
        String customerUrl = "http://" + HOST_NAME + ":8010/customer";
        log.info("Sending add new customer request...");
        restService.post(customerUrl, customerDto);
        log.info("Returning new credit id...");
        return creditId;
    }

    @Override
    public List<CreditApplicationDto> getCreditReport() {
        List<Credit> credits = getCredits();
        Object[] creditIds = getCreditIds(credits).toArray();
        String customerUrl = "http://" + HOST_NAME + ":8010/customer/ids";
        log.info("Sending request to set required credit ids for customers...");
        restService.post(customerUrl, creditIds);
        String productUrl = "http://" + HOST_NAME + ":8020/product/ids";
        log.info("Sending request to set required credit ids for products...");
        restService.post(productUrl, creditIds);
        List<CreditApplicationDto> creditReport = new ArrayList<>();
        if (!credits.isEmpty()) {
            List<CustomerDto> customerDtoList = getCustomers();
            List<ProductDto> productDtoList = getProducts();
            creditReport.addAll(createCreditReport(credits, customerDtoList, productDtoList));
        }
        return creditReport;
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

    private List<CustomerDto> getCustomers() {
        String customerUrl = "http://" + HOST_NAME + ":8010/customer";
        log.info("Sending request for get customers...");
        CustomerDto[] customerDtos = (CustomerDto[]) restService.getResponseBody(customerUrl, CustomerDto[].class);
        return  (customerDtos != null) ? Arrays.asList(customerDtos) : new ArrayList<>();
    }

    private List<ProductDto> getProducts() {
        String productUrl = "http://" + HOST_NAME + ":8020/product";
        log.info("Sending request for get products...");
        ProductDto[] productDtos = (ProductDto[]) restService.getResponseBody(productUrl, ProductDto[].class);
        return  (productDtos != null) ? Arrays.asList(productDtos) : new ArrayList<>();
    }

    private List<CreditApplicationDto> createCreditReport(
            List<Credit> credits,
            List<CustomerDto> customerDtoList,
            List<ProductDto> productDtoList
    ) {
        log.info("Preparing credit report...");
        List<CreditApplicationDto> report = new ArrayList<>();
        credits.sort(Comparator.comparing(Credit::getId));
        customerDtoList.sort(Comparator.comparing(CustomerDto::getCreditId));
        productDtoList.sort(Comparator.comparing(ProductDto::getCreditId));
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
