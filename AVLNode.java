import java.util.ArrayList;
import java.util.List;


public class AVLNode {
    double chave;
    List<Resultado> resultados;
    AVLNode esq, dir;
    int altura;

    public AVLNode(double chave, Resultado inicial){
        this.chave = chave;
        this.resultados = new ArrayList<>();
        this.resultados.add(inicial);
        this.esq = null;
        this.dir = null;
        this.altura = 1;
    }

    public void addResultado(Resultado r){
        this.resultados.add(r);
    }

    public int getAltura(){
        return this.altura;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }
}
