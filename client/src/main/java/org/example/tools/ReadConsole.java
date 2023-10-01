package org.example.tools;

import java.io.Console;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.example.tools.Inputer.scanner;


public class ReadConsole {
    static String command;
    public static String read(Scanner scanner, String str){
        System.out.println(str);
        try {
            command = scanner.nextLine();
        }catch (NoSuchElementException e){
            System.out.println("вы нажали ctrl D, подключитесь к серверу заново");
            System.exit(0);
        }
        return command;
    }

    public static String getPswd() {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword("Введите пароль: ");
            return new String(passwordArray);
        }
        else {
            return read(scanner, "Введите пароль: ");
        }
    }
}
