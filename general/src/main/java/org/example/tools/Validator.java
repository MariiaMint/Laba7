package org.example.tools;

import org.example.beginningClasses.Mood;
import org.example.beginningClasses.WeaponType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static org.example.tools.Printer.print;

public class Validator {
    public static String notEmptyString(Scanner scanner){
        String str = scanner.nextLine();
        while (str.isBlank()) {
            print("Имя должно быть не пустой строкой, введите его правильно");
            str = scanner.nextLine();
        }
        return str;
    }
    public static String onlyLetters(Scanner scanner){
        String str = notEmptyString(scanner);
        while (!str.chars().allMatch(Character::isLetter)) {
            print("Имя должно состоять из букв");
            str = notEmptyString(scanner);
        }
        return str;
    }
    public static boolean yesNo(Scanner scanner){
        String str = scanner.nextLine();
        boolean boo = true;
        while (!(str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("no"))) {
            print("Вам нужно ввести только yes или no");
            str = scanner.nextLine();
        }
        if (str.equalsIgnoreCase("yes")) {
            boo = true;
        } else if (str.equalsIgnoreCase("no")) {
            boo = false;
        }
        return boo;
    }
    public static Double etoDouble(boolean canBeNull, Scanner scanner){
        Double x;
        while (true) {
            String strX = scanner.nextLine().replace(",", ".");
            if (!strX.isBlank()) {
                try {
                    x = Double.parseDouble(strX);
                    break;
                } catch (NumberFormatException e) {
                    print("Введите число с плавающей запятой");
                }
            } else {
                if (canBeNull && strX.isEmpty()){x = null; break;}
                print("Введите число с плавающей запятой");
            }
        }
        return x;
    }

    public static WeaponType weaponType(Scanner scanner){
        WeaponType weaponType;
        while (true) {
            String weapon = scanner.nextLine().toUpperCase();
            List<String> myList = Arrays.asList("AXE", "HAMMER", "PISTOL", "RIFLE");
            if (myList.contains(weapon) || weapon.equals("")) {
                if (myList.contains(weapon)){weaponType = WeaponType.valueOf(weapon); break;};
                if (weapon.equalsIgnoreCase("")) {
                    weaponType = null;
                    break;
                }
            } else {
                print("Каким оружием обладает ваш человек(введите слово из данного списка \n AXE\n HAMMER\n PISTOL\n RIFLE)");
            }
        }
        return weaponType;
    }

    public static Mood mood(Scanner scanner){
        Mood mood;
        while (true) {
            String md = scanner.nextLine().toUpperCase();
            List<String> myList = Arrays.asList("SADNESS", "SORROW", "APATHY");
            if (myList.contains(md)) {mood = Mood.valueOf(md);break;}
            if (md.equalsIgnoreCase("")) {
                mood = null;
                break;
            }
            else {
                print("Какое настроение у человека(введите слово из данного списка либо пустую строку)\n SADNESS\n SORROW\n APATHY");
            }
        }
        return mood;
    }

    public static String id(String strId, Scanner scanner){
        int id;
        while(true) {
            if (strId.isBlank()) {
                print("Введите число");
                strId = scanner.nextLine();
            } else {
                try {
                    id = parseInt(strId);
                    break;
                } catch (NumberFormatException e) {
                    print("id должен быть целым числом(от 0 до 2147483647), введите его правильно");
                    strId = scanner.nextLine();
                }
            }
        }
        return strId;
    }
}
