package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.model.Tickets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "ticketId", source = "id")
    @Mapping(target = "ticketOwnerName", source = "account.fullName")
    TicketResponse toTicketResponse(Tickets ticket);
}
