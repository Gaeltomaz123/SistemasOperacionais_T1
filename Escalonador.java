public class Escalonador {
    public static void main(String[] args) {
        // Criação de processos

        // Entrada e saída
        Processo a = new Input_output("A", 2, 5, 6, 1, 3);
        Processo b = new Input_output("B", 3, 10, 6, 2, 3);

        // Cpu_bound
        Processo c = new Cpu_bound("C", 14, 3, 3);
        Processo d = new Cpu_bound("D", 10, 4, 3);


        System.out.println(a.getNome());
    }
}
