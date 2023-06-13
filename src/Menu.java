import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Menu {

    public static void callMenu () throws IOException {
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
            callSubMenu();
        } else {
            System.exit(0);
        }
    }


    public static void callSubMenu() throws IOException {

        System.out.println("Введите путь к файлу:\n");
        Scanner scanner = new Scanner(System.in);
        Path source = Path.of(scanner.nextLine());

        System.out.println("""
                Выберите метод подбора ключа:\s
                1 - BrutForce
                2 - На основе статистики
                AnyKey - Завершить работу
                """);

        String mode = scanner.nextLine();

        if (mode.equals("1")) Decrypt.brutForce(Encrypt.KEY, source);
        if (mode.equals("2")) Decrypt.statForce(Encrypt.KEY, source);

    }
}
