# selenium_simple_example
## Pre-Requisitos
- Java 8+ - https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
- Maven - https://maven.apache.org/download.cgi
- Chrome 88 - https://www.google.com/intl/pt-BR/chrome/
  - Caso o Chrome instalado na sua máquina seja superior, é necessário realizar o download do chromedriver ou chromedriver.exe referente à sua versão no link https://chromedriver.chromium.org/downloads e substituir o que está no diretório "drivers" do projeto. 

## Scripts

Faça o clone do repositório em um diretório local.

## Desafio

Automatizar os seguintes fluxos de um ecommerce:
1. Encontrar um produto válido e incluí-lo ao carrinho de compras
2. Pesquisar por um produto indisponível
3. Pesquisar por um produto inválido

## Objetivo

O objetivo desta POC é utilizar a melhor combinação de tecnologias para criar uma solução de testes automatizados que atenda:
- Selenium + Java;
- Casos de Teste em BDD;
- Relatório Automático;
- Execução em Pipeline de IC;
- Execução em OS e navegadores distintos;
- Possível execução em container;

## Solução

### Linguagem de Programação e Ferramentas

- Selenium WebDriver
  - Ferramenta de automação de testes web.

- Java 8+
  - Linguagem de programação. *Detalhe que é necessário que seja a versão 8, pois algumas implementações realizadas utilizam mecanismos presentes apenas nesta versão ou superiores*

- Cucumber
  - Ferramenta utilizada integração com BDD
  
- TestNG
  - Framework de testes utilizado para realizar as asserções dos testes

- ExtentReports
  - Ferramenta responsável pela geração de relatórios automáticos

- Logback
  - Framework de gerenciamento de logs
  
### Metodologias, Padrões de Projeto e Boas Práticas

- **PageObjects**
  - Padrão de projeto voltado para automação de testes que atribuir responsabilidades específicas para cada página/componente do sistema. Neste padrão, cada objeto Page é responsável por funcionalidades referentes apenas ao seu propósito. Nesta POC estão criadas as Pages "HomePage", "ProductListPage", "ProductPage" e "ShoppingCartPage". Todas estas Pages, utilizando o conceito de herança, extendem a classe "BasePage" que agrupa vários métodos e comportamentos semelhantes das demais Pages.

- **PageFactory**
  - Mecanismo relacionado ao padrão de projetos PageObject que realiza a criação da instância de um Page de maneira organizada. Nesta POC, a annotation "FindBy" e o método "initElements" da classe "PageFactory" foram utilizados.

- **DriverFactory**
  - Mecanismo criado para encapsular a criação de drivers independente do seu tipo e do sistema operacional. 

- **Singleton (DriverManager)**
  - A classe DriverManager implementa o padrão de projeto Singleton e serve para garantir que apenas uma unica instância do driver é utilizada durante toda a execução dos testes.

- **Data Lake Consumer**
  - O uso de Data Lakes ou ferramentas de TDM servem para garantir que a massa de dados utilizada esteja correta de acordo com a versão corrente do sistema. Nesta POC, simulei o uso de dados de um Data Lake ao criar um client que consome informações de um mock (arquivo data_lake_mock.json) e instancia uma lista de "Products" que são utilizadas durante o teste. Este tipo de mecanismo pode ser substituido por integração à TDMs, serviços de consulta e até mesmo, quando necessário, consulta a banco de dados.

- **BDD**
  - *O BDD não é automação de testes! FATO!* Mas a utilização de frameworks que interpretam a linguagem Gherkin e associa às chamadas das Pages permite com que Casos de Testes escritos neste modelo sejam utilizados como input para execução de testes automatizados. Para esta POC, os cenários de testes foram escritos seguindo a boa prática de não ser um script de teste, ou seja, detalhado demais. Além disto, palavras chaves como "Backgroud" e "Tags" foram utilizadas para realizar o reaproveitamento e melhor uso possível das informações que foram disponibilizadas.

- **Validators**
  - Utilizando o TestNG, foi criada uma classe especializada em realizar as asserções dos testes de forma segregada, o que permite que estas validações possam ser trabalhadas de maneira apartada do restante do código. As chamadas de validações foram incuídas em pontos onde os testes deveriam ser validados para garantir que o resultado obtido é igual ao resultado esperado.

- **Report Automático**
  - Utilizando o framework "ExtentReports", após cada execução um relatório html é gerado no diretório "test-output" contendo informações sobre o resultado obtido bem como screenshots do último passo de cada cenário. Este relatório possui dashboards para apresentações executivas e também informações detalhadas (stacktrace) dos erros que possam ter ocorrido.

- **Gerenciamento de Drivers**
  - Com o uso do DriverFactory citado acima, esta POC tem alguns mecanismos relacionados à utilização do driver:
        
	- Um mecanismo automático de detecção de sistema operacional, onde define o driver de acordo com SO utilizado durante a execução;
	- Capacidade de definir entre Chrome e Firefox informando apenas o paramêtro de execução "-Dbrowser=firefox" ou "-Dbrowser=chrome" para os respectivos navegadores. *Caso não informado, o navegador padrão é o Chrome*;
	- Modo de execução headless informando o paramêtro "-Dheadless=true" durante a execução. *Este modo de execução é válido apenas para o Chrome*;

- **Logs**
  - A solução utiliza o framework Logback para armazenar os principais logs de eventos ocorridos durante a execução.  


## Execução

Dentro do diretório do projeto, execute:

  1. Execução: Chrome

```
mvn clean test
```
	
  2. Execução: Firefox

```
mvn clean test -Dbrowser=firefox
```
	
  3. Execução: Headless
    
```
mvn clean test -Dheadless=true
```
	
  4. Execução: Paralela
    
```
mvn clean test -Dthreads=N
```
> **N** corresponde ao número de threads desejadas

  5. Execução: Remota
    
```
mvn clean test -Dremote=true
```

Após qualquer uma das execuções, o relatório "Report.html" é gerado dentro do diretório "test-output/Spark" contendo os resultados obtidos, os gráficos relacionados e screenshots.
 
## Integrações

### CI/CD

Utilizando a execução através da build do Maven, "mvn clean test" ou "mvn clean test -Dheadless=true" é possível criar um job em um pipeline onde os testes implementados nesta solução serão executados. Como foram implementados Asserts em pontos cruciais, caso algum falhe, a build do job irá quebrar.

### Docker

- **Configuração do container:**

1 - Instalação imagem **selenium hub**:
```
docker pull selenium/hub
```
2 - Instalação imagem **node Chrome e Firefox**
```
docker pull selenium/node-chrome && docker pull selenium/node-firefox
```
- **Inicialização do container com docker-compose:**

```
docker-compose -f docker-compose.yml up
```
> O arquivo docker-compose.yml está presente na raiz do projeto

Uma vez que o container docker seja inicializado com sucesso basta seguir a execução remota do projeto:
```
mvn clean test -Dremote=true
```
