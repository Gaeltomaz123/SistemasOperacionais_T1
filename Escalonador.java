import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    Queue<Processo> ready;
    int temporizador = 0;
    List<Processo> finalizados = new ArrayList<>();
    int quantidade_processos;
    List<Processo> bloqueados = new ArrayList<>();
    // Cores texto
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // Construtor
    public Escalonador(Queue<Processo> ready) {
        this.ready = ready;
        this.quantidade_processos = ready.size();
    }

    public void executar() {
        // Selecionado é o processo running
        Processo selecionado = ready.poll();
        // Enquanto todos processos não derem exit continua o loop
        while(finalizados.size() != quantidade_processos) {
            // Incremento do temporizador
            temporizador++;
            // check é a variavel utilizada para nao contar quando o processo vai para blocked
            int check = 1;

            // In_Out
            if(selecionado instanceof Input_output) {
                int surto_count = ((Input_output) selecionado).getSurto_count();
                // Enquanto tiver creditos e surto de cpu para cumprir
                if(surto_count > 0 && selecionado.getCreditos() > 0) {
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    ((Input_output) selecionado).setSurto_count(surto_count - 1);
                    System.out.println(ANSI_BLUE + "Tempo: " + temporizador + " - Processo " + selecionado.getNome() + " - Créditos: " + selecionado.getCreditos() + " - Running" + ANSI_RESET);
                } else { // Quando terminar ou creditos ou surto de cpu vai para blocked
                    check = 0;
                    ((Input_output) selecionado).setSurto_count(((Input_output) selecionado).getSurto_CPU());
                    bloqueados.add(selecionado);
                    selecionado = ready.poll();
                    temporizador--;
                }
            }

            // CPU Bound
            else {
                // Se tiver creditos - running
                if (selecionado.getCreditos() > 0) {
                    selecionado.setCreditos(selecionado.getCreditos() - 1);
                    selecionado.setTempo_total_CPU(selecionado.getTempo_total_CPU() - 1);
                    System.out.println(ANSI_BLUE + "Tempo: " + temporizador  + " - Processo " + selecionado.getNome() + " - Créditos: " + selecionado.getCreditos() + " - Running" + ANSI_RESET);
                } else { // Quando acabar vai para a lista de prontos
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

            // Print dos processos na lista de prontos
            if(!ready.isEmpty() && check == 1) {
                for(Processo processo : ready) {
                    System.out.println(ANSI_GREEN + "Tempo: " + temporizador + " - Processo " + processo.getNome() + " - Créditos: " + processo.getCreditos() + " - Ready" + ANSI_RESET);
                }
            }

            // Se um processo chegar a 0 creditos, altera ordem  do mesmo para mais a alta, reeordenando todos os outros tambem de forma circular
            if(selecionado.getCreditos() == 0 && selecionado.getTempo_total_CPU() != 0) {
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
        
            
            // Se os processos que estao ready chegarem a 0 creditos, assim como o processo que está running, distribui mais creditos com base na formula cred = cred/2 + prio
            if(selecionado.getCreditos() == 0 && validoParaRedistribuicao(ready)) {
                selecionado.setCreditos((selecionado.getCreditos()/2) + selecionado.getPrioridade());
               
                for(Processo bloqueado : bloqueados) {
                    bloqueado.setCreditos((bloqueado.getCreditos()/2) + bloqueado.getPrioridade());
                   
                }
                for(Processo pronto : ready) {
                    pronto.setCreditos((pronto.getCreditos()/2) + pronto.getPrioridade());
                    
                }
                ready.add(selecionado);
                selecionado = ready.poll();
            }

            if(check==1) {
                System.out.println("-----------------------------------------------");
            }

            // Se processo finalizar seu tempo total de cpu, exit
            if(selecionado.getTempo_total_CPU() == 0) {
                System.out.println(ANSI_RED + "Tempo: " + (temporizador+1) + " - Processo " + selecionado.getNome() + " - Exit" + ANSI_RESET);
                finalizados.add(selecionado);
                if(!ready.isEmpty()) {
                    selecionado = ready.poll();
                }
            }

            
        }
    }

    // Funcao para checar se os processos que estao na lista de ready finalizaram seus creditos
    public boolean validoParaRedistribuicao(Queue<Processo>prontos){
        int count = 0;
        for(Processo processo : prontos) {
            if(processo.getCreditos() == 0) {
                count++;
            }
        }
        if(count == prontos.size())
            return true;
        return false;
    }
}
