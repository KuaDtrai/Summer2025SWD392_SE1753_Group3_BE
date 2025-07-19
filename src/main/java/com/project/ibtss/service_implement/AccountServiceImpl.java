package com.project.ibtss.service_implement;

import com.project.ibtss.dto.response.DriverResponse;
import com.project.ibtss.dto.request.*;
import com.project.ibtss.dto.response.AccountDetailResponse;
import com.project.ibtss.dto.response.AccountManageResponse;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Gender;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.AccountMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Staff;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.AccountService;
import com.project.ibtss.service.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AccountMapper mapper;
    private final TripRepository tripRepository;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;


    private Account getAccount(){
        return (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountRepository repository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, JWTService jwtService, AccountMapper mapper, TripRepository tripRepository, StaffRepository staffRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.tripRepository = tripRepository;
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
    }

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

//    private String generateToken(Account account) {
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(account.getPhone())
//                .issuer("aia.com")
//                .issueTime(new Date())
//                .expirationTime(new Date(
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
//                ))
//                .claim("scope", buildScope(account))
//                .build();
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//
//        JWSObject jwsObject = new JWSObject(header, payload);
//
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            System.out.println(jwsObject.serialize());
//            return jwsObject.serialize();
//        } catch (JOSEException e) {
//            log.warn("Can't create JWT token!", e);
//            throw new RuntimeException(e);
//        }
//    }

    private String buildScope(Account account) {
        Role role = account.getRole();
        return (role != null) ? role.getRoleName() : " ";
    }

    @Override
    public AccountResponse getAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        return mapper.toAccountResponse(account);
    }

    @Override
    public List<AccountManageResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountManageResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            AccountManageResponse accountManageResponse = mapper.toAccountManageResponse(account);
            if(accountManageResponse.getRole().equals(Role.STAFF)){
                Staff staff = staffRepository.findByAccountId(accountManageResponse.getId()).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
                accountManageResponse.setPosition(staff.getPosition());
            }
            accountResponses.add(accountManageResponse);
        }
        return accountResponses;
    }

    @Override
    public AccountManageResponse addAccount(AccountRequest accountRequest) {
        if (accountRepository.findByPhone(accountRequest.getPhone()) != null) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        Account account = Account.builder()
                .phone(accountRequest.getPhone())
                .fullName(accountRequest.getFullName())
                .passwordHash(passwordEncoder.encode(accountRequest.getPassword()))
                .gender(Gender.valueOf(accountRequest.getGender().toUpperCase()))
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .build();
        account = accountRepository.save(account);
        if(accountRequest.getRole().equals(Role.STAFF.getRoleName())){

            account.setRole(Role.STAFF);
            accountRepository.save(account);

            Staff staff = Staff.builder()
                    .account(account)
                    .hiredDate(LocalDate.now())
                    .position(Position.valueOf(accountRequest.getPosition().toUpperCase()))
                    .build();
            staff = staffRepository.save(staff);

            AccountManageResponse accountManageResponse = mapper.toAccountManageResponse(account);
            accountManageResponse.setPosition(staff.getPosition());

            return accountManageResponse;
        } else{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(accountRequest.getDateOfBirth(), formatter);

            account.setRole(Role.CUSTOMER);
            accountRepository.save(account);

            Customer customer = Customer.builder()
                    .account(account)
                    .address(accountRequest.getAddress())
                    .dob(localDate)
                    .build();
            customer = customerRepository.save(customer);

            AccountManageResponse accountManageResponse = mapper.toAccountManageResponse(account);
            accountManageResponse.setAddress(customer.getAddress());
            accountManageResponse.setDateOfBirth(customer.getDob());

            return accountManageResponse;
        }
    }

    @Override
    public AccountResponse updateRole(Integer id, Role role) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return mapper.toAccountResponse(null);
        }
        account.setRole(role);
        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        Account account = accountRepository.findById(getAccount().getId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        account.setPasswordHash(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        account = accountRepository.save(account);

        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public AccountManageResponse updateAccount(Integer id,AccountRequest accountRequest) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new  AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Account accountForValid = accountRepository.findByPhone(accountRequest.getPhone());

        if (accountForValid != null && !accountForValid.getPhone().equals(account.getPhone())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        accountForValid = accountRepository.findByEmail(accountRequest.getEmail()).orElse(null);

        if(accountForValid != null && !accountForValid.getPhone().equals(account.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        account = Account.builder()
                .phone(accountRequest.getPhone())
                .fullName(accountRequest.getFullName())
                .passwordHash(passwordEncoder.encode(accountRequest.getPassword()))
                .gender(Gender.valueOf(accountRequest.getGender().toUpperCase()))
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .build();
        account = accountRepository.save(account);
        if(accountRequest.getRole().equals(Role.STAFF.getRoleName())){

            account.setRole(Role.STAFF);
            accountRepository.save(account);

            Staff staff = Staff.builder()
                    .account(account)
                    .hiredDate(LocalDate.now())
                    .position(Position.valueOf(accountRequest.getPosition().toUpperCase()))
                    .build();
            staff = staffRepository.save(staff);

            AccountManageResponse accountManageResponse = mapper.toAccountManageResponse(account);
            accountManageResponse.setPosition(staff.getPosition());

            return accountManageResponse;
        } else{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(accountRequest.getDateOfBirth(), formatter);

            account.setRole(Role.CUSTOMER);
            accountRepository.save(account);

            Customer customer = Customer.builder()
                    .account(account)
                    .address(accountRequest.getAddress())
                    .dob(localDate)
                    .build();
            customer = customerRepository.save(customer);

            AccountManageResponse accountManageResponse = mapper.toAccountManageResponse(account);
            accountManageResponse.setAddress(customer.getAddress());
            accountManageResponse.setDateOfBirth(customer.getDob());

            return accountManageResponse;
        }
    }

    @Override
    public AccountDetailResponse updateAccountInfo(UpdateAccountInfoRequest request) {
        Account account = getAccount();

        Account accountForValid = accountRepository.findByPhone(request.getPhone());

        if (accountForValid != null && !accountForValid.getPhone().equals(account.getPhone())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        accountForValid = accountRepository.findByEmail(request.getEmail()).orElse(null);

        if(accountForValid != null && !accountForValid.getEmail().equals(account.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dob = LocalDate.parse(request.getDob(), formatter);

        account.setEmail(request.getEmail());
        account.setFullName(request.getFullName());
        account.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        account.setUpdatedDate(LocalDateTime.now());

        accountRepository.save(account);

        Customer customer = customerRepository.findByAccount(account);
        customer.setDob(dob);
        customer.setAddress(request.getAddress());

        customer = customerRepository.save(customer);

        return AccountDetailResponse.builder()
                .email(account.getEmail())
                .phone(account.getPhone())
                .address(customer.getAddress())
                .role(account.getRole().getRoleName())
                .dob(dob.toString())
                .address(customer.getAddress())
                .createdDate(account.getCreatedDate().toString())
                .fullName(account.getFullName())
                .gender(account.getGender().getName())
                .build();
    }

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        Account account = accountRepository.findByPhone(loginRequest.getPhone()); //test
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if(!account.getIsActive()){
            throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPasswordHash())) {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }

        AccountResponse accountResponse = mapper.toAccountResponse(account);
        accountResponse.setAccessToken(jwtService.generateAccessToken(account));

        return accountResponse;
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            log.warn("Password and Confirm Password do not match");
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if (accountRepository.findByPhone(registerRequest.getPhone()) != null) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        Account user = Account.builder()
                .phone(registerRequest.getPhone())
                .fullName(registerRequest.getFullName())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .isActive(true)
                .role(Role.CUSTOMER)
                .createdDate(LocalDateTime.now())
                .build();

        accountRepository.save(user);
        return "Success";
    }

    @Override
    public AccountDetailResponse accountDetail() {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByPhone(phone);
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Customer customer = customerRepository.findByAccount(account);

        String dob = null;

        if(customer.getDob() != null){
            dob = customer.getDob().toString();
        }

        return AccountDetailResponse.builder()
                .email(account.getEmail())
                .phone(account.getPhone())
                .address(customer.getAddress())
                .role(account.getRole().getRoleName())
                .dob(dob)
                .address(customer.getAddress())
                .createdDate(account.getCreatedDate().toString())
                .fullName(account.getFullName())
                .gender(account.getGender().getName())
                .build();
    }

    @Override
    public AccountResponse setAccountActive(Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        account.setIsActive(!account.getIsActive());
        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public Boolean isCorrectPassword(String password) {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByPhone(phone);
        if(account == null){
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }

        return passwordEncoder.matches(password, account.getPasswordHash());
    }

    @Override
    public Page<DriverResponse> getAvailableDrivers(LocalDateTime departureTime, LocalDateTime arrivalTime, Pageable pageable, String search) {

        Pageable correctedPageable = PageRequest.of(Math.max(pageable.getPageNumber() - 1, 0), pageable.getPageSize());

        List<DriverResponse> availableDrivers = accountRepository.findByRoleAndStaff_Position(Role.STAFF, Position.DRIVER).stream()
                .filter(driver -> driver.getFullName().toLowerCase().contains(search.toLowerCase()))
                .filter(driver -> !tripRepository.existsByDriver_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
                        driver.getId(), arrivalTime, departureTime))
                .map(mapper::toDriverResponse)
                .toList();

        return new PageImpl<>(
                availableDrivers.stream()
                        .skip(correctedPageable.getOffset())
                        .limit(correctedPageable.getPageSize())
                        .toList(),
                correctedPageable,
                availableDrivers.size()
        );
    }

}