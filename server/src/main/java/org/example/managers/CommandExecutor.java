package org.example.managers;

import org.example.beginningClasses.HumanBeing;
import org.example.beginningClasses.Mood;
import org.example.comands.Command;
import org.example.tools.HumanComparator;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.example.DBTools.HumanBeingInstructions.*;
import static org.example.managers.serverTools.ResponseSenderServer.sendHuman;
import static org.example.tools.Printer.print;

public class CommandExecutor {

    private static  Vector<HumanBeing> collection;
    private final Socket socket;

    private final ReentrantLock collectionLock;

    public CommandExecutor(Vector<HumanBeing> collection, Socket socket) {
        this.collection = collection;
        this.socket = socket;
        this.collectionLock = new ReentrantLock();
    }

    //HELP
    public String help(Integer UID) {
        StringBuilder sb = new StringBuilder();
        for (Command element : CommandManager.getCommands().values()) {
            sb.append(element.description()).append("\n");
        }
        return(sb.toString());
    }

    //ADD
    public String add(HumanBeing human, Integer UID) {
        // Заблокировать доступ к коллекции
        collectionLock.lock();
        try {
            int id;
            if (collection.isEmpty()) {
                id = 0;
            } else {
                id = 0;
                for (HumanBeing person : collection) {
                    id = Math.max(id, person.getId() + 1);
                }
            }
            human.setId(id);
            LocalDateTime creationDate = LocalDateTime.now();
            human.setCreationDate(creationDate);
            if (addHuman(UID, human)) {
                collection.add(human);
                return "Human added";
            } else {
                print("Human not added");
                return null;
            }
        } finally {
            // Разблокировать доступ к коллекции в блоке finally
            collectionLock.unlock();
        }
    }


    //SHOW
    public String show(Integer UID) {
        // Заблокировать доступ к коллекции
        collectionLock.lock();
        try {
            StringBuilder response = new StringBuilder();
            response.append(String.format("%-4s %-4s %-10s %-35s %-30s %-7s %-12s %-12s %-14s %-10s %-10s %-7s%n",
                    "UID", "id", "name", "coordinates", "creationDate", "realHero", "hasToothpick", "impactSpeed",
                    "weaponType", "mood", "carName", "carCool"));

            for (HumanBeing obj : collection) {
                response.append(String.format("%-4s %-4d %-10s (%15f ; %15f) %-30s %-10b %-12b %-12.2f %-12s %-10s %-10s %-7b%n",
                        obj.getUserId(),
                        obj.getId(),
                        obj.getName(),
                        obj.getCoordinateX(),
                        obj.getCoordinateY(),
                        obj.getCreationDate(),
                        obj.isRealHero(),
                        obj.getHasToothpick(),
                        obj.getImpactSpeed(),
                        obj.getWeaponType(),
                        obj.getMood(),
                        obj.getCar().getName(),
                        obj.getCar().isCool()));
            }
            return response.toString();
        } finally {
            // Разблокировать доступ к коллекции в блоке finally
            collectionLock.unlock();
        }
    }


    //remove_first
    public String removeFirst(Integer UID) {
        collectionLock.lock();
        try {
            if (!collection.isEmpty()) {
                if (deleteHumansWithUserId(UID, 1)) {
                    collection.addAll(getAllHumans());
                    return "первый элемент удален";
                } else {
                    return "у вас пока нет human'ов";
                }
            } else {
                return "у вас пока нет human'ов";
            }
        } finally {
            collectionLock.unlock();
        }
    }

    //clear
    public String clear(Integer UID) {
        collectionLock.lock();
        try {
            if (deleteHumansWithUserId(UID, Integer.MAX_VALUE)) {
                collection.removeAllElements();
                return "ваши humans удалены";
            } else {
                return "у вас пока нет human'ов";
            }
        } finally {
            collectionLock.unlock();
        }
    }

    //print_field_descending_mood
    public String printFieldDescendingMood(Integer UID) {
        collectionLock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            int apathy = 0;
            int sorrow = 0;
            int sadness = 0;
            for (HumanBeing obj : collection) {
                if (obj.getMood() == Mood.APATHY) {
                    apathy += 1;
                } else if (obj.getMood() == Mood.SADNESS) {
                    sadness += 1;
                } else if (obj.getMood() == Mood.SORROW) {
                    sorrow += 1;
                }
            }
            sb.append("SORROW\n".repeat(Math.max(0, sorrow)));
            sb.append("SADNESS\n".repeat(Math.max(0, sadness)));
            sb.append("APATHY\n".repeat(Math.max(0, apathy)));
            return sb.toString();
        } finally {
            collectionLock.unlock();
        }
    }

    //count_less_than_impact_speed impactSpeed
    public String countLessThanImpactSpeed(String strIs, Integer UID) {
        collectionLock.lock();
        try {
            Double impactSpeed = parseDouble(strIs);
            long number = collection.stream()
                    .filter(obj -> obj.getImpactSpeed() != null && obj.getImpactSpeed() < impactSpeed)
                    .count();
            return "количество элементов, значение поля impactSpeed которых меньше заданного равно " + number;
        } finally {
            collectionLock.unlock();
        }
    }

    //removeBId id
    public String removeBId(String strId, Integer UID) {
        collectionLock.lock();
        try {
            boolean removed = false;
            if (deleteHuman(UID, parseInt(strId))) {
                for (HumanBeing obj : collection) {
                    if (obj.getId() == parseInt(strId)) {
                        collection.removeElement(obj);
                        removed = true;
                        break;
                    }
                }
            }
            if (removed) {
                return "Человек удален";
            } else {
                return "Нет человека с таким id";
            }
        } finally {
            collectionLock.unlock();
        }
    }

    //update id
    public String update(String strId, Integer UID) {
        collectionLock.lock();
        try {
            boolean here = false;
            for (HumanBeing person : collection) {
                if (person.getId() == parseInt(strId)) {
                    sendHuman(person, UID, socket);
                    here = true;
                }
            }
            if (!here) {
                return "Человека с таким id нет";
            }
            return null;
        } finally {
            collectionLock.unlock();
        }
    }
    public String updater(HumanBeing human, Integer UID) {
        collectionLock.lock();
        try {
            if (updateHuman(human, UID)) {
                for (HumanBeing person : collection) {
                    if (Objects.equals(person.getId(), human.getId())) {
                        collection.removeElement(person);
                        break;
                    }
                }
                collection.add(human);
            }
            return "Человек обновлен";
        } finally {
            collectionLock.unlock();
        }
    }

    //info
    public String info(Integer UID) {
        collectionLock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Информация о коллекции:\n");
            sb.append("\tТип: Vector\n");
            sb.append("\tКласс объектов: HumanBeing\n");
            sb.append("\tКоличество элементов: " + collection.size() + "\n");
            if (!collection.isEmpty()) {
                sb.append("\tВремя инициализации: " + collection.firstElement().creationDateToString());
            }
            return sb.toString();
        } finally {
            collectionLock.unlock();
        }
    }

    //sort
    public String sort(Integer UID) {
        collectionLock.lock();
        try {
            Collections.sort(collection);
            return "Коллекция отсортирована";
        } finally {
            collectionLock.unlock();
        }
    }

    //print_descending
    public String printDescending(Integer UID) {
        collectionLock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            ArrayList<HumanBeing> list = new ArrayList<>(collection);
            list.sort(new HumanComparator());
            sb.append("id; name; coordinates; creationDate; realHero; hasToothpick; impactSpeed; weaponType; mood; carName; carCool\n");
            for (HumanBeing obj : list) {
                sb.append(obj.toString()).append("\n");
            }
            return sb.toString();
        } finally {
            collectionLock.unlock();
        }
    }

    //add_if_max
    public String addIfMax(HumanBeing person, Integer UID) {
        collectionLock.lock();
        try {
            print("сравнение идет пунктам realHero, coordinate X, Y, carCool");
            if (!collection.isEmpty()) {
                Vector<HumanBeing> vector = new Vector<>(collection);
                Collections.sort(vector, new HumanComparator());
                Vector<HumanBeing> vector2 = new Vector<>();
                vector2.add(person);
                vector2.add(vector.elementAt(0));
                Collections.sort(vector2, new HumanComparator());
                if (vector2.elementAt(0) == person) {
                    if (addHuman(UID, person)) {
                        collection.add(person);
                        return "добавлен";
                    } else {
                        print("not added");
                    }
                } else {
                    return "Элемент не является максимальным, мы его не добавили";
                }
            } else {
                if (addHuman(UID, person)) {
                    collection.add(person);
                    return "добавлен";
                } else {
                    print("not added");
                }
            }
            return null;
        } finally {
            collectionLock.unlock();
        }
    }

    //execute_script
    public void execute_script(){
    }

}