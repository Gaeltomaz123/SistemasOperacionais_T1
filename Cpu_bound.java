public class Cpu_bound implements Processo {
    String nome;
    int tempo_total_CPU;
    int ordem;
    int prioridade;
    int creditos;

    public Cpu_bound(String nome, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.creditos = prioridade;
    }

    public String getNome() {
        return nome;
    }

    public int getTempo_total_CPU() {
        return tempo_total_CPU;
    }

    public int getOrdem() {
        return ordem;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int novos_creditos) {
        creditos = novos_creditos;
    }

    public void setTempo_total_CPU(int novo_tempo) {
        tempo_total_CPU = novo_tempo;
    }

    public void setOrdem(int nova_ordem) {
        ordem = nova_ordem;
    }
}
