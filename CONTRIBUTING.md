# Guia de Contribuição - MediPlan

## 🤝 Como Contribuir

### Configuração do Ambiente

1. **Fork do repositório**
2. **Clone seu fork:**
   ```bash
   git clone https://github.com/seu-usuario/mediplan.git
   cd mediplan
   ```
3. **Configure o upstream:**
   ```bash
   git remote add upstream https://github.com/original-usuario/mediplan.git
   ```

### Fluxo de Trabalho

1. **Crie uma branch para sua feature:**
   ```bash
   git checkout -b feature/nome-da-feature
   ```

2. **Faça suas mudanças**

3. **Teste localmente:**
   ```bash
   ./gradlew clean
   ./gradlew assembleDebug
   ./gradlew test
   ```

4. **Commit suas mudanças:**
   ```bash
   git add .
   git commit -m "feat: adiciona nova funcionalidade X"
   ```

5. **Push para seu fork:**
   ```bash
   git push origin feature/nome-da-feature
   ```

6. **Crie um Pull Request**

### Padrões de Commit

Use o padrão [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` nova funcionalidade
- `fix:` correção de bug
- `docs:` mudanças na documentação
- `style:` formatação, ponto e vírgula, etc
- `refactor:` refatoração de código
- `test:` adição ou correção de testes
- `chore:` mudanças em ferramentas, configurações

### Padrões de Código

1. **Kotlin:**
   - Use o estilo oficial do Kotlin
   - Nomes de classes em PascalCase
   - Nomes de funções e variáveis em camelCase
   - Use data classes quando apropriado

2. **XML:**
   - Use snake_case para IDs
   - Organize atributos alfabeticamente
   - Use recursos de string para textos

3. **Estrutura de pastas:**
   ```
   app/src/main/java/com/mediplan/
   ├── ui/                 # Activities, Fragments, ViewModels
   ├── data/              # Repositories, Data Sources
   ├── domain/            # Use Cases, Models
   ├── di/                # Dependency Injection
   └── utils/             # Utilitários
   ```

### Testes

- Escreva testes unitários para lógica de negócio
- Use MockK para mocking
- Mantenha cobertura de testes acima de 80%

### Documentação

- Documente funções públicas
- Atualize README.md se necessário
- Adicione comentários para lógica complexa

### Revisão de Código

Antes de submeter PR:
- [ ] Código compila sem warnings
- [ ] Testes passam
- [ ] Documentação atualizada
- [ ] Seguiu padrões de código
- [ ] Testou em dispositivo real

### Problemas Comuns

**Erro de build após pull:**
```bash
./gradlew clean
./gradlew --refresh-dependencies
```

**Conflitos de merge:**
```bash
git fetch upstream
git rebase upstream/main
# Resolver conflitos
git rebase --continue
```

**Problemas de sincronização:**
- File → Invalidate Caches and Restart
- Verificar versões no build.gradle

### Comunicação

- Use Issues para reportar bugs
- Use Discussions para perguntas
- Seja respeitoso e construtivo
- Forneça contexto suficiente

### Licença

Ao contribuir, você concorda que suas contribuições serão licenciadas sob a mesma licença do projeto.