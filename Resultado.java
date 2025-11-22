

public class Resultado {

    public String doc1;
    public String doc2;
    public double similaridade;

    public Resultado(String doc1, String doc2, double similaridade) {
        this.doc1 = doc1;
        this.doc2 = doc2;
        this.similaridade = similaridade;
    }

    public String getDoc1() {
        return doc1;
    }

    public String getDoc2() {
        return doc2;
    }

    public double getSimilaridade() {
        return similaridade;
    }

    @Override
    public String toString() {
        return doc1 + " <-> " + doc2 + " = " + similaridade;
    }
}
