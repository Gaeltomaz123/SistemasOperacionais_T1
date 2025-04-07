public class Input_output implements Processo {
    String nome;
    int surto_CPU;
    int tempo_es;
    int tempo_total_CPU;
    int ordem;
    int prioridade;

    public Input_output(String nome, int surto_CPU, int tempo_es, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.surto_CPU = surto_CPU;
        this.tempo_es = tempo_es;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
    }

    public String getNome() {
        return nome;
    }
}
