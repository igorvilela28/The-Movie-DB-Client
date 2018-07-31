# The Movie DB Cliente

Este projeto tem como objetivo demonstrar algumas habilidades aprendidas para o desenvolvimento Android utilizando Kotlin.

No presente momento, o aplicativo é capaz de conectar-se a [API](https://developers.themoviedb.org/3) do `The Movie DB` e mostrar uma lista paginada dos filmes mais populares do momento. Ao clicar em algum filme, você será levado a uma tela que contém o detalhamento do mesmo, contendo mais informações e os trailers, se existentes. Finalmente, ao clicar em algum trailer, você será levado pelo app do YouTube ou pelo navegador para visualização do mesmo.

## Como utilizar este projeto

O projeto foi desenvolvido utilizando a versão do Android Studio 3.1.3, Gradle 3.1.3, Kotlin 1.2.51 e Coroutines 0.23.4. Recomenda-se instalar o [plugin Kotlin](https://kotlinlang.org/docs/tutorials/kotlin-android.html). Após instalar, o projeto deverá ser compilado normalmente. 

Observe que a partir da versão 3.0 do Android Studio, o suporte para Kotlin já estará incluso e você poderá ignorar a instalação do plugin.

## Organização do projeto

A arquitetura do projeto segue os conceitos da [Clean architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) e é utilizado o Model-View-View-Model (MVVM) para a camada de apresentação.

O projeto está separado em módulos do gradle:

- `data`: Contém as implementações de repositórios para acesso aos dados
- `domain`: Contém a lógica de negócio da aplicação.
- `app`: Contém a lógica de apresentação e injeção de dependência.

## Bibliotecas e frameworks utilizados

- [Android Support Libraries](https://developer.android.com/topic/libraries/support-library/index.html): para implementação da UI das telas
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/): Para organização da camada de apresentação, utilizando o `ViewModel` e `LiveData`
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): Para programação assíncrona
- [Dagger2](https://github.com/google/dagger): para injeção de dependência.
- [Gson](https://github.com/google/gson): para mapeamento de JSON -> objeto
- [Picasso](https://github.com/square/picasso): para loading de imagens.
- [Retrofit](http://square.github.io/retrofit/): para as chamadas REST.
- [Timber](https://github.com/JakeWharton/timber): para logs

Para testes unitários
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - Para mockar requisições para a API
- [MockK](https://github.com/mockk/mockk) - Para mock de objetos

## Decisões de implementação

A utilização do Kotlin foi decidida por ser uma alternativa para o desenvolvimento Android nativo, oficialmente adotada pela google e pela comunidade de desenvolvimento Android. 

Além de ser menos verbosa que o Java, Kotlin nos oferece diversas ferramentas interessantes, como [null safety](https://kotlinlang.org/docs/reference/null-safety.html), [high order functions e lambdas](https://kotlinlang.org/docs/reference/lambdas.html), [delegate properties](https://kotlinlang.org/docs/reference/delegated-properties.html), [extensions](https://kotlinlang.org/docs/reference/extensions.html) e ainda em versão experimental [coroutines](https://github.com/Kotlin/kotlinx.coroutines).

Programação assíncrona é uma realidade para o desenvolvimento Android. O fato de que utilizando coroutines podemos escrever trechos de código assíncronos sequencialmente me chamou bastante a atenção. Portanto, coroutines foram utilizadas como prova de conceito.

De acordo com a [documentação](https://kotlinlang.org/docs/reference/coroutines.html) uma coroutine é uma computação que é capaz de suspender/resumir sua execução, sem bloquear uma thread, o que encaixa perfeitamente com o desenvolvimento Android, visto que precisamos executar certas operações fora da UI Thread (como chamadas de rede, acesso ao banco de dados local, etc). Além de possuir APIs simples e direta para paralelismo, cancelar tarefas, etc.''


## Trabalho futuro

- Testes de integração e de UI utilizando Espresso
- Cache dos resultados da API

# Licença

    Copyright 2018 Igor Vilela

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
