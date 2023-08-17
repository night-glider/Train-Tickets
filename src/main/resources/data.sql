DELETE FROM public.ticket;

DELETE FROM public.trip;

DELETE FROM public.route;

DELETE FROM public.train;

INSERT INTO public.route(
    start_city, finish_city, trip_time)
VALUES
    ('Липецк', 'Москва', INTERVAL '1 DAY'),
    ('Москва', 'Липецк', INTERVAL '1 DAY');



INSERT INTO public.train(
    model, passenger_capacity)
VALUES
    ('Победа', 100),
    ('Поражение', 50),
    ('Ничья', 75);

INSERT INTO public.trip(
    train_id, route_id, start_time, end_time, price)
SELECT
     (SELECT id FROM public.train WHERE model='Победа'),
     (SELECT id FROM public.route WHERE start_city='Липецк'),
     i, i + INTERVAL '1 DAY', 10
FROM generate_series(NOW() - INTERVAL '1 DAY',NOW() + INTERVAL '1 DAY', INTERVAL '1 HOUR') as t(i);

INSERT INTO public.trip(
    train_id, route_id, start_time, end_time, price)
SELECT
    (SELECT id FROM public.train WHERE model='Ничья'),
    (SELECT id FROM public.route WHERE start_city='Москва'),
    i, i + INTERVAL '1 DAY', 10
FROM generate_series(NOW() - INTERVAL '1 HOUR',NOW() + INTERVAL '1 HOUR', INTERVAL '1 HOUR') as t(i);

INSERT INTO public.ticket(trip_id, passenger_name)
SELECT
    (SELECT id FROM public.trip LIMIT 1),
    'TEST NAME'
FROM generate_series(0,99) as t(i);
