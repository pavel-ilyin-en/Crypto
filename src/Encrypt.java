import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encrypt {

    public static final String KEY = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";

    public static Path encryptFile (String key, Path source, int shift) throws IOException {

        String sourceString = Files.readString(source);
        String dest = Encrypt.encryptString(sourceString, Encrypt.KEY, shift);

        Path destFile = Path.of("encrypted_" + source.getFileName());
        Files.createFile(destFile);
        Files.writeString(destFile, dest);

        return destFile;
    }


    public static String encryptString (String s, String key, int shift) {

        StringBuilder sb = new StringBuilder(s.toLowerCase());

        /*

        Почему здесь строку преобразовываю в StringBuilder:

        Можно всё сделать и в String через метод replace (char oldChar, char newChar),
        но поскольку объекты String immutable, будет создано большое количество мусора,
        а именно столько мусорных объектов String сколько символов в ключе, поскольку для каждого нужно
        символа применить replace, в нашем случае это 41.

        Поэтому преобразовываем строку в StringBuilder, делаем необходимые замены, преобразовываем назад в String.

        */

        for (int i = 0; i < sb.length(); i++) {
            if (key.indexOf(sb.charAt(i)) == -1) continue;
            int newIndex = (key.indexOf(sb.charAt(i)) + shift) % key.length();
            sb.setCharAt(i, key.charAt(newIndex));
        }

        return sb.toString();
    }

}
