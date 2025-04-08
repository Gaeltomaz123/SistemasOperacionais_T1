import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    Queue<Processo> ready;
    int temporizador = 0;
    List<Processo> finalizados = new ArrayList<>();
    int quantidade_processos;
    List<Processo> bloqueados = new ArrayList<>();
    List<Processo> restart = new ArrayList<>();

    public Escalonador(Queue<Processo> ready) {
        this.ready = ready;
        this.quantidade_processos = ready.size();
    }

    public void executar() {
        Processo selecionado = ready.poll();
        while(finalizados.size() != quantidade_processos) {
            temporizador++;
            int check = 1;

            // In_Out
            if(selecionado instanceof Input_output) {
                int surto_count = ((Input_output) selecionado).getSurto_count();
                if(surto_count > 0 && selecionado.getCreditos() > 0) {
                    selecionado.setEstado(Estado.RUNNING);
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    ((Input_output) selecionado).setSurto_count(surto_count - 1);
                    System.out.println(temporizador + selecionado.getNome());
                }
                else {
                    check = 0;
                    ((Input_output) selecionado).setSurto_count(((Input_output) selecionado).getSurto_CPU());
                    bloqueados.add(selecionado);
                    selecionado = ready.poll();
                    temporizador--;
                }
            }

            // CPU Bound
            else {
                if (selecionado.getCreditos() > 0) {
                    selecionado.setEstado(Estado.RUNNING);
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    System.out.println(temporizador + selecionado.getNome());
                } else {
                    check = 0;
                    ready.add(selecionado);
                    selecionado = ready.poll();
                    temporizador--;
                }
                
            }

            // Bloqueados - Processo de E/S
            if(bloqueados.size() > 0 && check == 1) {
                Processo retirado = null;
                for (Processo processo : bloqueados) {
                    if(((Input_output) processo).getEs_count() > 0) {
                        System.out.println(temporizador + processo.getNome() + ((Input_output) processo).getEs_count());
                        ((Input_output) processo).setEs_count(((Input_output) processo).getEs_count() - 1);
                    } else {
                        ((Input_output) processo).setEs_count(((Input_output) processo).getTempo_es());
                        retirado = processo;
                        ready.add(processo);
                    }
                }
                bloqueados.remove(retirado);
            }

            //mudar
            if(selecionado.getCreditos() == 0) {
                restart.add(selecionado);
            }

            

            if(selecionado.getTempo_total_CPU() == 0) {
                System.out.println(selecionado.getNome() + " Terminou");
                finalizados.add(selecionado);
                if(!ready.isEmpty()) {
                    selecionado = ready.poll();
                }
            }
        }
    }
}
