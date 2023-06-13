import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Decrypt {

    public static final String KEY = Encrypt.KEY;

    public static Path brutForce(String key, Path source) throws IOException {

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

            for (char c : dest.toCharArray()) {
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

    public static Path statForce(String key, Path encryptedFile) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к вспомогательному файлу: ");

        Path auxFile = Path.of(scanner.nextLine());

        Map<Character, Float> auxFileStat = getStat(auxFile, key);
        Map<Character, Float> encryptedFileStat = getStat(encryptedFile, key);
        Map<Character, Character> keyMap = findMatches(encryptedFileStat, auxFileStat);

        String string = Files.readString(encryptedFile).toLowerCase();
        StringBuilder sb = new StringBuilder(string);

        for (int i = 0; i < sb.length();i++){
            Character newChar = keyMap.get(sb.charAt(i));
            if (newChar == null) continue;
            sb.setCharAt(i, newChar);
        }

        Path decryptedFile = Path.of("decrypted_" + encryptedFile.getFileName());
        Files.writeString(decryptedFile, sb.toString());
        return decryptedFile;
    }

    public static Map<Character, Character> findMatches(Map<Character, Float> encryptedMap, Map<Character, Float> auxMap) throws IOException {

        Map<Character, Character> keyMap = new HashMap<>();
        float diff = Float.MAX_VALUE;

        for (var encryptedKey : encryptedMap.entrySet()) {
            for (var auxKey : auxMap.entrySet()) {
                if (Math.abs(encryptedKey.getValue() - auxKey.getValue()) < diff && !keyMap.containsValue(auxKey.getKey())) {
                    keyMap.put(encryptedKey.getKey(), auxKey.getKey());
                    diff = Math.abs(encryptedKey.getValue() - auxKey.getValue());
                }
            }
            diff = Float.MAX_VALUE;
        }
        return keyMap;
    }

    public static Map<Character, Float> getStat(Path path, String key) throws IOException {

        String string = Files.readString(path).toLowerCase();
        Map<Character, Float> statMap = new HashMap<>();

        for (char c : key.toCharArray()) {
            statMap.put(c, 0.0f);
        }

        for (char c : string.toCharArray()) {
            if (statMap.containsKey(c)) statMap.replace(c, statMap.get(c) + 1);
        }

        for (var c : statMap.keySet()) {
            float stat = Math.round((statMap.get(c) / string.length() * 100) * 100000.00f) / 100000.00f;
            statMap.replace(c, stat);
        }

        return statMap;
    }

}
