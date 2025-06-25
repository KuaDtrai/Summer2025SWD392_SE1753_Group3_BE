package com.project.ibtss.mapper;


import com.project.ibtss.dto.response.FeedbackResponse;
import com.project.ibtss.model.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponse toFeedbackResponse(Feedback feedback);
}
