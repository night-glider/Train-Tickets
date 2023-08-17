/*
 * This file is generated by jOOQ.
 */
package com.example.demo;


import com.example.demo.tables.Route;
import com.example.demo.tables.Ticket;
import com.example.demo.tables.Train;
import com.example.demo.tables.Trip;
import com.example.demo.tables.records.RouteRecord;
import com.example.demo.tables.records.TicketRecord;
import com.example.demo.tables.records.TrainRecord;
import com.example.demo.tables.records.TripRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<RouteRecord> ROUTE_PKEY = Internal.createUniqueKey(Route.ROUTE, DSL.name("route_pkey"), new TableField[] { Route.ROUTE.ID }, true);
    public static final UniqueKey<TicketRecord> TICKET_PKEY = Internal.createUniqueKey(Ticket.TICKET, DSL.name("ticket_pkey"), new TableField[] { Ticket.TICKET.ID }, true);
    public static final UniqueKey<TrainRecord> TRAIN_PKEY = Internal.createUniqueKey(Train.TRAIN, DSL.name("train_pkey"), new TableField[] { Train.TRAIN.ID }, true);
    public static final UniqueKey<TripRecord> TRIP_PKEY = Internal.createUniqueKey(Trip.TRIP, DSL.name("trip_pkey"), new TableField[] { Trip.TRIP.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TicketRecord, TripRecord> TICKET__TICKET_TRIP_ID_FKEY = Internal.createForeignKey(Ticket.TICKET, DSL.name("ticket_trip_id_fkey"), new TableField[] { Ticket.TICKET.TRIP_ID }, Keys.TRIP_PKEY, new TableField[] { Trip.TRIP.ID }, true);
    public static final ForeignKey<TripRecord, RouteRecord> TRIP__TRIP_ROUTE_ID_FKEY = Internal.createForeignKey(Trip.TRIP, DSL.name("trip_route_id_fkey"), new TableField[] { Trip.TRIP.ROUTE_ID }, Keys.ROUTE_PKEY, new TableField[] { Route.ROUTE.ID }, true);
    public static final ForeignKey<TripRecord, TrainRecord> TRIP__TRIP_TRAIN_ID_FKEY = Internal.createForeignKey(Trip.TRIP, DSL.name("trip_train_id_fkey"), new TableField[] { Trip.TRIP.TRAIN_ID }, Keys.TRAIN_PKEY, new TableField[] { Train.TRAIN.ID }, true);
}