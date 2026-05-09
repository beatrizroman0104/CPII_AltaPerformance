package fila;

import entidades.Mensagem;

public class FilaMensagens {
    Mensagem[] dados;
    int inicio, fim, capacidade;


    public FilaMensagens() {
        init(100); // capacidade padrão
    }

    public void init(int cap) {
        capacidade = cap + 1; // uma posição extra para distinguir full/empty
        dados = new Mensagem[capacidade];
        inicio = 0;
        fim = 0;
    }

    public boolean isEmpty() {
        return inicio == fim;
    }

    public boolean isFull() {
        return (fim + 1) % capacidade == inicio;
    }

    public boolean enqueue(Mensagem elem) {
        if (isFull()) {
            return false;
        } else {
            dados[fim] = elem;
            fim = (fim + 1) % capacidade;
            return true;
        }
    }

    public Mensagem dequeue() {
        if (isEmpty()) {
            return null;
        } else{
            Mensagem m = dados[inicio];
            dados[inicio] = null;
            inicio = (inicio + 1) % capacidade;
            return m;
        }
    }

    // opcional: tamanho
    public int size() {
        if (fim >= inicio){
            return fim - inicio;
        } else{
            return capacidade - (inicio - fim);
        }
    }
}
