
public class HashTable {
    HashNode[] tabela;
    int capacidade;
    int tamanho;
    int tipoHash;

    public HashTable(int capacidade, int tipoHash){
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.tipoHash = tipoHash;
        this.tabela = new HashNode[capacidade];
    }

    public int getHash(String chave){
        if (this.tipoHash == 1){
            return hashMulti(chave);
        } else {
            return hashPoli(chave);
        }
    }

    private int hashMulti(String chave){
        int h = 0;

        for (int i = 0; i <chave.length(); i++){
            char c = chave.charAt(i);
            h = (31 * h + c);
        }

        return (int) Math.abs(h % this.capacidade);
    }

    private int hashPoli(String chave){
        long h = 0;
        long p = 53;
        long pot = 1;

        for (int i = 0; i < chave.length(); i++){
            char c = chave.charAt(i);

            long valor = (long) c;

            h = (h + (valor * pot)) % capacidade;
            pot = (pot* p) % capacidade;
        }
        return (int) h;
    }

    public void put(String chave, int valor){
        int indice = getHash(chave);
        HashNode atual = tabela[indice];

        if(atual == null){
            tabela[indice] = new HashNode(chave, valor);
            tamanho++;
            return;
        }

        HashNode anterior = null;

        while(atual != null){
            if(atual.chave.equals(chave)){
                atual.valor = valor;
                return;
            }
            anterior = atual; 
            atual = atual.proximo;
        }

        anterior.proximo = new HashNode(chave,valor);
        tamanho++;
    }

    public Integer get(String chave){
        int indice = getHash(chave);
        HashNode atual = tabela[indice];

        while (atual != null){
            if(atual.chave.equals(chave)){
                return atual.valor;
            }
            atual = atual.proximo;
        }
        return null;
    }

    public boolean contemChave(String chave){
        return get(chave) != null;
    }
}
