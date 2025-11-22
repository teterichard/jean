
public class AVLTree {
    
    public AVLNode raiz;
    public int RSDcount = 0;
    public int RSEcount = 0;
    public int RDDcount = 0;
    public int RDEcount = 0;
    
    public void insert(double chave, Resultado r){
        this.raiz = insertRec(this.raiz,chave,r);
    }

    private AVLNode insertRec(AVLNode no, double chave, Resultado r){
        if (no == null){
            return new AVLNode(chave, r);
        }

        if(chave < no.chave){
            no.esq = insertRec(no.esq, chave, r);
        } else if (chave > no.chave){
            no.dir = insertRec(no.dir, chave, r);
        } else {
            no.addResultado(r);
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));

        int fator = fatorBalanceamento(no);

        if (fator > 1 && chave < no.esq.chave){
            RSDcount++;
            return rD(no);
        }
        if (fator < -1 && chave > no.dir.chave){
            RSEcount++;
            return rE(no);
        }
        if (fator > 1 && chave > no.esq.chave){
            RDEcount++;
            no.esq = rE(no.esq);
            return rD(no);
        }
        if (fator > 1 && chave < no.esq.chave){
            RDDcount++;
            no.dir = rD(no.dir);
            return rE(no);
        }

        return no;
    }

    private int altura(AVLNode n){
        return (n == null ? 0 : n.altura);
    }

    private int fatorBalanceamento(AVLNode n){
        if(n == null) return 0;
        return altura(n.esq) - altura(n.dir);
    }

    private AVLNode rD(AVLNode y) {

        AVLNode x = y.esq;
        AVLNode T2 = x.dir;

        x.dir = y;
        y.esq = T2;

        y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;
        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;

        return x;
    }

    private AVLNode rE(AVLNode x) {

        AVLNode y = x.dir;
        AVLNode T2 = y.esq;

        y.esq = x;
        x.dir = T2;

        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;
        y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;

        return y;
    }

    public void listar(){
        listarRec(this.raiz);
    }

    private void listarRec(AVLNode no){
        if (no == null) return;

        listarRec(no.esq);

        System.out.println("Similaridade: " + no.chave);
        for (Resultado r : no.resultados){
            System.out.println(" -> " + r.getDoc1() + " <-> " + r.getDoc2() + " = " +  r.getSimilaridade());
        }

        listarRec(no.dir);
    }

    public void imprimirRotacoes() {
        System.out.println("=== Estatísticas de Rotações ===");
        System.out.println("Simples Direita: " + RSDcount);
        System.out.println("Simples Esquerda: " + RSEcount);
        System.out.println("Dupla Direita: " + RDDcount);
        System.out.println("Dupla Esquerda: " + RDEcount);
    }
}
