1. К сожалению app/src/main/resources/schema.sql не генерируется из app/src/main/java/pr4/model, поэтому приходится писать его вручную.

2. Отсутствует маппинг связей между объектами, приходится вручную получать связанные объекты при помощи написания вручую sql-запросов или вызовов по типу "1 + N"

```java 
public class Ticket {
    @Id long id;

    double price;
    Instant sessionDate;

    // нельзя указать явно связь
    long filmId;
    @With Film film;
}
```
```java 
@Transactional(readOnly = true)
public Flux<Ticket> getAllTickers() {
    return ticketRepository.findAll().flatMap(
        // Приходится дополнять полученные данные с помощью вложенных запросов
        (Ticket t) -> filmService.getFilmById(t.getFilmId()).map(
            (Film f) -> t.withFilm(f)
        )
    );
}
```

3. Не работает декодирование и кодирование данных в контроллере, можно получать и отдавать только json-совместимые типы данных 
```java
public Flux<String> allTickets();
public Mono<String> ticketById(String ticketId);
public Flux<String> ticketCreationChannel(Flux<String> input);
```

[adminer](http://localhost:8080/?pgsql=localhost&username=postgres&db=postgres&ns=public)

```sh
gradle run
```

```sh
cd frontend && npm start
```
