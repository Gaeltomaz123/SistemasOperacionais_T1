public class Cpu_bound implements Processo {
    String nome;
    int tempo_total_CPU;
    int ordem;
    int prioridade;
    Estado estado;
    int creditos;

    public Cpu_bound(String nome, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.estado = Estado.READY;
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

    public Estado getEstado() {
        return estado;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int novos_creditos) {
        creditos = novos_creditos;
    }

    public void setEstado(Estado novo_estado) {
        estado = novo_estado;
    }

    public void setTempo_total_CPU(int novo_tempo) {
        tempo_total_CPU = novo_tempo;
    }
}
