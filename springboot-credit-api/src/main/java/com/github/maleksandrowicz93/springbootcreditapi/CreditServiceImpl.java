package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Value("${HOST_NAME}")
    private String HOST_NAME;

    @Autowired
    private CreditRepo creditRepo;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public int createCredit(CreditApplicationDto creditApplicationDto) {
        int creditId = createCreditFromApplication(creditApplicationDto).getId();
        ProductDto productDto = creditApplicationDto.getProductDto();
        productDto.setCreditId(creditId);
        log.info("Sending add new product request...");
        restTemplate.postForObject("http://" + HOST_NAME + ":8020/product", productDto, ProductDto.class);
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();
        customerDto.setCreditId(creditId);
        log.info("Sending add new customer request...");
        restTemplate.postForObject("http://" + HOST_NAME + ":8010/customer", customerDto, CustomerDto.class);
        log.info("Returning new credit id...");
        return creditId;
    }

    @Override
    public List<CreditApplicationDto> getCreditReport() {
        List<Credit> credits = getCredits();
        Object[] creditIds = getCreditIds(credits).toArray();
        log.info("Sending request to set required credit ids for customers...");
        restTemplate.postForObject("http://" + HOST_NAME + ":8010/customer/ids", creditIds, Object[].class);
        log.info("Sending request to set required credit ids for products...");
        restTemplate.postForObject("http://" + HOST_NAME + ":8020/product/ids", creditIds, Object[].class);
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
        log.info("Sending request for get customers...");
        ResponseEntity<CustomerDto[]> GetCustomersExchange = restTemplate.exchange(
                "http://" + HOST_NAME + ":8010/customer",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CustomerDto[].class
        );
        CustomerDto[] customerDtos = GetCustomersExchange.getBody();
        return  (customerDtos != null) ? Arrays.asList(customerDtos) : new ArrayList<>();
    }

    private List<ProductDto> getProducts() {
        log.info("Sending request for get products...");
        ResponseEntity<ProductDto[]> GetProductsExchange = restTemplate.exchange(
                "http://" + HOST_NAME + ":8020/product",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ProductDto[].class
        );
        ProductDto[] productDtos = GetProductsExchange.getBody();
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
