package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.FeedbackRequest;
import com.project.ibtss.dto.response.FeedbackResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Role;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.FeedbackMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Feedback;
import com.project.ibtss.model.Staff;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.CustomerRepository;
import com.project.ibtss.repository.FeedbackRepository;
import com.project.ibtss.repository.StaffRepository;
import com.project.ibtss.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    private final FeedbackMapper feedbackMapper;
    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;

    private Optional<Account> getAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findById(account.getId().describeConstable().orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND)));

    }

    @Override
    public FeedbackResponse getFeedback(Integer id) {
        return feedbackMapper.toFeedbackResponse(feedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND)));
    }

    @Override
    public List<FeedbackResponse> getAllFeedback() {
        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        Account account = getAccount().orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        if (account.getRole() == Role.ADMIN){
            List<Feedback> feedbackList = feedbackRepository.findAll();
            for (Feedback feedback : feedbackList) {
                feedbackResponseList.add(feedbackMapper.toFeedbackResponse(feedback));
            }
        }else if (account.getRole() == Role.USER){
            Customer customer = customerRepository.findByAccount(account);
            List<Feedback> feedbackList = feedbackRepository.getAllByCustomer(customer);
            for (Feedback feedback : feedbackList) {
                feedbackResponseList.add(feedbackMapper.toFeedbackResponse(feedback));
            }
        }
        return feedbackResponseList;
    }

    @Override
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        Account account = getAccount().orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Customer customer = customerRepository.findByAccount(account);
        if (customer == null) throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        Staff staff = staffRepository.findById(feedbackRequest.getStaffId()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Feedback feedback = new Feedback();
        feedback.setCustomer(customer);
        feedback.setStaff(staff);
        feedback.setRating(feedbackRequest.getRating());
        feedback.setContent(feedbackRequest.getContent());
        feedback.setCreatedDate(LocalDateTime.now());
        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }
}
