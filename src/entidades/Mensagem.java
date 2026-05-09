package entidades;

import fila.FilaMensagens;

import java.util.Scanner;

public class Mensagem {
    String nome;
    String contato;
    int motivo;
    String texto;

    public Mensagem(String nome, String texto, int motivo, String contato) {
        this.nome = nome;
        this.texto = texto;
        this.motivo = motivo;
        this.contato = contato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public int getMotivo() {
        return motivo;
    }

    public void setMotivo(int motivo) {
        this.motivo = motivo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "nome='" + nome + '\'' +
                ", contato='" + contato + '\'' +
                ", motivo=" + motivo +
                ", texto='" + texto + '\'' +
                '}';
    }
}
