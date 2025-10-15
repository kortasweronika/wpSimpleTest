package tests;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.UUID;

public class SharedVarTests {

  // TEST 1: może wygenerować i „zwrócić” zmienną przez ITestContext
  @Test
  public void producer(ITestContext ctx) {
    // jeśli chcesz czasem NIE włożyć zmiennej, zakomentuj dwie linie poniżej
    String token = UUID.randomUUID().toString();
    ctx.setAttribute("token", token);
    Reporter.log("Producer: put token=" + token, true);
  }

  // TEST 2: działa poprawnie, czy zmienna jest, czy jej nie ma
  @Test
  public void consumer(ITestContext ctx) {
    Optional<String> token = Optional.ofNullable((String) ctx.getAttribute("token"));

    if (token.isPresent()) {
      Reporter.log("Consumer: got token=" + token.get(), true);
      // ... użyj tokenu (np. jako parametr do żądania / selektora itp.)
    } else {
      Reporter.log("Consumer: no token found, proceeding with default flow", true);
      // ... ścieżka domyślna gdy zmiennej nie ma
    }
  }
}
