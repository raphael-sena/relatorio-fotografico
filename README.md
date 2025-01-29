# ⚙️ relatorio-fotografico

![home](https://github.com/user-attachments/assets/bf301df0-d284-4b49-bacf-1be990262156)

## 📚 Sobre
O Gerenciador de Relatório Fotográfico é uma aplicação executável em Sistema Operacional Windows com o Java JRE, desenvolvido para a empresa Eletronet Materiais Elétricos LTDA e New Energy Soluções Elétricas LTDA. A aplicação tem a finalidade de gerar relatórios em .pdf com as informações de itens e cliente para inspeção do Relatório de Evento de Inspeção (REI) exigido pela CEMIG DISTRIBUIÇÃO SA., um dos clientes das empresas.

O aplicativo foi desenvolvido com Java e Spring Boot para o backend e, para a interface gráfica, foi utilizado Java Swing. O banco é local na máquina do usuário com a abordagem do H2.

## 🗂️ Project Structure
```
📦 relatorio-fotografico
├─ README.md
├─ code
│  └─ backend
│     └─ inspecao-rei
│        ├─ .gitattributes
│        ├─ .gitignore
│        ├─ .mvn
│        │  └─ wrapper
│        │     └─ maven-wrapper.properties
│        ├─ data
│        │  ├─ inspecao_db.lock.db
│        │  ├─ inspecao_db.mv.db
│        │  └─ inspecao_db.trace.db
│        ├─ mvnw
│        ├─ mvnw.cmd
│        ├─ pom.xml
│        └─ src
│           ├─ main
│           │  ├─ java
│           │  │  └─ com
│           │  │     └─ newenergy
│           │  │        └─ inspecao_rei
│           │  │           ├─ InspecaoReiApplication.java
│           │  │           ├─ configs
│           │  │           │  ├─ DatabaseConfigManager.java
│           │  │           │  └─ WebConfig.java
│           │  │           ├─ controllers
│           │  │           │  ├─ InspecaoController.java
│           │  │           │  └─ ItemController.java
│           │  │           ├─ models
│           │  │           │  ├─ Clientes.java
│           │  │           │  ├─ Inspecao.java
│           │  │           │  ├─ Item.java
│           │  │           │  └─ dtos
│           │  │           │     ├─ InspecaoCreateDTO.java
│           │  │           │     ├─ InspecaoDTO.java
│           │  │           │     ├─ InspecaoUpdateDTO.java
│           │  │           │     ├─ ItemDTO.java
│           │  │           │     ├─ ItemInspecaoDTO.java
│           │  │           │     └─ ItemMinDTO.java
│           │  │           ├─ repositories
│           │  │           │  ├─ InspecaoRepository.java
│           │  │           │  └─ ItemRepository.java
│           │  │           ├─ services
│           │  │           │  ├─ ImageService.java
│           │  │           │  ├─ InspecaoService.java
│           │  │           │  └─ ItemService.java
│           │  │           └─ views
│           │  │              ├─ Footer.java
│           │  │              ├─ MainFrame.java
│           │  │              ├─ Menu.java
│           │  │              ├─ NovaInspecao.java
│           │  │              ├─ PdfExporter.java
│           │  │              └─ VisualizarInspecoes.java
│           │  └─ resources
│           │     ├─ application-dev.properties
│           │     ├─ application-prod.properties
│           │     ├─ application-test.properties
│           │     └─ application.properties
│           └─ test
│              └─ java
│                 └─ com
│                    └─ newenergy
│                       └─ inspecao_rei
│                          └─ InspecaoReiApplicationTests.java
├─ innoscript.iss
└─ uml
   └─ ClassDiagram.asta
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

## 🚀 Configuração do Ambiente

### Pré-requisitos
Garanta que você tenha instalada as ferramentas:
- [Java JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi?.)
- [Inno Setup](https://jrsoftware.org/isdl.php)

### 📦 Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/raphael-sena/relatorio-fotografico
2. Acesse o diretório do código do projeto:
   ```bash
   cd code/backend/inspecao-rei
3. Install the dependecies: 
    ```bash
    mvn clean package -DskipTests

### 🚧 Rodando a aplicação
Para rodar a aplicação, crie, dentro da pasta target em:
```bash
  cd code\backend\inspecao-rei\target
```

Uma pasta chamada jre, depois baixe a versão do jre compatível com a da aplicação (21) pelo adoptium em:
- [Java JRE 21](https://adoptium.net/download/)
Após, descompacte o download para a pasta criada (target/jre).

Além disso, crie um arquivo, também na pasta target, chamado start.bat com o conteúdo:
  ```bash
  @echo on
  "%~dp0\jre\bin\java.exe" -jar inspecao-rei-0.0.1-SNAPSHOT.jar
  pause
  ```

Depois de seguir estes passos, com o Inno Setup, substituindo em [File] os caminhos dos arquivos pelos do seu projeto, rode o script .iss em:
```bash
  \innoscript.iss
```

Será gerado um arquivo executável e, após, instale-o em sua máquina. Caminho:
```bash
  inspecao-rei\Output\relatorio.exe
```

Rode o arquivo .bat em Arquivos de Pragramas e utilize a aplicação! É comum que o arquivo esteja disponível em:
```bash
  C:\Program Files\RelatorioFotografico\start.bat
```

### 🛠️ Tecnologias Utilizadas
* **Desenvolvimento**: Java, Swing, H2
* **Framework**: Spring Boot
* **Ferramentas**: Inno Setup, Launch4j, Postman
* **IDE**: IntelliJ
* **Controle de Versão**: Git
