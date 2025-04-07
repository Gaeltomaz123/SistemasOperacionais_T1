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
                if(selecionado instanceof Input_output) {
                    int surto_inicial = ((Input_output) selecionado).getSurto_inicial();
                    if(surto_inicial > 0) {
                        selecionado.setEstado(Estado.RUNNING);
                        selecionado.setCreditos(selecionado.getCreditos() - 1);
                        ((Input_output) selecionado).setSurto_inicial(surto_inicial - 1);
                        //System.out.println(temporizador + selecionado.getNome());
                    }
                    else {
                        ((Input_output) selecionado).setSurto_inicial(((Input_output) selecionado).getSurto_CPU());
                    }
                } else {
                    selecionado.setEstado(Estado.RUNNING);
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    System.out.println(temporizador + selecionado.getNome());
                }
                temporizador++;
            }
            else {
                finalizados.add(selecionado);
                selecionado = ready.poll();
            }
        }
    }

}
