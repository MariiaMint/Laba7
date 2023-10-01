package org.example.beginningClasses;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.example.tools.Printer.print;
import static org.example.tools.Validator.*;

public class HumanBeing implements Serializable, Comparable<HumanBeing>{
    private Integer userId;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле не может быть null
    private Double impactSpeed; //Поле может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле может быть null
    private Car car; //Поле не может быть null

    public HumanBeing() {
    }
    public int compareTo(HumanBeing obj){
        return this.getName().compareToIgnoreCase(obj.getName());
    }

    public String getName() {
        return name;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public Car getCar() {
        return car;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUserId(Integer userId){this.userId = userId;}
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setName(String name){this.name = name;}

    public void setCar(Car car) {
        this.car = car;
    }

    public Mood getMood() {
        return mood;
    }

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    public Double getCoordinateX() {
        return coordinates.getX();
    }
    public Double getCoordinateY() {
        return coordinates.getY();
    }

    public static void createCoordinates(HumanBeing person, Scanner scanner){
        print("Введите координату 'X' (число с плавающей запятой, должно быть больше -386)");
        Double x = etoDouble(false, scanner);
        while (x <= -386) {
            print("Kоордината Х должна быть > -386");
            x = etoDouble(false, scanner);
        }
        print("Введите координату 'Y'(любое число с плавающей запятой)");
        double y = etoDouble(false, scanner);
        Coordinates coordinates = new Coordinates(x, y);
        person.setCoordinates(coordinates);
    }
    public static void creatCar(HumanBeing person, Scanner scanner){
        Car car = new Car();
        print("У вашего человек должна быть машина, как она называется?(можно ввести пустую строку)");
        String car_name = scanner.nextLine();
        if (car_name.isEmpty()) {
            car_name = null;
        }
        car.setName(car_name);
        print("Также вы можете сказать сказать крутая ли машина(yes или no)");
        car.setCool(yesNo(scanner));
        person.setCar(car);}
    public static HumanBeing creatingHuman(Scanner scanner, Integer UID) {

        HumanBeing person = new HumanBeing();
        person.setUserId(UID);

        //проверка имени
        print("Введите имя человека");
        person.setName(onlyLetters(scanner));

        //Car
        creatCar(person, scanner);

        //Coordinates
        createCoordinates(person, scanner);

        //hasToothpick
        print("Есть ли у человека зубочистка? {yes/no}");
        person.setHasToothpick(yesNo(scanner));

        // create impactSpeed
        print("Введите скорость человека(число или пустая строка)");
        Double impactSpeed = etoDouble(true, scanner);
        person.setImpactSpeed(impactSpeed);

        //realhero
        print("Является ли человек настоящим героем {yes/no}");
        boolean realhero = yesNo(scanner);
        person.setRealHero(realhero);

        // weaponType
        print("Каким оружием обладает ваш человек(введите слово из данного списка\n AXE\n HAMMER\n PISTOL\n RIFLE");
        person.setWeaponType(weaponType(scanner));

        // setMood
        print("Какое настроение у человека(введите слово из данного списка либо пустую строку)\n SADNESS\n SORROW\n APATHY");
        person.setMood(mood(scanner));
        return person;
    }

    @Override
    public String toString() {
        return  userId + "; " +
                id + "; " +
                name + "; " +
                coordinates.toString() + "; " +
                this.creationDateToString() + "; " +
                realHero + "; " +
                hasToothpick + "; " +
                impactSpeed + "; " +
                weaponType + "; " +
                mood + "; " +
                car.toString();}

    public String toCSV() {
        return  id + "; " +
                name + "; " +
                coordinates.toString() + "; " +
                creationDate + "; " +
                realHero + "; " +
                hasToothpick + "; " +
                impactSpeed + "; " +
                weaponType + "; " +
                mood + "; " +
                car.toString() + "\n";}

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyyг HHч.mmмин.sс");
    public String creationDateToString() {
        return creationDate.format(dateTimeFormatter);
    }

}

