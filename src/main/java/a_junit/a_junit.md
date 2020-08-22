# Zadanie

 1. W bieżącym pakiecie (`main/java/a_junit`) utwórz klasę `Calculator`.
 1. Zaimplementuj w niej działania `+`, `-`, `*`, `/`:
   * `Integer add(Integer a, Integer b)`
   * `Integer substract(Integer a, Integer b)`
   * `Integer multiply(Integer a, Integer b)`
   * `Integer divide(Integer a, Integer b)`
 1. Następnie w `test/java/a_junit` utwórz klasę `CalculatorTest`.
 1. W klasie `CalculatorTest` napisz testy dla metod zaimplementowanych w klasie `Calculator`
 wykorzystując JUnit 5.
 
# Zadanie

 1. W klasie `CalculatorTest` wykorzystaj adnotacje JUnit 5 do wypisania na konsolę
 poniższych komunikatów:
    * komunikat przed wszystkimi testami: `przed wszystkimi`
    * komunikat po wszystkich testach: `po wszystkich`
    * komunikat przed każdym, pojedynczym testem: `przed testem`
    * komunikat po każdym, pojedynczym teście: `po teście` 
 1. Następnie wybierz jeden z testów i z pomocą odpowiedniej adnotacji
 spowoduj, że będzie on ignorowny przy uruchamianiu testów.
