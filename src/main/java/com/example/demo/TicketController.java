package com.example.demo;

import com.example.demo.tables.Route;
import com.example.demo.tables.Ticket;
import com.example.demo.tables.Train;
import com.example.demo.tables.Trip;
import com.example.demo.tables.records.TrainRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

import static org.jooq.impl.DSL.noCondition;

@RestController
public class TicketController {
    @Autowired
    public DSLContext dbContext;
    
    @GetMapping("/trips")
    public LinkedList<HashMap<String, Object>> getTrips(
            @RequestParam(value = "start_city", defaultValue = "") String startCity,
            @RequestParam(value = "finish_city", defaultValue = "") String finishCity,
            @RequestParam(value = "train_model", defaultValue = "") String trainModel) {

        Condition cond = noCondition();

        cond = cond.and(Trip.TRIP.START_TIME.greaterThan(LocalDateTime.now()));

        if(!trainModel.isEmpty()) {
            cond = cond.and(Train.TRAIN.MODEL.eq(trainModel));
        }
        if(!startCity.isEmpty()) {
            cond = cond.and(Route.ROUTE.START_CITY.eq(startCity));
        }
        if(!finishCity.isEmpty()) {
            cond = cond.and(Route.ROUTE.FINISH_CITY.eq(finishCity));
        }

        var query = dbContext.select(Trip.TRIP.ID, Route.ROUTE.START_CITY, Route.ROUTE.FINISH_CITY, Train.TRAIN.MODEL, Trip.TRIP.START_TIME, Trip.TRIP.END_TIME)
                .from(Trip.TRIP)
                .join(Train.TRAIN).on(Trip.TRIP.TRAIN_ID.eq(Train.TRAIN.ID))
                .join(Route.ROUTE).on(Trip.TRIP.ROUTE_ID.eq(Route.ROUTE.ID))
                .where(cond);


        var result = query.fetch();

        var return_res = new LinkedList< HashMap<String, Object> >();
        for(var row : result) {
            var test = new HashMap<String, Object>();
            test.put("id", row.getValue("id"));
            test.put("start_city", row.getValue("start_city"));
            test.put("finish_city", row.getValue("finish_city"));
            test.put("train_model", row.getValue("model"));
            test.put("start_time", row.getValue("start_time"));
            test.put("end_time", row.getValue("end_time"));
            return_res.add(test);
        }

        return return_res;
    }

    @PostMapping("/new_ticket")
    public HashMap<String, Object> createTicket(
            @RequestParam(value = "passenger_name", defaultValue = "") String passengerName,
            @RequestParam(value = "trip_id", defaultValue = "0") int tripId,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        var result = new HashMap<String, Object>();
        result.put("success", false);

        if(passengerName.isEmpty()) {
            result.put("message", "Имя пассажира должно быть указано");
            return result;
        }

        var trip = dbContext.fetchOne(Trip.TRIP, Trip.TRIP.ID.eq(tripId));

        if(trip == null) {
            result.put("message", "Поездки с таким id нет");
            return result;
        }
        if(trip.getStartTime().isBefore(LocalDateTime.now())) {
            result.put("message", "Посадка уже закончена");
            return result;
        }

        int busyTicketsCount = dbContext.fetchCount(
                dbContext.selectFrom(Ticket.TICKET)
                        .where(Ticket.TICKET.TRIP_ID.eq(trip.getId())
                                .and(Ticket.TICKET.CANCELED.eq(false)))
        );

        var train = dbContext.fetchOne(Train.TRAIN, Train.TRAIN.ID.eq(trip.getTrainId()));

        assert train != null;
        int availableTicketCount = train.getPassengerCapacity() - busyTicketsCount;

        if(availableTicketCount < 1) {
            result.put("message", "Все места уже заняты =(");
            return result;
        }

        if(availableTicketCount < count) {
            result.put(
                    "message",
                    String.format("Вы запрашиваете %d билетов, но доступно только %d.", count, availableTicketCount));
            return result;
        }

        for(int i = 0; i < count; i++) {
           var newTicket = dbContext.newRecord(Ticket.TICKET);
           newTicket.setTripId(trip.getId());
           newTicket.setPassengerName(passengerName);
           newTicket.store();
        }

        result.put("success", true);
        result.put("message", "Билеты успешно куплены!");
        return result;
    }

    @PostMapping("/cancel_ticket")
    public HashMap<String, Object> cancelTicket(
            @RequestParam(value = "ticket_id", defaultValue = "0") int ticketId
    ) {
        var result = new HashMap<String, Object>();
        result.put("success", false);

        var ticket = dbContext.fetchOne(Ticket.TICKET, Ticket.TICKET.ID.eq(ticketId));
        if(ticket == null) {
            result.put("message", "Билета с такми id нет!");
            return result;
        }
        if(ticket.getCanceled()) {
            result.put("message", "Билет уже отменён!");
            return result;
        }

        var trip = dbContext.fetchOne(Trip.TRIP, Trip.TRIP.ID.eq(ticket.getTripId()));
        assert trip != null;
        if(trip.getStartTime().isBefore(LocalDateTime.now())) {
            result.put("message", "Отправка уже свершилась. Отмена невозможна!");
            return result;
        }
        if(trip.getStartTime().minusHours(2).isBefore( LocalDateTime.now() )) {
            result.put("message", "До отправления осталось меньше 2 часов, отмена невозможна!");
            return result;
        }

        ticket.setCanceled(true);
        ticket.store();

        result.put("success", true);
        result.put("message", "Билет успешно отменён!");
        return result;
    }
}
