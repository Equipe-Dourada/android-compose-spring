# EQUIPE DOURADA
# Integrantes:
    JERFESON NASCIMENTO CAMPOS SOUZA
    JOSE VAZ FILHO
    GUILHERME OLIVEIRA SANTOS
    JULIO CESAR BATISTA PIRES
# android-compose-spring
Projeto Final de Programação Mobile com Android Avançado
Este é um aplicativo Android desenvolvido usando Jetpack Compose para gerenciar informações de pacientes, incluindo visualização, atualização e exclusão de dados. O aplicativo se comunica com uma API backend para realizar operações CRUD (Criar, Ler, Atualizar e Excluir) relacionadas aos pacientes.

## Funcionalidades

- **Visualização de Dados**: Exibe informações detalhadas sobre um paciente, como nome, CPF, telefone, email, endereço e histórico médico.
- **Atualização de Dados**: Permite editar os dados de um paciente e atualizar as informações no backend.
- **Exclusão de Dados**: Exclui um paciente do banco de dados.
- **Carregamento e Exibição de Dados**: Utiliza Retrofit para realizar requisições HTTP e obter ou modificar os dados do paciente.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal para o desenvolvimento Android.
- **Jetpack Compose**: Framework para UI declarativa para Android.
- **Retrofit**: Biblioteca para facilitar as requisições HTTP para interagir com APIs REST.
- **Coroutines**: Para realizar tarefas assíncronas no Android.
- **Material Design**: Para componentes de UI modernos e consistentes.

## Requisitos

- **Android Studio** com suporte para Kotlin e Jetpack Compose.
- **MinSDK**: 21 ou superior.
- **Conexão com a internet**: Necessária para comunicação com a API.

## Como Rodar o Projeto

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/Equipe-Dourada/android-compose-spring

2. Abra o projeto no Android Studio:
        No Android Studio, selecione Open e escolha a pasta do projeto clonado.

3. Sincronize o Gradle:
        No Android Studio, clique em Sync Now para garantir que todas as dependências sejam baixadas corretamente.

4. Execute o aplicativo:
        Conecte um dispositivo Android ou inicie um emulador.
        Clique em Run para compilar e executar o aplicativo.

Estrutura do Projeto

    ui/screens: Contém os Composables responsáveis pelas telas do aplicativo, como visualização e atualização dos dados do paciente.
    api: Contém as classes para definir os modelos de dados e as chamadas Retrofit para interagir com a API backend.
    RetrofitClient: Singleton para configurar e gerenciar as chamadas à API.

Exemplos de Tela

Tela Login

![Imagem do WhatsApp de 2025-03-06 à(s) 22 43 21_95516ef1](https://github.com/user-attachments/assets/033fb7d6-a616-48dd-9690-462acb88b597)

Tela de Cadastro de Paciente

![Imagem do WhatsApp de 2025-03-06 à(s) 22 43 20_f0907c4a](https://github.com/user-attachments/assets/d51e176a-3d54-403f-863c-e2bd870939f4)


Detalhes do Paciente

A tela de detalhes exibe todas as informações sobre o paciente, como nome, CPF, telefone, email, endereço e histórico médico. Além disso, há opções para excluir ou atualizar os dados do paciente.
Atualizar Paciente
![Imagem do WhatsApp de 2025-03-06 à(s) 22 43 21_968e0ba7](https://github.com/user-attachments/assets/ed77f814-f0d0-4cf9-9e80-9b49bd5e1f7c)


A tela de atualização permite ao usuário editar as informações do paciente e salvar as alterações no backend.
![Imagem do WhatsApp de 2025-03-06 à(s) 22 43 20_ef55d265](https://github.com/user-attachments/assets/59744760-1981-4fb8-b53b-5d0af0fa0288)


# Para testar a API
json {
    "cpf": "11223344550",
    "nome": "Pedro Oliveira",
    "telefone": "123456789",
    "email": "pedro.oliveira@example.com",
    "endereco": {
        "numero": "123",
        "cep": "12345678",
        "rua": "Rua A",
        "complemento": "Apto 1",
        "bairro": "Centro",
        "cidade": "São Paulo",
        "estado": "SP"
    },
    "ficha": {
        "id": 3,
        "medicamentos": "Medicamento A",
        "historico": "Histórico médico",
        "alergias": "Nenhuma"
    }
}
# Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo LICENSE para mais detalhes.
