import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    Queue<Processo> ready;
    int temporizador = 0;
    List<Processo> finalizados = new ArrayList<>();
    int quantidade_processos;
    List<Processo> bloqueados = new ArrayList<>();
    int restart = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

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
                    
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    ((Input_output) selecionado).setSurto_count(surto_count - 1);
                    System.out.println(ANSI_BLUE + "Tempo: " + temporizador + " - Processo " + selecionado.getNome() + " - Créditos: " + selecionado.getCreditos() + " - Running" + ANSI_RESET);
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
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    System.out.println(ANSI_BLUE + "Tempo: " + temporizador  + " - Processo " + selecionado.getNome() + " - Créditos: " + selecionado.getCreditos() + " - Running" + ANSI_RESET);
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
                        System.out.println(ANSI_YELLOW + "Tempo: " + temporizador + " - Processo " + processo.getNome() + " - Créditos: " + processo.getCreditos() + " - Blocked" + ANSI_RESET);
                        ((Input_output) processo).setEs_count(((Input_output) processo).getEs_count() - 1);
                    } else {
                        ((Input_output) processo).setEs_count(((Input_output) processo).getTempo_es());
                        retirado = processo;
                        ready.add(processo);
                    }
                }
                bloqueados.remove(retirado);
            }

            if(!ready.isEmpty() && check == 1) {
                for(Processo processo : ready) {
                    System.out.println(ANSI_GREEN + "Tempo: " + temporizador + " - Processo " + processo.getNome() + " - Créditos: " + processo.getCreditos() + " - Ready" + ANSI_RESET);
                }
            }

            //mudar
            if(selecionado.getCreditos() == 0 && selecionado.getTempo_total_CPU() != 0) {
                restart++;
                while(selecionado.getOrdem() < quantidade_processos) {
                    selecionado.setOrdem(selecionado.getOrdem() + 1);
                    for(Processo bloqueado : bloqueados) {
                        bloqueado.setOrdem((bloqueado.getOrdem() + 1) % quantidade_processos);
                    }
                    for(Processo pronto : ready) {          
                        pronto.setOrdem((pronto.getOrdem() + 1) % quantidade_processos);
                    }
                }
              
                
            }
        
            System.out.println("restart: " + restart);
            System.out.println("quant: " + quantidade_processos);
            
            if(restart == quantidade_processos) {
                selecionado.setCreditos((selecionado.getCreditos()/2) + selecionado.getPrioridade());
               
                for(Processo bloqueado : bloqueados) {
                    bloqueado.setCreditos((bloqueado.getCreditos()/2) + bloqueado.getPrioridade());
                   
                }
                for(Processo pronto : ready) {
                    pronto.setCreditos((pronto.getCreditos()/2) + pronto.getPrioridade());
                    
                }
                ready.add(selecionado);
                restart = 0;
                selecionado = ready.poll();
            }

            if(selecionado.getTempo_total_CPU() == 0) {
                System.out.println(ANSI_RED + "Tempo: " + (temporizador+1) + " - Processo " + selecionado.getNome() + " - Exit" + ANSI_RESET);
                finalizados.add(selecionado);
                if(!ready.isEmpty()) {
                    selecionado = ready.poll();
                }
            }
        }
    }
}
