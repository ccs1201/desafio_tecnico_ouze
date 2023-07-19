Agradecemos pelo seu interesse na vaga de desenvolvedor em nossa empresa. Como parte do processo de seleção, gostaríamos de solicitar sua participação em um teste de desenvolvimento relacionado a um software financeiro. O objetivo deste teste é avaliar suas habilidades técnicas, capacidade de lidar com conceitos financeiros e sua capacidade de desenvolver soluções para problemas do mundo real.

O teste consiste na implementação de uma funcionalidade específica: um módulo de cálculo de juros compostos para um empréstimo pessoal. O usuário poderá inserir os dados do empréstimo, consultar as parcelas e confirmar o pagamento de forma assíncrona, utilizando um barramento de mensagens via fila para simular o pagamento externo e notificar o sistema.

Aqui estão os requisitos para a implementação do módulo:

1. Criação de contrato:
    - O usuário deve informar o ID do cliente, simulando uma referência a uma tabela no banco de dados.
    - O usuário deve informar se o cliente é Ouze ou não. Os clientes Ouze têm uma taxa de juros de 0.7% ao mês, enquanto os demais clientes têm uma taxa de 1% ao mês.
    - O usuário deve informar o valor total do empréstimo e o período de pagamento, com um mínimo de 12 parcelas e um máximo de 48 meses.
    - Um cliente não pode ter mais de um contrato ativo. Se houver mais de um contrato ativo para o mesmo cliente, um erro deve ser retornado.
    - As parcelas devem ter uma data de vencimento.

2. Listagem das parcelas:
    - A listagem deve mostrar apenas as parcelas do último empréstimo do usuário. Se o usuário não tiver nenhum empréstimo, um erro deve ser retornado.

3. Confirmação de pagamento:
    - A confirmação do pagamento deve ser feita por meio de uma fila do RabbitMQ.
    - Em caso de falha, a mensagem deve ser reprocessada três vezes. Se o erro persistir, a mensagem deve ser enviada para uma fila de estacionamento para análise do time de sustentação.
    - Quando todas as parcelas forem pagas, o contrato deve ser desativado. O cliente poderá criar um novo contrato após isso, voltando ao primeiro requisito.
    - Em caso de falha na criação do contrato ou na confirmação do pagamento, uma transação deve ser criada e o rollback deve ser feito para evitar incoerências nos dados.

A seguir, descrevemos as tarefas que devem ser realizadas para cada nível de experiência:

Senior:
- Disponibilizar um endpoint para a criação do contrato, seguindo os requisitos mencionados anteriormente.
- Construir um consumidor para processar o pagamento das parcelas do empréstimo, seguindo os requisitos mencionados anteriormente.

Pleno:
- Realizar o cálculo correto das parcelas com base no tipo do cliente.
- Construir a lógica de confirmação de pagamento, seguindo os requisitos mencionados anteriormente.

Junior:
- Corrigir a falha no cálculo das parcelas, onde os clientes Ouze estão com a mesma taxa dos clientes não Ouze.
- Corrigir a validação da quantidade mínima e máxima de parcelas na criação do contrato.
- Corrigir a falha na fila de estacionamento, onde as falhas na confirmação do pagamento não estão sendo redirecionadas conforme os requisitos mencionados anteriormente.

Você deve implementar a solução utilizando a linguagem Java e desenvolvê-la de forma modular e extensível, considerando possíveis atualizações e requisitos adicionais no futuro.

Você terá acesso a um ambiente de desenvolvimento com a linguagem Java e as dependências necessárias para a execução da aplicação. Para facilitar a inicialização do RabbitMQ, fornecemos um arquivo docker-compose no repositório. As configurações estão corretas, mas você pode modificá-las, se necessário.

Procedimento:

1. Faça o fork do projeto para a implementação do módulo de cálculo de juros compostos.
2. Implemente a solução de acordo com os requisitos mencionados acima.
3. Documente as instruções necessárias para executar e testar o módulo no arquivo README.
4. Faça um commit do seu código no repositório fornecido.
5. Envie-nos o link do seu repositório com a solução.
6. Informe-nos se você utilizou ferramentas de inteligência artificial para resolver alguma parte da solução.

Boa sorte! Estamos ansiosos para ver sua implementação.