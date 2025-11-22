

public class StopWords {

    private static final HashTable palavras = new HashTable(257, 1);
    static {
        palavras.put("a", 1);
        palavras.put("o", 1);
        palavras.put("as", 1);
        palavras.put("os", 1);
        palavras.put("um", 1);
        palavras.put("uma", 1);
        palavras.put("de", 1);
        palavras.put("do", 1);
        palavras.put("da", 1);
        palavras.put("das", 1);
        palavras.put("dos", 1);
        palavras.put("em", 1);
        palavras.put("no", 1);
        palavras.put("na", 1);
        palavras.put("nos", 1);
        palavras.put("nas", 1);
        palavras.put("para", 1);
        palavras.put("por", 1);
        palavras.put("com", 1);
        palavras.put("sem", 1);
        palavras.put("sobre", 1);
        palavras.put("e", 1);
        palavras.put("ou", 1);
        palavras.put("mas", 1);
        palavras.put("como", 1);
        palavras.put("que", 1);
        palavras.put("se", 1);
        palavras.put("porque", 1);
        palavras.put("pois", 1);
        palavras.put("ele", 1);
        palavras.put("ela", 1);
        palavras.put("eles", 1);
        palavras.put("elas", 1);
        palavras.put("isso", 1);
        palavras.put("isto", 1);
        palavras.put("aquilo", 1);
        palavras.put("este", 1);
        palavras.put("esta", 1);
        palavras.put("esses", 1);
        palavras.put("essas", 1);
        palavras.put("aquele", 1);
        palavras.put("aquela", 1);
        palavras.put("já", 1);
        palavras.put("sempre", 1);
        palavras.put("nunca", 1);
        palavras.put("ainda", 1);
        palavras.put("também", 1);
        palavras.put("muito", 1);
        palavras.put("mais", 1);
        palavras.put("menos", 1);
    }
    public static boolean contains(String palavra) {
        return palavras.contemChave(palavra);
    }
}