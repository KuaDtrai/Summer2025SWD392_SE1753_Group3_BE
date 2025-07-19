package com.project.ibtss.utilities.mapper;

import com.project.ibtss.dto.response.FeedbackResponse;
import com.project.ibtss.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "feedbackId", source = "id")
    @Mapping(target = "customerName", expression = "java(feedback.getCustomer() != null && feedback.getCustomer().getAccount() != null ? feedback.getCustomer().getAccount().getFullName() : null)")
    @Mapping(target = "driverName", expression = "java(feedback.getDriver() != null && feedback.getDriver().getAccount() != null ? feedback.getDriver().getAccount().getFullName() : null)")
    @Mapping(target = "driverPhone", expression = "java(feedback.getDriver() != null && feedback.getDriver().getAccount() != null ? feedback.getDriver().getAccount().getPhone() : null)")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "createdDate", expression = "java(feedback.getCreatedDate() != null ? feedback.getCreatedDate() : null)")
    @Mapping(target = "status", source = "status")
    FeedbackResponse toFeedbackResponse(Feedback feedback);
}
