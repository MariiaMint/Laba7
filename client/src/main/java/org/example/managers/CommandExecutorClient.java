package org.example.managers;

import org.example.MessWithHuman;
import org.example.Message;
import org.example.beginningClasses.*;

import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static org.example.beginningClasses.HumanBeing.creatCar;
import static org.example.beginningClasses.HumanBeing.createCoordinates;
import static org.example.tools.EntryHandler.getUID;
import static org.example.tools.FileScanner.scan;
import static org.example.tools.Inputer.scanner;
import static org.example.tools.Printer.print;
import static org.example.tools.RequestReaderClient.serverRead;
import static org.example.tools.ResponseSenderClient.*;
import static org.example.tools.Validator.*;

public class CommandExecutorClient {
    public void add() {
        sendMessWithHuman("add", HumanBeing.creatingHuman(scanner, getUID()), getUID());
        print(serverRead().getMessageName());
    }

    public void remove_by_id(String strId) {
        while (strId == null) {
            strId = id("", scanner);
        }
        sendMessWithArg("remove_by_id", strId, getUID());
        print(serverRead().getMessageName());
    }

    public void update(String strId) {
        if (Objects.equals(strId, "")) {
            strId = id("", scanner);
        }
        sendMessWithArg("update", strId, getUID());
        Message message = serverRead();
        if(message instanceof MessWithHuman){
            CommandExecutorClient.updater(((MessWithHuman) message).getHuman());
            //message = RequestReaderClient.serverRead();
        }else {print(message.getMessageName());}
    }

    public static void updater(HumanBeing person) {

        List<String> pars = Arrays.asList("name", "coordinates", "realHero", "hasToothpick", "impactSpeed", "weaponType", "mood", "car");
        String par = "";
        while (!par.equals("stop")) {
            print("выберите что из перечисленного вы хотите изменить(вводите по одному слову параметр)" + pars);
            print("чтобы закончить изменение введите 'stop'");
            par = scanner.nextLine();
            while (!(pars.contains(par)) && !par.equals("stop")) {
                print("выберите что из перечисленного вы хотите изменить(вводите по одному слову параметр)" + pars);
                par = scanner.nextLine();
            }
            switch (par) {
                case "name" -> {
                    print("Введите имя человека");
                    person.setName(onlyLetters(scanner));
                }
                case "coordinates" -> createCoordinates(person, scanner);
                case "realHero" -> {
                    print("Является ли человек настоящим героем {yes/no}");
                    boolean realhero = yesNo(scanner);
                    person.setRealHero(realhero);
                }
                case "hasToothpick" -> person.setHasToothpick(yesNo(scanner));
                case "impactSpeed" -> {
                    print("Введите скорость человека(число или пустая строка)");
                    Double impactSpeed = etoDouble(true, scanner);
                    person.setImpactSpeed(impactSpeed);
                }
                case "weaponType" -> {
                    print("Каким оружием обладает ваш человек(введите слово из данного списка\n AXE\n HAMMER\n PISTOL\n RIFLE");
                    person.setWeaponType(weaponType(scanner));
                }
                case "mood" -> {
                    print("Какое настроение у человека(введите слово из данного списка либо пустую строку)\n SADNESS\n SORROW\n APATHY");
                    person.setMood(mood(scanner));
                }
                case "car" -> creatCar(person, scanner);
            }
        }
        sendMessWithHuman("update", person, getUID());
        print(serverRead().getMessageName());
    }

    public void exit() {
        print("Завершаемся...");
    }

    public void countLessThanImpactSpeed(String strIs) {
        //Double impactSpeed;
        while (true) {
            if (strIs.isBlank()) {
                print("Введите число");
                strIs = scanner.nextLine();
            } else {
                try {
                    Double.parseDouble(strIs);
                    break;
                } catch (NumberFormatException e) {
                    print("скорость должна быть числом, введите ее правильно");
                    strIs = scanner.nextLine();
                }
            }
        }
        sendMessWithArg("count_less_than_impact_speed", strIs, getUID());
        print(serverRead().getMessageName());
    }

    static Vector<String> filePaths = new Vector<>();

    private HumanBeing humanForScript(ArrayList<String> listCommands, int i) throws NumberFormatException {
        HumanBeing human = new HumanBeing();
        try {
            human.setId(null);
            human.setCreationDate(null);
            if (listCommands.get(i + 1).chars().allMatch(Character::isLetter)) {
                human.setName(listCommands.get(i + 1));
            } else {
                throw new NumberFormatException();
            }
            human.setCoordinates(new Coordinates(parseDouble(listCommands.get(i + 2).replace(",", ".")), parseDouble(listCommands.get(i + 3).replace(",", "."))));
            human.setRealHero(parseBoolean(listCommands.get(i + 4)));
            human.setHasToothpick(parseBoolean(listCommands.get(i + 5)));
            if (listCommands.get(i + 6).equals("null")) {
                human.setImpactSpeed(null);
            } else {
                human.setImpactSpeed(parseDouble(listCommands.get(i + 6).replace(",", ".")));
            }
            if (listCommands.get(i + 7).equals("null")) {
                human.setWeaponType(null);
            } else {
                human.setWeaponType(WeaponType.valueOf(listCommands.get(i + 7)));
            }
            if (listCommands.get(i + 8).equals("null")) {
                human.setMood(null);
            } else {
                human.setMood(Mood.valueOf(listCommands.get(i + 8)));
            }
            if (!(listCommands.get(i + 9).equals("null")) && listCommands.get(i + 9).chars().allMatch(Character::isLetter)) {
                human.setCar(new Car(listCommands.get(i + 9), parseBoolean(listCommands.get(i + 10))));
            } else if (listCommands.get(i + 9).equals("null")) {
                human.setCar(new Car(null, parseBoolean(listCommands.get(i + 10))));
            } else {
                throw new NumberFormatException();
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            throw new NumberFormatException();
        }
        return human;
    }

    public void executeScript(String arg) {
        ArrayList<String> listCommands = scan(arg);
        if (listCommands.size() == 0) {
            sendMessWithArg("execute_script", "0", getUID());
        } else {
            filePaths.add(arg);
            int m = -1;
            int i = -1;
            for (String command : listCommands) {
                m++;
                String[] st = command.split(" ");
                if (i > -1 && m - i < 11) {
                    continue;
                }
                if (st[0].equals("add")) {
                    i = m;
                    try {
                        HumanBeing human = humanForScript(listCommands, i);
                        sendMessWithHuman("add", human, getUID());
                    } catch (NumberFormatException e) {
                        print("В файле есть некорректные данные");
                        break;
                    }
                } else if (st[0].equals("l_max")) {
                    i = m;
                    try {
                        HumanBeing human = humanForScript(listCommands, i);
                        sendMessWithHuman("add_if_max", human, getUID());
                    } catch (NumberFormatException e) {
                        print("В файле есть некорректные данные");
                        break;
                    }
                } else if (st[0].equals("update")) {
                    i = m;
                    if (st.length == 2 && st[1].chars().allMatch(Character::isDigit)) {
                        try {
                            HumanBeing human = humanForScript(listCommands, i);
                            sendMessWithArg("remove_by_id", st[1], getUID());
                            if (serverRead().getMessageName().equals("Человек удален")) {
                                human.setId(Integer.valueOf(st[1]));
                                sendMessWithHuman("add", human, getUID());
                            } else {
                                print("Нет человека с таким id");
                            }
                        } catch (NumberFormatException e) {
                            print("В файле есть некорректные данные");
                            break;
                        }
                    } else {
                        print("В файле есть ошибка, исправьте ее");
                        break;
                    }
                } else if (st[0].equals("remove_by_id")) {
                    if (st.length == 2 && st[1].chars().allMatch(Character::isDigit)) {
                        sendMessWithArg("remove_by_id", st[1], getUID());
                    } else {
                        print("В файле есть ошибка, исправьте ее");
                        break;
                    }
                } else {
                    if (st.length == 1) {
                        sendMessage(st[0], getUID());
                        print(serverRead().getMessageName());
                    } else if (st.length == 2) {
                        if (st[0].equals("execute_script")) {
                            if (filePaths.contains(st[1])) {
                                print("Команда " + st[0] + " " + st[1] + " уже была выполнена, дальнейшее выполнение приведёт к рекурсии");
                            } else {
                                executeScript(st[1]);
                            }
                        } else {
                            sendMessWithArg(st[0], st[1], getUID());
                            print(serverRead().getMessageName());
                        }
                    }
                }
            }
        }
        filePaths.clear();
    }
}
