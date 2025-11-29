# DevOps Project

Este é um projeto Java/Spring Boot com configurações de CI/CD e monitoramento com New Relic.

## Configuração do CI/CD

O projeto inclui um fluxo de trabalho do GitHub Actions que automatiza o build, teste e deploy da aplicação.

### Fluxo de Trabalho

1. **Build e Testes**
   - Executa o build do projeto com Maven
   - Executa os testes unitários
   - Gera relatórios de cobertura de código

2. **Build e Push da Imagem Docker**
   - Constrói uma imagem Docker da aplicação
   - Faz push da imagem para o Docker Hub
   - Utiliza cache para builds mais rápidos

### Configuração Necessária

1. **Variáveis de Ambiente no GitHub**
   - `DOCKERHUB_USERNAME`: Seu nome de usuário do Docker Hub
   - `DOCKERHUB_TOKEN`: Token de acesso pessoal do Docker Hub

2. **Como configurar**
   - Acesse as configurações do seu repositório no GitHub
   - Vá em "Settings" > "Secrets and variables" > "Actions"
   - Adicione as variáveis de ambiente necessárias

## Configuração do New Relic

Este guia explica como configurar o monitoramento com New Relic no projeto.

## Pré-requisitos

- Conta no [New Relic](https://newrelic.com/)
- Java 8 ou superior
- Maven

## Configuração

1. **Arquivo de Configuração**
   - O arquivo de configuração do New Relic está em: `src/main/resources/newrelic/newrelic.yml`
   - Você NÃO precisa do arquivo ZIP do New Relic na raiz do projeto

2. **Configuração da Licença**
   - Abra o arquivo `src/main/resources/newrelic/newrelic.yml`
   - Localize a linha com `license_key:`
   - Substitua o valor existente pela sua chave de licença do New Relic:
     ```yaml
     common: &default_settings
       license_key: 'SUA_CHAVE_DE_LICENCA_AQUI'
     ```

3. **Nome do Aplicativo**
   - No mesmo arquivo, localize a linha com `app_name:`
   - Defina o nome que deseja exibir no painel do New Relic:
     ```yaml
     app_name: 'Nome do Seu Aplicativo'
     ```
   - Você pode especificar até 3 nomes de aplicativos separados por ponto e vírgula (;)

4. **Ambiente**
   - O ambiente pode ser configurado em `application.properties`:
     ```properties
     newrelic.environment=development  # Pode ser development, staging, production, etc.
     ```

## Executando o Projeto

### No IntelliJ
1. Clique em "Run" > "Edit Configurations..."
2. Adicione nas VM Options:
   ```
   -javaagent:target/newrelic/newrelic.jar
   ```
3. Certifique-se que o diretório de trabalho (Working Directory) aponta para a raiz do projeto

### Linha de Comando
```bash
mvn clean package
java -javaagent:target/newrelic/newrelic.jar -jar target/seu-aplicativo.jar
```

## Verificando a Conexão

Acesse o endpoint de health para verificar se o New Relic está configurado corretamente:
```
GET /api/health
```

## Solução de Problemas

- **Erro de licença inválida**: Verifique se a chave de licença está correta
- **Agente não inicializado**: Verifique se o caminho para o newrelic.jar está correto
- **Dados não aparecendo no dashboard**: Aguarde alguns minutos e atualize a página do New Relic

## Suporte

Em caso de dúvidas, consulte a [documentação oficial do New Relic](https://docs.newrelic.com/) ou entre em contato com a equipe de desenvolvimento.
