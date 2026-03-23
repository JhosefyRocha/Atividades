public class PilhaDinamica implements PilhaOperacoes {
    private No topo; // Referência para o elemento no topo da pilha
    private int tamanho;

    public PilhaDinamica() {
        this.topo = null;
        this.tamanho = 0;
    }

    // --- Métodos de Contrato da Interface ---

    @Override
    public int adicionarVarios(String[] elementos) {
        int adicionados = 0;
        for (String valor : elementos) {
            // No encadeamento dinâmico, o "empurrar" (push) é sempre no topo
            No novoNo = new No(valor);
            novoNo.proximo = topo;
            topo = novoNo;
            tamanho++;
            adicionados++;
        }
        return adicionados;
    }

    @Override
    public int contar() {
        return this.tamanho;
    }

    @Override
    public String obter(int indice) {
        if (indice < 0 || indice >= tamanho) return null;

        No atual = topo;
        // Como o topo é o "último", o índice 0 seria o fundo da pilha
        // ou o topo dependendo da sua convenção. Aqui seguiremos a ordem do topo (0) para o fundo.
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }

    @Override
    public boolean inserir(int indice, String elemento) {
        if (indice < 0 || indice > tamanho) return false;

        No novoNo = new No(elemento);
        if (indice == 0) { // Inserir no topo
            novoNo.proximo = topo;
            topo = novoNo;
        } else {
            No anterior = topo;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.proximo;
            }
            novoNo.proximo = anterior.proximo;
            anterior.proximo = novoNo;
        }
        tamanho++;
        return true;
    }

    @Override
    public String removerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanho) return null;

        String removido;
        if (indice == 0) { // Remover do topo
            removido = topo.valor;
            topo = topo.proximo;
        } else {
            No anterior = topo;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.proximo;
            }
            removido = anterior.proximo.valor;
            anterior.proximo = anterior.proximo.proximo;
        }
        tamanho--;
        return removido;
    }

    @Override
    public int removerTodas(String elemento) {
        int removidos = 0;
        // Tratando o topo repetidamente se necessário
        while (topo != null && topo.valor.equals(elemento)) {
            topo = topo.proximo;
            tamanho--;
            removidos++;
        }

        No atual = topo;
        while (atual != null && atual.proximo != null) {
            if (atual.proximo.valor.equals(elemento)) {
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                removidos++;
            } else {
                atual = atual.proximo;
            }
        }
        return removidos;
    }

    @Override
    public void limpar() {
        this.topo = null; // O Garbage Collector do Java limpa os nós órfãos
        this.tamanho = 0;
    }

    @Override
    public int ultimoIndiceDe(String elemento) {
        int indiceUltimo = -1;
        No atual = topo;
        for (int i = 0; i < tamanho; i++) {
            if (atual.valor.equals(elemento)) {
                indiceUltimo = i;
            }
            atual = atual.proximo;
        }
        return indiceUltimo;
    }

    @Override
    public int contarOcorrencias(String elemento) {
        int cont = 0;
        No atual = topo;
        while (atual != null) {
            if (atual.valor.equals(elemento)) cont++;
            atual = atual.proximo;
        }
        return cont;
    }

    @Override
    public int substituir(String antigo, String novo) {
        int trocas = 0;
        No atual = topo;
        while (atual != null) {
            if (atual.valor.equals(antigo)) {
                atual.valor = novo;
                trocas++;
            }
            atual = atual.proximo;
        }
        return trocas;
    }
}