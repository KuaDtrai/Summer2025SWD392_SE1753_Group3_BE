package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.FeedbackResponse;
import com.project.ibtss.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "feedbackId", source = "id")
    @Mapping(target = "customerName", expression = "java(feedback.getCustomer() != null && feedback.getCustomer().getAccount() != null ? feedback.getCustomer().getAccount().getFullName() : null)")
    @Mapping(target = "staffName", expression = "java(feedback.getStaff() != null && feedback.getStaff().getAccount() != null ? feedback.getStaff().getAccount().getFullName() : null)")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "createdDate", expression = "java(feedback.getCreatedDate() != null ? feedback.getCreatedDate().toLocalDate() : null)")
    FeedbackResponse toFeedbackResponse(Feedback feedback);
}