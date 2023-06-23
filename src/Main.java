import java.io.*;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.Scanner;

/*
Работа в коде с классами Path и Files,
так как у Files есть методы напрямую работающие со стороками в текстовых файлах.
Эту логику можно организовать и через Reader/Writer (что сначала и сделал, потом переделал),
но с использованием Path и Files посчитал более оптимальным для читаемости и количества кода.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        Menu.callMenu();
    }
}
