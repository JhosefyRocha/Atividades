public class Pilha implements PilhaOperacoes {
    private String[] pilha;
    private int topo; // Controla o índice do último elemento

    public Pilha(int tamanho) {
        this.pilha = new String[tamanho];
        this.topo = -1; // Pilha começa vazia
    }

    // --- Métodos Auxiliares de Pilha ---

    public boolean estaCheia() {
        return topo == pilha.length - 1;
    }

    public boolean estaVazia() {
        return topo == -1;
    }

    // --- Implementação da Interface ListaOperacoes (Adaptada para Pilha) ---

    @Override
    public int adicionarVarios(String[] elementos) {
        int adicionados = 0;
        for (String elemento : elementos) {
            if (!estaCheia()) {
                topo++;
                pilha[topo] = elemento;
                adicionados++;
            } else {
                break; // Para se atingir a capacidade máxima
            }
        }
        return adicionados;
    }

    @Override
    public int contar() {
        return topo + 1;
    }

    @Override
    public String obter(int indice) {
        if (indice < 0 || indice > topo) {
            return null;
        }
        return pilha[indice];
    }

    @Override
    public boolean inserir(int indice, String elemento) {
        // Em uma pilha, inserir no meio exige deslocar os elementos para cima
        if (estaCheia() || indice < 0 || indice > topo + 1) {
            return false;
        }

        for (int i = topo; i >= indice; i--) {
            pilha[i + 1] = pilha[i];
        }
        pilha[indice] = elemento;
        topo++;
        return true;
    }

    @Override
    public String removerPorIndice(int indice) {
        if (indice < 0 || indice > topo) {
            return null;
        }
        String removido = pilha[indice];
        // Desloca elementos para preencher o buraco (manter a estrutura contígua)
        for (int i = indice; i < topo; i++) {
            pilha[i] = pilha[i + 1];
        }
        pilha[topo] = null;
        topo--;
        return removido;
    }

    @Override
    public int removerTodas(String elemento) {
        int removidos = 0;
        for (int i = 0; i <= topo; i++) {
            if (pilha[i].equals(elemento)) {
                removerPorIndice(i);
                i--; // Ajusta o índice após o deslocamento
                removidos++;
            }
        }
        return removidos;
    }

    @Override
    public void limpar() {
        // No vetor, basta resetar o topo e limpar as referências
        for (int i = 0; i <= topo; i++) {
            pilha[i] = null;
        }
        topo = -1;
    }

    @Override
    public int ultimoIndiceDe(String elemento) {
        for (int i = topo; i >= 0; i--) {
            if (pilha[i].equals(elemento)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int contarOcorrencias(String elemento) {
        int cont = 0;
        for (int i = 0; i <= topo; i++) {
            if (pilha[i].equals(elemento)) {
                cont++;
            }
        }
        return cont;
    }

    @Override
    public int substituir(String antigo, String novo) {
        int trocas = 0;
        for (int i = 0; i <= topo; i++) {
            if (pilha[i].equals(antigo)) {
                pilha[i] = novo;
                trocas++;
            }
        }
        return trocas;
    }

    // Método extra para visualização
    public void exibirPilha() {
        if (estaVazia()) {
            System.out.println("Pilha vazia.");
            return;
        }
        for (int i = topo; i >= 0; i--) {
            System.out.println("| " + pilha[i] + " |" + (i == topo ? " <- Topo" : ""));
        }
    }
}