# Desafio NTConsult

A questão sobre o versionamento de API, após pesquisar um pouco, acho que utilizaria
um versionamento baseado na header da requisição. Criaria um atributo version
para controalr a versão que o cliente espera da api, e caso nao seja passado
nenhuma versão de controle seria utilizada a versão mais recente da API.


Foi utilizado o lombok na aplicação, eu tentei fazer funcionar no clipse mas desisti
porque estava dando muito trabalho atoa, então passei a usar só o intellij.
Para rodar mais facil aocnselho a utilizar o intellij também, pois ele já vem
com a integração com o lombok.