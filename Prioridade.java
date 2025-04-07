import java.util.Comparator;

public class Prioridade implements Comparator<Processo> {
    @Override
    public int compare(Processo p1, Processo p2) {
        int cmp = Integer.compare(p2.getCreditos(), p1.getCreditos());
        if (cmp == 0) {
            return Integer.compare(p1.getOrdem(), p2.getOrdem());
        }
        return cmp;
    }
}
