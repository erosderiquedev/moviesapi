# MVVM Movies App - (Part 1, Part 2 & Part 3)

Este repositório contém a primeira parte de um tutorial sobre a implementação de uma aplicação de filmes com arquitetura limpa utilizando o padrão MVVM (Model-View-ViewModel). O tutorial usa Retrofit para consumo de API, Room Database para persistência local, e Jetpack Compose com Kotlin para a interface do usuário.

## Descrição do Projeto

Neste projeto, você aprenderá a:

- **Consumir dados da API** utilizando Retrofit.
- **Armazenar dados localmente** com Room Database.
- Utilizar **Jetpack Compose** para criar interfaces reativas e modernas.
- Implementar a **arquitetura MVVM** para separar a lógica de negócios da UI e promover um código mais testável e escalável.

O projeto foi desenvolvido com base no tutorial de Ahmed Guedmioui, onde ele demonstra como implementar uma app de filmes no Android com essas tecnologias.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal.
- **Jetpack Compose**: Biblioteca de UI declarativa para criar interfaces nativas no Android.
- **Retrofit**: Biblioteca para facilitar o consumo de APIs REST.
- **Room Database**: Biblioteca para persistência local de dados no Android.
- **MVVM**: Padrão de arquitetura para separar a lógica de negócios da interface de usuário.

## API Utilizada

A API utilizada para obter dados sobre filmes é a [The Movie Database (TMDb)](https://www.themoviedb.org).

### Configuração da API Key

Antes de executar o projeto, será necessário incluir sua **API Key** no arquivo abaixo:

```text
app/src/main/java/com/erosutuidev/moviesapi/movieList/data/remote/MovieApi.kt
