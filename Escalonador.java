import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    Queue<Processo> ready;
    int temporizador = 0;
    List<Processo> finalizados = new ArrayList<>();
    int quantidade_processos;

    public Escalonador(Queue<Processo> ready) {
        this.ready = ready;
        this.quantidade_processos = ready.size();
    }

    public void executar() {
        Processo selecionado = ready.poll();
        while(finalizados.size() != quantidade_processos) {
            if(selecionado.getCreditos() > 0) {
                selecionado.setEstado(Estado.RUNNING);
                selecionado.setCreditos(selecionado.getCreditos() - 1);
                temporizador++;
                System.out.println(selecionado.getNome());
            }
            else {
                finalizados.add(selecionado);
                selecionado = ready.poll();
            }
        }
    }

}
