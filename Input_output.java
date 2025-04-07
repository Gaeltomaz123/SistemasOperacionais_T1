public class Input_output implements Processo {
    String nome;
    int surto_CPU;
    int tempo_es;
    int tempo_total_CPU;
    int ordem;
    int prioridade;
    Estado estado;
    int creditos;

    public Input_output(String nome, int surto_CPU, int tempo_es, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.surto_CPU = surto_CPU;
        this.tempo_es = tempo_es;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.estado = Estado.READY;
        this.creditos = prioridade;
    }

    public String getNome() {
        return nome;
    }

    public int getSurto_CPU() {
        return surto_CPU;
    }

    public int getTempo_es() {
        return tempo_es;
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
}
