import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // Criação de processos
        Queue<Processo> processos = new PriorityQueue<>(new Prioridade());
        processos.add(new Input_output("A", 2, 5, 6, 1, 3));
        processos.add(new Input_output("B", 3, 10, 6, 2, 3));
        processos.add(new Cpu_bound("C", 14, 3, 3));
        processos.add(new Cpu_bound("D", 10, 4, 3));

        Escalonador escalonador = new Escalonador(processos);
        escalonador.executar();
    }
}
