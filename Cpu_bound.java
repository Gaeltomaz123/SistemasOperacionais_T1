public class Cpu_bound implements Processo {
    String nome;
    int tempo_total_CPU;
    int ordem;
    int prioridade;

    public Cpu_bound(String nome, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
    }

    public String getNome() {
        return nome;
    }
}
