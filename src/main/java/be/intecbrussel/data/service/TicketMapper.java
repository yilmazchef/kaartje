package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.TicketDto;
import be.intecbrussel.data.entity.Ticket;
import org.mapstruct.*;

@Mapper ( componentModel = "spring" )
public interface TicketMapper {
    @Mapping ( source = "ticketId", target = "id" )
    Ticket ticketDtoToTicket ( TicketDto ticketDto );

    @Mapping ( source = "id", target = "ticketId" )
    TicketDto ticketToTicketDto ( Ticket ticket );

    @Mapping ( source = "ticketId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Ticket updateTicketFromTicketDto ( TicketDto ticketDto, @MappingTarget Ticket ticket );
}
