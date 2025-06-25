package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.CustomerRequest;
import com.project.ibtss.dto.response.CustomerResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Gender;
import com.project.ibtss.enums.Role;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.CustomerMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.CustomerRepository;
import com.project.ibtss.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account = accountRepository.findById(account.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (account.getRole() == Role.USER) {
            Customer customer = customerRepository.findByAccountId(account.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            // Update fields
            customer.setDob(request.getDateOfBirth());
            customer.setAddress(request.getAddress());
            if (request.getGender().equals(Gender.FEMALE))
                customer.setGender(Gender.FEMALE);
            else
                customer.setGender(Gender.MALE);

            // Save and map to response
            return customerMapper.toCustomer(customerRepository.save(customer));
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public CustomerResponse getCustomer() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account = accountRepository.findById(account.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (account.getRole() == Role.USER) {
            return customerMapper.toCustomer(customerRepository.findByAccountId(account.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
}