package d_assertj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zoo {
    private Random random = new Random();

    private List<Animal> animals;

    public Zoo() {
        this.animals = new ArrayList<>();
    }

    public void addAnimal(Animal pet) {
        this.animals.add(pet);
    }

    // pozwala na dodanie zwierzaczka do Zoo
    public void addAnimals(Animal... animals) {
        for (Animal animal : animals) {
            this.animals.add(animal);
        }
    }

    // zwraca imię zwierzątka w zabawnej formie, np. Stefan -> sTeFAn
    public String generateFunnyName(Animal animal) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : animal.getName().toCharArray()) {
            stringBuilder.append((random.nextInt(1000) % 2 == 0) ? Character.toLowerCase(c) : Character.toUpperCase(c));
        }
        return stringBuilder.toString();
    }

    // zwraca imię w formacie Zwierzątko [losowa ilość spacji] Imię, np. Stefan -> Zwierzątko      Stefan
    public String generateNameWithPrefix(Animal animal) {
        int numberOfSpaces = random.nextInt(10);
        StringBuilder spaces = new StringBuilder();
        for(int i = 0; i < numberOfSpaces; i++) {
            spaces.append(" ");
        }
        return "Zwierzątko " + spaces.toString() + " " + animal.getName();
    }

    // zwraca wszystkie zwierzaczki, które są w Zoo
    public List<Animal> getAllAnimals() {
        return animals;
    }

}
