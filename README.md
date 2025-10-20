# BlockyChat
BlockyChat é um plugin de customização de chat para o servidor BlockyCRAFT. Ele formata as mensagens dos jogadores, se integra com o [BlockyFactions](https://github.com/andradecore/BlockyFactions) para exibir tags e permite a personalização completa das cores de chat através de um arquivo de configuração.

## Funcionalidades
- **Integração com Facções**: Exibe a tag da facção do jogador ao lado de seu nome no chat.
- **Cores Totalmente Configuráveis**: Permite que todas as cores padrão (nome do jogador, tag da facção, colchetes e mensagem) sejam alteradas no arquivo `config.yml` sem precisar editar o código do plugin.
- **Greentext e Redtext**: Mantém a funcionalidade clássica onde mensagens iniciadas com `>` são exibidas em verde e com `<` em vermelho, sobrescrevendo a cor padrão da mensagem.
- **Formato Dinâmico**: O formato do chat se adapta automaticamente, mostrando a tag apenas para jogadores que fazem parte de uma facção.

## Exemplo de Formato
- **Com Facção**: `jogador1 [TAG]: mensagem de teste!`
- **Sem Facção**: `jogador2: mensagem de teste!`

## Configuração
O plugin cria um arquivo `config.yml` na pasta `/plugins/BlockyChat/` que permite customizar a aparência do chat.

**Exemplo do `config.yml`:**
```yml
# Use os codigos de cores padrao do Minecraft com '&'.
# Exemplo: '&f' para branco, '&b' para azul claro, '&7' para cinza.
chat-colors:
  # Cor para o nome do jogador e os colchetes.
  player-name: '&f'

  # Cor para o texto da tag da faccao.
  faction-tag: '&b'
  
  # Cor padrao para a mensagem do chat.
  message: '&7'