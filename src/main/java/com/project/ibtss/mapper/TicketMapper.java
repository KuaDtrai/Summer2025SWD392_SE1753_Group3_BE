package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.model.Tickets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "ticketId", source = "id")
    @Mapping(target = "customerName", source = "customer.account.fullName")
    @Mapping(target = "staffName", source = "soldBy.account.fullName")
    TicketResponse toTicketResponse(Tickets ticket);
}
