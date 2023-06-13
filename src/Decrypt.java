import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decrypt {

    public static final String KEY = Encrypt.KEY;

    public static Path brutForce (String key, Path source) throws IOException {

        /* Анализ подбора шифра основан на частотности пробелов в тексте.
        Пробел - самый высокочастотный символ с большим отрывом от частоты других символов.
        Метод работает на текстах примерно от 200 символов, иначе недостаточно статистики.
        Также можно сделать анализ по встречаемости пробела после знака препинания,
        например по таким substring как
        ". "
        ", "
        "! "
         */

        String sourceString = Files.readString(source);

        Path destFile = Path.of("decrypted_" + source.getFileName());
        Files.createFile(destFile);

        for (int i = 0; i < key.length() - 2; i++) {

            //строка ниже будет генерировать много мусорных объектов String, по одному в каждом витке цикла, такая жертва )
            String dest = Encrypt.encryptString(sourceString, key, i);

            int spaceFrequency = 0;

            for (char c : dest.toCharArray()){
                if (c == ' ') {
                    spaceFrequency++;
                }
            }

            if (spaceFrequency > 13) {
                Files.writeString(destFile, dest);
                break;
            }
        }
        return destFile;
    }

    public static Path statisticalDecrypt (String key, Path source, Path known) throws IOException{

        /*
        Статистический анализ будет работать так:

        Создаётся объект Map в котором ключ это символ а значение это его частота встречаемости в тексте.
        Проходом по тексту подгруженного файла собирается частота каждого символа, добавляется в Map.

        Делается проход по зашифрованному файлу, также собирается частота по каждому символу.
        Сопоставляется со значением каждой пары в предыдущей Map, наименьшая разница принимается как удачный подбор ключа.

        Залью в следующем коммите.

        */

        return null;
    }
}
