
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Uso correto:");
            System.out.println("java Main <diretorio> <limiar> <modo> [opcoes]");
            System.out.println("Exemplos:");
            System.out.println("java Main ./docs 0.75 lista");
            System.out.println("java Main ./docs 0.80 topK 5");
            System.out.println("java Main ./docs 0.00 busca doc1.txt doc2.txt");
            return;
        }
        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2];

        List<Documento> documentos = carregarDocumentos(diretorio);

        if (documentos.isEmpty()) {
            System.out.println("Nenhum documento encontrado no diretório.");
            return;
        }

        AVLTree arvore = new AVLTree();
        int totalComparacoes = 0;

        if (modo.equalsIgnoreCase("busca")) {

            if (args.length < 5) {
                System.out.println("Uso: java Main <dir> 0.0 busca doc1.txt doc2.txt");
                return;
            }

            String d1 = args[3];
            String d2 = args[4];

            Documento doc1 = null, doc2 = null;

            for (Documento d : documentos) {
                if (d.getNome().equals(d1)) doc1 = d;
                if (d.getNome().equals(d2)) doc2 = d;
            }

            if (doc1 == null || doc2 == null) {
                System.out.println("Arquivos especificados não encontrados.");
                return;
            }

            double sim = ComparadorDeDocumentos.calcularSimilaridade(doc1, doc2);

            String saida =
                    "=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n" +
                    "Comparando: " + d1 + " <-> " + d2 + "\n" +
                    "Similaridade calculada: " + sim + "\n" +
                    "Métrica utilizada: Cosseno";

            System.out.println(saida);
            salvarEmArquivo(saida);
            return;
        }
        for (int i = 0; i < documentos.size(); i++) {
            for (int j = i + 1; j < documentos.size(); j++) {

                Documento A = documentos.get(i);
                Documento B = documentos.get(j);

                double sim = ComparadorDeDocumentos.calcularSimilaridade(A, B);

                Resultado r = new Resultado(A.getNome(), B.getNome(), sim);
                arvore.insert(sim, r);

                totalComparacoes++;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        sb.append("Total de documentos processados: ").append(documentos.size()).append("\n");
        sb.append("Total de pares comparados: ").append(totalComparacoes).append("\n");
        sb.append("Função hash utilizada: hashMultiplicativo\n");
        sb.append("Métrica de similaridade: Cosseno\n\n");

        if (modo.equalsIgnoreCase("lista")) {

            sb.append("Pares com similaridade acima de ").append(limiar).append(":\n");
            sb.append("--------------------------------------\n");

            listarAcimaDoLimiar(arvore, limiar, sb);
        }

        else if (modo.equalsIgnoreCase("topK")) {

            if (args.length < 4) {
                System.out.println("Uso: java Main <dir> <limiar> topK <K>");
                return;
            }

            int K = Integer.parseInt(args[3]);
            List<Resultado> top = coletarTodos(arvore);

            top.sort((a, b) -> Double.compare(b.similaridade, a.similaridade));

            sb.append("TOP ").append(K).append(" resultados:\n");
            sb.append("--------------------------------------\n");

            for (int i = 0; i < Math.min(K, top.size()); i++) {
                Resultado r = top.get(i);
                if (r.similaridade >= limiar) {
                    sb.append(r).append("\n");
                }
            }
        }

        // Mostrar rotação (exigência do relatório)
        sb.append("\n=== ROTAÇÕES DA AVL ===\n");
        sb.append("Simples Direita: ").append(arvore.RSDcount).append("\n");
        sb.append("Simples Esquerda: ").append(arvore.RSEcount).append("\n");
        sb.append("Dupla Direita: ").append(arvore.RDDcount).append("\n");
        sb.append("Dupla Esquerda: ").append(arvore.RDEcount).append("\n");

        // Print e arquivo
        System.out.println(sb);
        salvarEmArquivo(sb.toString());
    }
    private static List<Documento> carregarDocumentos(String dir) {

        List<Documento> docs = new ArrayList<>();
        File pasta = new File(dir);

        if (!pasta.exists() || !pasta.isDirectory()) return docs;

        for (File f : pasta.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
                docs.add(new Documento(f.getPath()));
            }
        }

        return docs;
    }

    private static void listarAcimaDoLimiar(AVLTree arvore, double limiar, StringBuilder sb) {
        listarAcimaRec(arvore.raiz, limiar, sb);
    }

    private static void listarAcimaRec(AVLNode no, double limiar, StringBuilder sb) {
        if (no == null) return;

        listarAcimaRec(no.esq, limiar, sb);

        if (no.chave >= limiar) {
            for (Resultado r : no.resultados) {
                sb.append(r).append("\n");
            }
        }

        listarAcimaRec(no.dir, limiar, sb);
    }

    private static List<Resultado> coletarTodos(AVLTree arvore) {
        List<Resultado> lista = new ArrayList<>();
        coletar(arvore.raiz, lista);
        return lista;
    }

    private static void coletar(AVLNode no, List<Resultado> lista) {
        if (no == null) return;

        coletar(no.esq, lista);

        lista.addAll(no.resultados);

        coletar(no.dir, lista);
    }

    private static void salvarEmArquivo(String conteudo) {
        try {
            FileWriter fw = new FileWriter("resultado.txt");
            fw.write(conteudo);
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo resultado.txt");
        }
    }
}