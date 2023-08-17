# Train-Tickets
Тестовое задание от МедСофт.

# Запуск
1. Установить OpenServer с PostgreSQL.
2. Создать проект postgres в директории domains
3. Удалить проект localhost из директории domains
4. Запустить OpenServer
5. Открыть проект в Intellij IDEA
6. Подтянуть зависимости
7. Запустить DemoApplication.java

# API
## GET /trips
> **Параметры**
> string start_city опционально
> 
> string finish_city опционально
> 
> string train_model опционально
> 
> **Описание**
> 
> Выдаёт все будущие поездки. Можно указать параметры для поиска.

## POST /new_ticket
> **Параметры**
> 
> string passenger_name обязательно
> 
> int trip_id обязательно
>
> int count необязательно, значение по умолчанию - 1
>
> **Описание**
>
> Регистрирует новый билет на passenger_name на соответствующую поездку. Можно взять сразу несколько билетов

## POST /cancel_ticket
> **Параметры**
>
> int ticket_id обязательно
>
> **Описание**
>
> Отменяет билет с соответствующим id.
