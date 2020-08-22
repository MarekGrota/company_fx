package f_mockito;

import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class BooksProcessor {

  private Database database;

  public BooksProcessor(Database database) {
    this.database = database;
  }

  // metoda zwraca sumę cen książek
  // np. 30.99

  // próba dostępu do bazy danych następuje trzykrotnie
  // np. jeśli za pierwszym razem wystąpi błąd w dostępie do bazy danych wykonuje jeszcze dwie próby
  // jeśli żadna z 3 prób się nie powiedzie rzucany jest wyjątek z odpowiednią wiadomością
  public double getTotalPrice() {
    try {
      return runWithRetries(3, () -> this.database.getBooks().stream().mapToDouble(Book::getPrice).sum());
    } catch (Exception e) {
      throw generateBooksProcessorException(e);
    }
  }

  // metoda zwraca listę tytułów książek (tytuły oddzielone przecinkami)
  // np. Pan Tadeusz,Pan Wołodyjowski,Potop

  // próba dostępu do bazy danych następuje trzykrotnie
  // np. jeśli za pierwszym razem wystąpi błąd w dostępie do bazy danych wykonuje jeszcze dwie próby
  // jeśli żadna z 3 prób się nie powiedzie rzucany jest wyjątek z odpowiednią wiadomością
  public String getListOfTitles() {
    try {
      return runWithRetries(3,
          () -> this.database.getBooks().stream().map(Book::getTitle).collect(Collectors.joining(",")));
    } catch (Exception e) {
      throw generateBooksProcessorException(e);
    }
  }

  private BooksProcessorException generateBooksProcessorException(Exception e) {
    return new BooksProcessorException("Database has thrown exception with message: " + e.getMessage(), e);
  }

  <T> T runWithRetries(int maxRetries, Callable<T> t) throws Exception {
    int count = 0;
    while (count < maxRetries) {
      try {
        return t.call();
      } catch (RuntimeException e) {
        if (++count >= maxRetries) {
          throw e;
        }
      }
    }
    throw new Exception();
  }
}