package aplicacao;

import entidades.Mensagem;
import fila.FilaMensagens;

import java.util.Scanner;

public class AtendimentoMensagem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FilaMensagens filaReclamacao = new FilaMensagens();
        FilaMensagens filaSugestao = new FilaMensagens();
        FilaMensagens filaResolucao = new FilaMensagens();

        filaReclamacao.init(100);
        filaSugestao.init(100);
        filaResolucao.init(100);

        boolean encerra = false;

        while (!encerra) {
            System.out.println("\n----- Menu -----");
            System.out.println("0-Encerra o programa");
            System.out.println("1-Recebimento de Mensagem");
            System.out.println("2-Atendimento de Mensagem");
            System.out.println("3-Recebimento e Encaminhamento de Resolução");
            System.out.print("Escolha: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 0:
                    if (filaReclamacao.isEmpty() && filaSugestao.isEmpty() && filaResolucao.isEmpty()) {
                        System.out.println("Encerrando programa!");
                        encerra = true;
                    } else {
                        System.out.println("Ainda há mensagens nas filas. Remova antes de encerrar.");
                    }
                    break;

                case 1:
                    receberMensagem(sc, filaReclamacao, filaSugestao);
                    break;

                case 2:
                    atendimentoMensagem(sc, filaReclamacao, filaSugestao, filaResolucao);
                    break;

                case 3:
                    recebimentoResolucao(filaResolucao);
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }

    private static void receberMensagem(Scanner sc, FilaMensagens filaReclamacao, FilaMensagens filaSugestao) {
        System.out.println("\n--- Recebimento de Mensagem ---");
        System.out.print("Meio (1=email / 2=telefone): ");
        int meio = sc.nextInt();
        sc.nextLine();

        String nome = null;
        System.out.print("Deseja informar nome? (s/n): ");
        String resp = sc.nextLine().trim();
        if (resp.equalsIgnoreCase("s")) {
            System.out.print("Nome: ");
            nome = sc.nextLine().trim();
            if (nome.isEmpty()){
                nome = null;
            }
        }

        String contato;
        if (meio == 1) {
            System.out.print("Email: ");
            contato = sc.nextLine().trim();
        } else {
            System.out.print("Telefone: ");
            contato = sc.nextLine().trim();
        }

        int motivo;
        while (true) {
            System.out.print("Motivo do contato (1-reclamação / 2-sugestão): ");
            motivo = sc.nextInt();
            sc.nextLine();
            if (motivo == 1 || motivo == 2) {
                break;
            } else{
                System.out.println("Motivo inválido. Digite 1 ou 2.");
            }
        }

        System.out.print("Mensagem (texto): ");
        String texto = sc.nextLine().trim();

        Mensagem m = new Mensagem(nome, contato, motivo, texto);

        boolean ok;
        if (motivo == 1) {
            ok = filaReclamacao.enqueue(m);
            if (!ok) {
                System.out.println("Fila de reclamações cheia. Mensagem não recebida.");
            }
            else {
                System.out.println("Mensagem de reclamação recebida e enfileirada.");
            }
        } else {
            ok = filaSugestao.enqueue(m);
            if (!ok) {
                System.out.println("Fila de sugestões cheia. Mensagem não recebida.");
            }
            else {
                System.out.println("Mensagem de sugestão recebida e enfileirada.");
            }
        }
    }

    private static void atendimentoMensagem(Scanner sc, FilaMensagens filaReclamacao, FilaMensagens filaSugestao, FilaMensagens filaResolucao) {
        System.out.println("\n--- Atendimento de Mensagem ---");
        System.out.print("Escolha tipo para atendimento (1=reclamação / 2=sugestão): ");
        int tipo = sc.nextInt();
        sc.nextLine();
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido.");
            return;
        }

        FilaMensagens filaEscolhida = (tipo == 1) ? filaReclamacao : filaSugestao;
        if (filaEscolhida.isEmpty()) {
            System.out.println("Não há mensagens desse tipo para atendimento.");
            return;
        }

        Mensagem m = filaEscolhida.dequeue();
        System.out.println("Atendendo mensagem de: " + (m.getNome() == null ? m.getContato() : m.getNome()));
        System.out.println("Conteúdo: " + m.getTexto());

        System.out.print("Resposta pronta? (1=pronta / 2=encaminhar ao setor): ");
        int acao = sc.nextInt();
        sc.nextLine();
        if (acao == 1) {
            System.out.println("Enviada resposta para cliente: sua solicitação já foi resolvida. Obrigado!!!");
        } else if (acao == 2) {
            boolean ok = filaResolucao.enqueue(m);
            if (!ok) {
                System.out.println("Fila de resolução cheia. Não foi possível encaminhar.");
                // em caso de falha re-enfileirar na fila original (para não perder)
                filaEscolhida.enqueue(m);
            } else {
                System.out.println("Cliente informado que em breve receberá resposta; encaminhado para filaResolucao.");
            }
        } else {
            System.out.println("Ação inválida. Recolocando mensagem na fila original.");
            filaEscolhida.enqueue(m);
        }
    }

    private static void recebimentoResolucao(FilaMensagens filaResolucao) {
        System.out.println("\n--- Recebimento e Encaminhamento de Resolução ---");
        if (filaResolucao.isEmpty()) {
            System.out.println("Não há mensagens aguardando resolução.");
            return;
        }
        Mensagem m = filaResolucao.dequeue();
        System.out.println("Enviada resposta para cliente: sua solicitação já foi resolvida pelo setor responsável. Obrigado!!!");
        System.out.println("Mensagem resolvida: " + (m.getNome() == null ? m.getContato() : m.getNome()));
    }
}