package HomeWork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: " +
                "Фамилия Имя Отчество Дата_рождения(dd.mm.yyyy) " +
                "Номер_телефона(целое беззнаковое число без форматирования) Пол(f или m)");
        String input = scanner.nextLine();

        try {
            String[] data = parseInput(input);
            validateData(data);
            writeToFile(data);
            System.out.println("Данные успешно записаны.");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String[] parseInput(String input) throws InputMismatchException {
        String[] data = input.split(" ");
        if (data.length != 6) {
            throw new InputMismatchException("Некорректное количество данных. Ожидается 6, получено " + data.length);
        }
        return data;
    }

    private static void validateData(String[] data) throws InputMismatchException {
        if (!isAlpha(data[0])) {
            throw new InputMismatchException("Некорректный формат фамилии.");
        }
        if (!isAlpha(data[1])) {
            throw new InputMismatchException("Некорректный формат имени.");
        }
        if (!isAlpha(data[2])) {
            throw new InputMismatchException("Некорректный формат отчества.");
        }
        if (!isValidDate(data[3])) {
            throw new InputMismatchException("Некорректный формат даты рождения. Ожидается dd.mm.yyyy");
        }
        if (!isNumeric(data[4])) {
            throw new InputMismatchException("Некорректный формат номера телефона. Ожидается целое число.");
        }
        if (!isValidGender(data[5])) {
            throw new InputMismatchException("Некорректный формат пола. Ожидается f или m.");
        }
    }


    private static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDate(String str) {
        if (str.length() != 10) {
            return false;
        }
        if (str.charAt(2) != '.' || str.charAt(5) != '.') {
            return false;
        }
        String[] parts = str.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        return isNumeric(parts[0]) && parts[0].length() == 2 &&
                isNumeric(parts[1]) && parts[1].length() == 2 &&
                isNumeric(parts[2]) && parts[2].length() == 4;
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidGender (String str) {
        return str.equals("f") || str.equals("m");
    }

    private static void writeToFile (String[] data) throws IOException {
        String fileName = data[0] + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(String.join(" ", data));
            writer.newLine();
        }
    }
}
