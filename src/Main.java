import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

/*
Работа в коде с классами Path и Files,
так как у Files есть методы напрямую работающие со стороками в текстовых файлах.
Эту логику можно организовать и через Reader/Writer (что сначала и сделал, потом переделал),
но с использованием Path и Files посчитал более оптимальным для читаемости и количества кода.
 */

public class Main {

    public static void main(String[] args) throws IOException {

        String initialMessage = """
                Выберите режим работы программы:\s
                1 - Шифрование
                2 - Расшифровка
                AnyKey - Завершить работу
                """;

        System.out.println(initialMessage);
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine();


        if (mode.equals("1")) {

            System.out.println("Выберите шаг сдвига: "); //
            int shift = Integer.parseInt(scanner.nextLine()) % 41; //

            System.out.println("Введите путь к файлу:\n");
            Path source = Path.of(scanner.nextLine());
            Path dest = Encrypt.encryptFile(Encrypt.KEY, source, shift);

        } else if (mode.equals("2")) {

            System.out.println("Введите путь к файлу:\n");
            Path source = Path.of(scanner.nextLine());
            Path dest = Decrypt.brutForce(Decrypt.KEY, source);

        } else {
            System.exit(0);
        }

    }

}
