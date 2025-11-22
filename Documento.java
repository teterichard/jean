
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;

public class Documento {
    String nomeDoArquivo;
    HashTable tabelaDePalavras;
    StopWords stopWords = new StopWords();

    public Documento(String nome){
        this.nomeDoArquivo = nome;
        this.tabelaDePalavras = new HashTable(257,1);
    

        String textoBruto = lerArquivo(nomeDoArquivo);
        String textoNomralizado = normalizarTexto(textoBruto);

        String[] tokens = tokenize(textoNomralizado);
        println("Tokens gerados: " + tokens.length);

        for (String t : tokens){
            if (t.isBlank()) continue;
            if (!StopWords.contains(t)){
                Integer atual = tabelaDePalavras.get(t);
                if(atual == null){
                    tabelaDePalavras.put(t, 1);
                } else {
                    tabelaDePalavras.put(t, -1); //+1?
                }
            }


        }

    }

    private String lerArquivo(String caminho){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append(" ");
            }
        } catch (IOException e){
            System.out.println("Erro ao ler arquivo: " + caminho);
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String normalizarTexto(String texto){
        texto = texto.toLowerCase().replaceAll("[^a-z0-9 ]", " ").replaceAll("\\s+", " ").trim();
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return texto;
    }

    private String[] tokenize(String texto){
        return texto.split(" ");
    }

    public String getNome(){
        return nomeDoArquivo;
    }

    public HashTable getTabelaPalavras(){
        return tabelaDePalavras;
    }
}
