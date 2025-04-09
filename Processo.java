public interface Processo {
    public String getNome();
    public int getTempo_total_CPU();
    public int getOrdem();
    public int getPrioridade();
    public int getCreditos();
    public void setCreditos(int novos_creditos);
    public void setTempo_total_CPU(int novo_tempo);
    public void setOrdem(int nova_ordem);
}