

public class ComparadorDeDocumentos {
    public static double calcularSimilaridade(Documento d1, Documento d2){
        HashTable t1 = d1.getTabelaPalavras();
        HashTable t2 = d2.getTabelaPalavras();
        double produtoEscalar = calcularProdutoEscalar(t1, t2);
        double norma1 = calculaNorma(t1);
        double norma2 = calculaNorma(t2);

        if (norma1 == 0 || norma2 == 0){
            return 0.0;
        }

        return produtoEscalar / (norma1 * norma2);
    }

    private static double calcularProdutoEscalar(HashTable ht1, HashTable ht2){
        double soma = 0.0;

        for (int i = 0; i < ht1.capacidade; i++){
            HashNode atual = ht1.tabela[i];

            while(atual != null){
                String palavra = atual.chave;
                int frenq1 = atual.valor;
                Integer frenq2 = ht2.get(palavra);
                if (frenq2 != null){
                    soma += frenq1 * frenq2;
                }

                atual = atual.proximo;
            }
        }
        return soma;

    }

    private static double calculaNorma(HashTable tabela){
        double soma =0.0;
        for (int i = 0; i <tabela.capacidade; i++){
            HashNode atual = tabela.tabela[i];
            while (atual != null) {
                int freq = atual.valor;
                soma += (freq * freq);
                atual = atual.proximo;
            }
        }
        return Math.sqrt(soma);
    }
}
