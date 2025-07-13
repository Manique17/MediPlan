# Guia de Resolução de Problemas - MediPlan

## Problemas Comuns com GitHub e Android Studio

### 1. Erro de Sincronização do Gradle

**Sintomas:**
- "Gradle sync failed"
- "Could not resolve dependencies"
- "Failed to resolve: ..."

**Soluções:**
1. **Limpar cache do Gradle:**
   ```bash
   ./gradlew clean
   ./gradlew --refresh-dependencies
   ```

2. **No Android Studio:**
   - File → Invalidate Caches and Restart
   - Build → Clean Project
   - Build → Rebuild Project

3. **Verificar versões:**
   - Gradle Wrapper: `gradle/wrapper/gradle-wrapper.properties`
   - Android Gradle Plugin: `build.gradle` (Project level)
   - Compile SDK e Target SDK: `build.gradle` (App level)

### 2. Problemas de Merge/Pull do Git

**Sintomas:**
- "Your local changes would be overwritten by merge"
- "Automatic merge failed"
- Conflitos de merge

**Soluções:**
1. **Fazer stash das mudanças locais:**
   ```bash
   git stash
   git pull origin main
   git stash pop
   ```

2. **Resolver conflitos manualmente:**
   - Abrir arquivos com conflitos
   - Resolver marcações `<<<<<<<`, `=======`, `>>>>>>>`
   - Fazer commit das resoluções

3. **Reset para versão remota (CUIDADO - perde mudanças locais):**
   ```bash
   git fetch origin
   git reset --hard origin/main
   ```

### 3. Problemas de Configuração do Android Studio

**Sintomas:**
- "SDK not found"
- "Build tools not found"
- Projeto não abre corretamente

**Soluções:**
1. **Verificar SDK Manager:**
   - Tools → SDK Manager
   - Instalar Android SDK necessário
   - Instalar Build Tools correspondente

2. **Configurar local.properties:**
   ```properties
   sdk.dir=/caminho/para/android/sdk
   ```

3. **Verificar Project Structure:**
   - File → Project Structure
   - Verificar SDK Location
   - Verificar JDK Location

### 4. Problemas de Dependências

**Sintomas:**
- "Failed to resolve: androidx..."
- "Duplicate class found"
- "Version conflict"

**Soluções:**
1. **Atualizar repositórios no build.gradle (Project):**
   ```gradle
   allprojects {
       repositories {
           google()
           mavenCentral()
           jcenter() // Deprecated, remover se possível
       }
   }
   ```

2. **Verificar versões das dependências:**
   - Usar versões compatíveis
   - Verificar changelog das bibliotecas
   - Usar BOM (Bill of Materials) quando disponível

### 5. Problemas de Encoding/Caracteres

**Sintomas:**
- Caracteres especiais não aparecem corretamente
- Erro de compilação com acentos

**Soluções:**
1. **Configurar encoding no gradle.properties:**
   ```properties
   org.gradle.jvmargs=-Dfile.encoding=UTF-8
   android.useAndroidX=true
   android.enableJetifier=true
   ```

2. **No Android Studio:**
   - File → Settings → Editor → File Encodings
   - Definir tudo como UTF-8

## Checklist para Colaboração

### Antes de fazer Push:
- [ ] Testar build local: `./gradlew assembleDebug`
- [ ] Verificar se não há arquivos sensíveis (senhas, keys)
- [ ] Verificar .gitignore atualizado
- [ ] Fazer commit com mensagem descritiva

### Antes de fazer Pull:
- [ ] Fazer backup das mudanças locais importantes
- [ ] Verificar se há conflitos pendentes
- [ ] Fazer stash se necessário
- [ ] Testar após pull

### Configuração Inicial do Projeto:
1. **Clonar repositório:**
   ```bash
   git clone https://github.com/usuario/mediplan.git
   cd mediplan
   ```

2. **Abrir no Android Studio:**
   - File → Open
   - Selecionar pasta do projeto
   - Aguardar sync do Gradle

3. **Configurar SDK se necessário:**
   - Seguir prompts do Android Studio
   - Instalar SDKs sugeridos

## Comandos Úteis

### Git:
```bash
# Verificar status
git status

# Ver diferenças
git diff

# Ver histórico
git log --oneline

# Desfazer último commit (mantém mudanças)
git reset --soft HEAD~1

# Ver branches remotos
git branch -r

# Atualizar referências remotas
git fetch --all
```

### Gradle:
```bash
# Limpar projeto
./gradlew clean

# Build debug
./gradlew assembleDebug

# Verificar dependências
./gradlew dependencies

# Verificar tasks disponíveis
./gradlew tasks
```

## Contato para Suporte

Se os problemas persistirem:
1. Documentar erro exato (screenshot/log)
2. Informar versões (Android Studio, Gradle, SDK)
3. Descrever passos que levaram ao erro
4. Verificar se problema ocorre em projeto novo/limpo