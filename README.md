# Socket_java
TCP-são protocolos de redes que permitem a comunicação entre dispositivos
em uma rede, é um protocolo de transporte orientado a conexão, o que significa que ele estabelece a conexão antes de enviar dados e garante a entrega e a ordem dos pacotes. A confiabilidade é mais importante do que a velocidade, exmplo: transações bancarias online. 

UDP-user datagrama protocolo, é um protocolo de transporte sem conexão, o que significa que não existe conexão antes de enviar dados, ele simplesmente envia pacotes(datagrama) para o destino, sem garantir a entrega e a ordem dos pacotes. Adequada para casos em que a velocidade é mais importante do que confiabilidade, explo: Livestream 

para ser possível a criação de um sistema socket em java que suporta os protocolos 

precisa-se de uma classe DatagramSocket e ServerSocket, o DatagramSocket é usado
para comunicação UDP,ServerSocket é usado para comunicação TCP.
Quando é criado um objeto serversocket a porta que será usada para a comunicação precisa ser especificada.

 Tal como foi proposto pelo professor nas aulas, foi implementado um ponto de comunicação entre duas máquinas, através de uma conexão estabelecida (TCP).
Foi necessário um objeto socket para a conexão TCP e um objeto PrintWriter para escrever os dados e facilitar na leitura de dados a partir do servidor e também facilitar a leitura de dados a partir do servidor, invocando o método println() passando a mensagem como argumento.
Para receber dados usando TCP, foi necessário um objeto socket para estabelecer a conexão TCP e para ler os dados BufferedReader.
