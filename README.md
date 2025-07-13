# MediPlan

Aplicativo Android para gestão de medicamentos e planos de saúde.

## 🚀 Configuração do Projeto

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- Android SDK API 21+ (Android 5.0)
- Git

### Configuração Inicial

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/mediplan.git
   cd mediplan
   ```

2. **Abra no Android Studio:**
   - File → Open
   - Selecione a pasta do projeto
   - Aguarde a sincronização do Gradle

3. **Configure o SDK (se necessário):**
   - Tools → SDK Manager
   - Instale as versões necessárias do Android SDK

### 🔧 Resolução de Problemas

Se encontrar problemas ao atualizar o projeto, consulte o [Guia de Troubleshooting](TROUBLESHOOTING.md).

**Problemas comuns:**
- Erro de sincronização do Gradle → Limpe o cache: `./gradlew clean`
- "untracked files prevent merge" → Mova ou adicione arquivos ao Git
- Conflitos de merge → Use `git stash` antes de fazer pull
- SDK não encontrado → Verifique o SDK Manager

### 📱 Build e Execução

```bash
# Build debug
./gradlew assembleDebug

# Instalar no dispositivo
./gradlew installDebug

# Executar testes
./gradlew test
```

### 🤝 Colaboração

**Antes de fazer push:**
1. Teste o build localmente
2. Verifique se não há arquivos sensíveis
3. Use mensagens de commit descritivas

**Antes de fazer pull:**
1. Faça backup das mudanças importantes
2. Use `git stash` se necessário
3. Resolva conflitos cuidadosamente

### 📂 Estrutura do Projeto

```
mediplan/
├── app/                    # Código principal do app
├── gradle/                 # Configurações do Gradle
├── .gitignore             # Arquivos ignorados pelo Git
├── gradle.properties      # Propriedades do Gradle
├── TROUBLESHOOTING.md     # Guia de resolução de problemas
└── README.md              # Este arquivo
```

### 🆘 Suporte

Se os problemas persistirem:
1. Consulte o [TROUBLESHOOTING.md](TROUBLESHOOTING.md)
2. Verifique as [Issues](https://github.com/seu-usuario/mediplan/issues)
3. Crie uma nova issue com detalhes do problema

---

**Versões Recomendadas:**
- Android Studio: 2023.1.1+
- Gradle: 8.0+
- Android Gradle Plugin: 8.1.0+
- Kotlin: 1.9.0+