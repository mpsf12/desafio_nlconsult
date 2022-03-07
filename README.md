# Desafio NTConsult

A questão sobre o versionamento de API, após pesquisar um pouco, acho que utilizaria
um versionamento baseado na header da requisição. Criaria um atributo version
para controalr a versão que o cliente espera da api, e caso nao seja passado
nenhuma versão de controle seria utilizada a versão mais recente da API.