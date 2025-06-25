package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.StaffRequest;
import com.project.ibtss.dto.response.StaffResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Role;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.StaffMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Staff;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.StaffRepository;
import com.project.ibtss.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffMapper staffMapper;
    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;

    private Account getAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findById(account.getId()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public StaffResponse updateStaff(StaffRequest request) {
        Account account = getAccount();
        if (account.getRole() == Role.STAFF){
            Staff staff = staffRepository.findByAccountId(account.getId()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
            staff.setHiredDate(request.getHireDate());
            staff.setPosition(request.getPosition());
            return staffMapper.toStaffResponse(staffRepository.save(staff));
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public StaffResponse getStaff() {
        Account account = getAccount();
        if (account.getRole() == Role.STAFF){
            return staffMapper.toStaffResponse(staffRepository.findByAccountId(account.getId()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND)));
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
}
