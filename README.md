# Teste Técnico - Sicredi Canais - Tribo Plataforma
Seja bem-vindo ao teste técnico da tribo de Plataforma de Canais. Abaixo você encontrará as instruções necessárias para prosseguir.
O entendimento do contexto, do escopo e do objetivo da implementação fazem parte de sua avaliação. 
Todas as informações para dar continuidade com o teste se encontram neste documento.

# Contexto
Você é um engenheiro de software que atua diariamente alocado em uma squad com outros 3 engenheiros, seguindo as priorizações 
realizadas pelo time de negócio (representadas dentro da squad pelo papel de um PM). Um de seus colegas engenheiro, puxou um ticket priorizado
e conseguiu finalizar a implementação em uma sexta-feira antes de iniciar seu período de férias. O colega em questão finalizou o código, 
realizou o deploy em ambiente de homologação, fez algumas validações rápidas e deixou a seguinte mensagem no canal de comunicação da equipe:

"Time, finalizei a implementação do ticket. Foi na correria, mas deu tudo certo. Subi a API em homologação para que o time de qualidade
possa iniciar os testes na próxima semana. Conforme falamos, estou saindo de férias e retorno somente daqui a 30 dias.
Conto com vocês para darem seguimento na demanda e disponibilizarem a feature em produção. Nos vemos no próximo mês, até logo!"

Na segunda-feira da semana seguinte, o time de qualidade inicia os testes e todos os comportamentos testados nos primeiros dias se mostram dentro do esperado.
Na quarta-feira, quando a demanda já está quase na etapa de finalização dos testes e a expectativa do time de negócio está alta para 
liberar a funcionalidade em ambiente produtivo, surge um ticket de bug no board da equipe, vinculado a demanda em questão.

A sua tarefa é descobrir e resolver este bug. Você deve fazer isso respeitando as orientações do ticket da demanda e os acordos do time, mesmo diante da pressão pela entrega.

### Acordos do Time
1. Prezamos por um ambiente colaborativo. Nos ajudamos e nos fortalecemos em momentos de dificuldade, sem realizar apontamento de dedos para nossos colegas.
2. Prezamos pela qualidade das entregas na engenharia de software. Mesmo em momentos de pressão, a qualidade da entrega em sua totalidade deve ser priorizada.
3. Entregamos o código sempre melhor do que o recebemos. Não nos limitamos ao escopo da alteração.

### Ticket da Demanda
Disponibilize uma API que consiga realizar uma consulta de usuários de nossa base Legada e também de seus arquivos.
1. É necessário ser disponibilizado um endpoint para listar todos os usuários da base de dados.
2. É necessário ser disponibilizado um endpoint para detalhar todas as informações de um usuário específico.
3. É necessário ser disponibilizado um endpoint para retornar os dados de um usuário específico, porém de forma simplificada (Apenas nome e email).
4. É necessário disponibilizar um endpoint que retorne todos os arquivos do sistema, porém sem seu respectivo conteúdo. Neste mesmo endpoint, caso seja informado um usuário específico, deve-se retornar todos os arquivos do usuário, com o conteúdo dos arquivos incluso.

Premissa: Esta é uma base legada consumida por diversos sistemas da empresa. Não devemos realizar alterações sobre a massa de dados disponível.

### Ticket do Bug
Constatamos uma intermitência no serviço disponibilizado. Às vezes, a operação para retornar as informações completas de um usuário específico falha, retornando erro 500.
O comportamento parece se normalizar sem nenhuma intervenção algum tempo depois, mas logo volta a ocorrer novamente.
Segue evidência na imagem abaixo.

(A imagem de evidência encontra-se dentro da pasta "resources" deste projeto).

### Como entregar
1. Faça um `clone` deste repositório em sua máquina.
2. Realize a implementação.
3. Crie um novo repositório público em sua conta pessoal no Github.
4. Suba o código.
5. Envie um email para "eduardo_fsilva@sicredi.com.br;gabriel_antunes@sicredi.com.br;juliane_melo@sicredi.com.br" com o link do repositório.

