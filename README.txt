################################################################################################
##################			SuperXMart - Avaliação Técnica			############################
################################################################################################

################################################################################################
##################					Tecnologia						############################
################################################################################################

Para a solução do novo sistema de logística do SuperXMart foram usadas as seguintes tecnologias:
- Apache Maven 2.2.1
- WebService SOAP
- Hibernate
- Tomcat 7
- HSQLDB
- TestNG

################################################################################################
##################				Como instalar a versão				############################
################################################################################################

A versão esta disponível na raiz do projeto SuperXMart com o nome "services.war". 
Foi desenvolvida e testada no Apache Tomcat 7.0.55 (http://tomcat.apache.org/download-70.cgi). 
Então basta copiar o arquivo "services.war" e jogar na pasta webapps do Tomcat e então executar o startup.bat (Windows).
O banco de dados HSQLDB irá criar sua estrutura de diretórios a partir do diretório do usuário logado. <home.dir>/superxmart/jls/data.


################################################################################################
##################		Importando o projeto no Eclipse				############################
################################################################################################

Os arquivos .project e .classpath para importar no eclipse são gerados também pelo maven.
Após o maven configurado (variáveis de ambiente), basta executar na raiz do projeto o comando "mvn eclipse:eclipse"


################################################################################################
##################				Como executar o programa			############################
################################################################################################

A url para acessar o WSDL da aplicação é a http://<host>:<porta>/services/Rota?wsdl
Há duas operações disponíveis, que são:

- cadastrarRota
O xml de requisição é composto por mapa, que contém o nome, e as rotas, que contém origem, destino e distância. Exemplo:

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.superxmart.com.br/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:cadastrarRota>
         <mapa>
            <nome>SP</nome>
            <rotas>
               <origem>A</origem>
               <destino>B</destino>
               <distancia>10</distancia>
            </rotas>
            <rotas>
               <origem>B</origem>
               <destino>D</destino>
               <distancia>15</distancia>
            </rotas>
         </mapa>
      </ws:cadastrarRota>
   </soapenv:Body>
</soapenv:Envelope>

A resposta contém o mesmo xml, porém com o id gerado, tanto do mapa quanto de suas rotas. Exemplo:
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:cadastrarRotaResponse xmlns:ns2="http://ws.superxmart.com.br/">
         <return>
            <id>3</id>
            <nome>SP</nome>
            <rotas>
               <id>7</id>
               <destino>B</destino>
               <distancia>10</distancia>
               <origem>A</origem>
            </rotas>
			<rotas>
               <id>8</id>
               <destino>D</destino>
               <distancia>15</distancia>
               <origem>B</origem>
            </rotas>
         </return>
      </ns2:cadastrarRotaResponse>
   </S:Body>
</S:Envelope>


- pesquisarRota
o xml de requisição contém os campos nomeMapa, origem, destino, autonomiaVeiculo e valorLitroCombustivel. Exemplo:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.superxmart.com.br/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:pesquisarRota>
         <pesquisaDeRota>
            <autonomiaVeiculo>10</autonomiaVeiculo>
            <destino>D</destino>
            <nomeMapa>SP</nomeMapa>
            <origem>A</origem>
            <valorLitroCombustivel>2.5</valorLitroCombustivel>
         </pesquisaDeRota>
      </ws:pesquisarRota>
   </soapenv:Body>
</soapenv:Envelope>

O xml de retorno conterá a melhorRota, o custoEstimadoDaRota, e as rotas até o destino. Exemplo:
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:pesquisarRotaResponse xmlns:ns2="http://ws.superxmart.com.br/">
         <return>
            <custoEstimadoDaRota>6.25</custoEstimadoDaRota>
            <melhorRota>Rota A B D com custo de 6,25.</melhorRota>
            <rotas>
               <id>1</id>
               <destino>B</destino>
               <distancia>10</distancia>
               <origem>A</origem>
            </rotas>
            <rotas>
               <id>2</id>
               <destino>D</destino>
               <distancia>15</distancia>
               <origem>B</origem>
            </rotas>
         </return>
      </ns2:pesquisarRotaResponse>
   </S:Body>
</S:Envelope>

################################################################################################
##################					Considerações					############################
################################################################################################

- Quando for encontrada mais de uma rota com o menor custo, será então escolhida à rota com a menor distância. 


################################################################################################
##################					Dados do avaliado				############################
################################################################################################

Qualquer dúvida ou informação estou a disposição.

Jonathas Lopes de Souza
jonitu@gmail.com 

