import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class Serverv2 implements Runnable {
    private ArrayList<ConexaoHospedeiro> connections;
    private ServerSocket server;
    private boolean ok;
    private ExecutorService pool; //thread pool, coleção de threads em fila para executar lista de tarefas

    public Serverv2() {
        connections = new ArrayList<>();
        ok = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(9999); //socket de servidor para o servidor
            pool = Executor.ThreadPool();
            while (!ok) { //enquanto não estiver concluído, aceitar conexão
                Socket client = server.accept(); // retorna um socket do cliente e torna aceitável, uma thread
                ConexaoHospedeiro hospedeiro = new ConexaoHospedeiro(client);
                connections.add(hospedeiro);
                pool.execute(hospedeiro); //cada vez que adicionar uma conexão
            }
        } catch (IOException e) {
            desligar();
        }
    }

    //função de transmissão
    public void broadcast(String message) {
        //condição
        for (ConexaoHospedeiro ch : connections) {
            if (ch != null) {
                ((Object) ch).ch(message);
            }
        }
    }

    public void desligar() {
        try {
            ok = true;
            if (!server.isClosed()) {
                server.close();
            }

            for (ConexaoHospedeiro ch : connections) {
                ch.desligar();
            }
        } catch (IOException e) {
        }
    }

    class ConexaoHospedeiro implements Runnable {
        //construtor para manipular a conexão
        //o socket cliente privado
        //buffer será responsável pelo fluxo do socket

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nome;
        public ConexaoHospedeiro(Socket client) {
            this.client = client;
        }

        public void desligar() {
        }

        @Override
        public void run() {
            //iniciar fluxos de entrada e saída
            try{
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Olá!\n Entre com o seu Nome/Nickname: ");
                //para identificar os clientes no chat, vou pedir que o nome ou nickname
                nome = in.readLine();
                System.out.println(nome + " aderiu.");
                broadcast(nome + " Entrou no grupo!");
                //implementar algumas regras para chat
                // /nome significa que o usuário pretende alterar o seu nickname
                // se o usuário quiser sair da conversa, só terá de fazer o comando /sair
                // e caso não houver comando ou comando não existente para o sistema
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nome")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nome + " nickname alterado para " + messageSplit[1]);
                            System.out.println(nome + " nickname alterado para " + messageSplit[1]);

                            nome = messageSplit[1];
                        }
                    }
                }
            
        }
        catch(IOException e){
    }
}
    }
}