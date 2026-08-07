# 🚘 Consulta Tabela FIPE

Uma aplicação Desktop desenvolvida em Java para consulta atualizada de valores de veículos (carros, motos e caminhões) utilizando a API pública da Tabela FIPE. 

O projeto demonstra o consumo de APIs RESTful, desserialização de dados JSON e a construção de uma Interface Gráfica de Usuário (GUI) nativa separada da lógica de negócios.

---

## ✨ Funcionalidades

* **Consulta de Marcas:** Listagem completa de montadoras divididas por categoria (Carros, Motos ou Caminhões).
* **Busca de Modelos:** Recuperação de todos os modelos associados a uma marca específica.
* **Histórico de Valores:** Consulta automatizada (via Threads em background) de todos os anos de fabricação de um modelo, retornando o valor atualizado de cada versão segundo a FIPE.
* **Interface Gráfica (GUI):** Interface intuitiva construída com Java Swing, garantindo uma experiência de usuário fluida e sem travamentos.

---

## 🛠️ Tecnologias Utilizadas

* **Java:** Linguagem principal do projeto.
* **Spring Boot:** Utilizado para injeção de dependências e inicialização da aplicação (configurado em modo `headless(false)` para suporte a GUI).
* **Java Swing:** Biblioteca nativa para a construção da interface gráfica (janelas, botões, campos de texto).
* **Jackson (Mapeamento de Dados):** Biblioteca para conversão (desserialização) dos retornos JSON da API para objetos Java (`Records`).
* **HttpClient nativo:** Realização das requisições HTTP assíncronas e síncronas.
* **API Externa:** [API Tabela FIPE (Parallelum)](https://deividfortuna.github.io/fipe/)

---

## 🏗️ Arquitetura e Boas Práticas

O projeto foi refatorado para seguir o **Princípio da Responsabilidade Única (SRP)** da Orientação a Objetos. A arquitetura está dividida em:

* `model`: Classes e Records que representam o domínio dos dados (Veículo, Marca, Modelo).
* `service`: Classes isoladas responsáveis por ações externas (Consumo da API HTTP e Conversão de JSON).
* `visao`: Contém a classe `InterfaceFipe`, isolando completamente a lógica de renderização de tela (Swing) e captura de eventos de clique.
* `principal`: Onde o Spring Boot inicializa a aplicação e invoca a interface visual.

---

## 🚀 Como Executar

1. Certifique-se de ter o **Java JDK** instalado na sua máquina (versão 17 ou superior recomendada).
2. Clone este repositório:
   ```bash
   git clone [[https://github.com/Mateuscastelani/TabelFipe.git](https://github.com/Mateuscastelani/TabelFipe.git)]
   Importe o projeto na sua IDE favorita (IntelliJ IDEA, Eclipse, VS Code) como um projeto Maven.

Atualize as dependências do Maven (Reload Project).

Execute a classe TabelFipeApplication (ou a classe que contém o método main do Spring Boot).

A interface gráfica será aberta automaticamente.

👨‍💻 Autor
Mateus Castellani
