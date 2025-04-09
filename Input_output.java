public class Input_output implements Processo {
    String nome;
    int surto_CPU;
    int tempo_es;
    int tempo_total_CPU;
    int ordem;
    int prioridade;
    int creditos;
    int surto_count;
    int es_count;

    public Input_output(String nome, int surto_CPU, int tempo_es, int tempo_total_CPU, int ordem, int prioridade) {
        this.nome = nome;
        this.surto_CPU = surto_CPU;
        this.tempo_es = tempo_es;
        this.tempo_total_CPU = tempo_total_CPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.creditos = prioridade;
        this.surto_count = surto_CPU;
        this.es_count = tempo_es;
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

    public int getCreditos() {
        return creditos;
    }

    public int getSurto_count() {
        return surto_count;
    }

    public int getEs_count() {
        return es_count;
    }

    public void setCreditos(int novos_creditos) {
        creditos = novos_creditos;
    }


    public void setSurto_count(int novo_surto_count) {
        surto_count = novo_surto_count;
    }

    public void setEs_count(int novo_es_count) {
        es_count = novo_es_count;
    }

    public void setTempo_total_CPU(int novo_tempo) {
        tempo_total_CPU = novo_tempo;
    }

    public void setOrdem(int nova_ordem) {
        ordem = nova_ordem;
    }
}
