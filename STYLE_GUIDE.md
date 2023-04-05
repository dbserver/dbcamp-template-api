# O guia de estilo Java

## Inspiração

Este guia de estilo é uma mistura entre a linguagem Java existente
guias de estilo e um guia de estilo Swift focado na legibilidade do tutorial. O idioma
orientação é extraída do
[Guia de estilo para colaboradores do Android](https://source.android.com/source/code-style.html)
e a
[Guia de estilo Java do Google](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html).
As alterações para oferecer suporte à legibilidade adicional nos tutoriais foram inspiradas no
[guia de estilo Swift raywenderlich.com](https://github.com/raywenderlich/swift-style-guide).

## Índice

- [Nomenclatura](#nomenclatura)
    + [Pacotes](#pacotes)
    + [Classes e interfaces](#classes--interfaces)
    + [Métodos](#métodos)
    + [Campos](#campos)
    + [Variáveis ​​e Parâmetros](#variáveis--parâmetros)
    + [Misc](#misc)
- [Declarações](#declarações)
    + [Modificadores de nível de acesso](#modificadores de nível de acesso)
    + [Campos e variáveis](#campos--variáveis)
    + [Aulas](#aulas)
    + [Enum Classes](#enum-classes)
- [Espaçamento](#espaçamento)
    + [Recuo](#recuo)
    + [Comprimento da linha](#comprimento da linha)
    + [Espaçamento vertical](#espaçamento vertical)
- [Getters & Setters](#getters--setters)
- [estilo de cinta](#estilo de cinta)
- [Switch Statements](#switch-statements)
- [Anotações](#anotações)
- [Orientação XML](#xml-guidance)
    + [Nomes de arquivos XML](#xml-file-names)
    + [Recuo](#recuo-1)
    + [Usar arquivos XML específicos de contexto](#use-context-specific-xml-files)
    + [Ordenação de atributos XML](#xml-attribute-ordering)
- [Idioma](#idioma)
- [Declaração de direitos autorais](#copyright-statement)
- [Cara sorridente](#cara sorridente)
- [Crédito](#créditos)


## Nomenclatura

No geral, a nomenclatura deve seguir os padrões Java.

### Pacotes

Os nomes dos pacotes são todos __minúsculas__, várias palavras concatenadas,
sem
hifens ou sublinhados:

__RUIM__:

```java
com.RayWenderlich.funky_widget
```

__BOM__:

```java
com.raywenderlich.funkywidget
```

### Classes e Interfaces

Escrito em __UpperCamelCase__. Por exemplo `RadialSlider`.

### Métodos

Escrito em __lowerCamelCase__. Por exemplo `setValue`.

### Campos

Escrito em __lowerCamelCase__.

Campos estáticos devem ser escritos em __maiúsculas__, com um sublinhado separando
palavras:

```java
public static final int THE_ANSWER = 42;
```

Por mais desagradável que seja, a nomenclatura dos campos deve seguir o código-fonte do Android
convenções de nomenclatura:

- Nomes de campos não públicos e não estáticos começam com um `m`.
- Nomes de campos estáticos começam com um `s`.

Por exemplo:

```java
public class MinhaClasse {
  public static final int SOME_CONSTANT = 42;
  public int publicField;
  privado estático MyClass sSingleton;
  int mPackagePrivate;
  privado int mPrivado;
  protegido int mProtegido;
}
```

> __Nota:__ Você pode configurar o Android Studio para seguir esta convenção. Veja este SO
> link para detalhes http://stackoverflow.com/questions/22732722/intellij-android-studio-member-variable-prefix

### Variáveis ​​e Parâmetros

Escrito em __lowerCamelCase__.

Valores de caractere único devem ser evitados, exceto para variáveis ​​de loop temporárias.

### Diversos

No código, as siglas devem ser tratadas como palavras. Por exemplo:

__RUIM:__

```java
XMLHTTPRequest
URL da string
findPostByID
```
__BOM:__

```java
XmlHttpRequest
url da string
findPostById
```

## Declarações

### Modificadores de nível de acesso

Modificadores de nível de acesso devem ser explicitamente definidos para classes, métodos e
variáveis ​​de membro.

### Campos e Variáveis

Prefira declaração única por linha.

__RUIM:__

```java
Nome de usuário da string, twitterHandle;
```

__BOM:__

```java
Nome de usuário da string;
String twitterHandle;
```

### Aulas

Exatamente uma classe por arquivo fonte, embora classes internas sejam encorajadas onde
escopo apropriado.


### Classes de enumeração

As classes Enum devem ser evitadas sempre que possível, devido a uma grande sobrecarga de memória.
Constantes estáticas são preferidas. Consulte http://developer.android.com/training/articles/memory.html#Overhead
para mais detalhes.

Classes Enum sem métodos podem ser formatadas sem quebras de linha, como segue:

```java
private enum CompassDirection { EAST, NORTH, WEST, SOUTH }
```

## Espaçamento

O espaçamento é especialmente importante no código raywenderlich.com, pois o código precisa ser
facilmente legível como parte do tutorial. Java não se presta bem a isso.

### Recuo

O recuo está usando espaços - nunca tabulações.

#### Blocos

A indentação para blocos usa 2 espaços (não os 4 padrão):

__RUIM:__

```java
for (int i = 0; i < 10; i++) {
    Log.i(TAG, "index=" + i);
}
```

__BOM:__

```java
for (int i = 0; i < 10; i++) {
  Log.i(TAG, "index=" + i);
}
```

#### Quebras de linha

O recuo para quebras de linha deve usar 4 espaços (não o padrão 8):

__RUIM:__

```java
Widget CoolUiWidget =
        someIncredablyLongExpression(isso, realmenteNãoCaberia, em uma única linha);
```

__BOM:__

```java
Widget CoolUiWidget =
    someIncredablyLongExpression(isso, realmenteNãoCaberia, em uma única linha);
```

### Comprimento da linha

As linhas não devem ter mais de 100 caracteres.


### Espaçamento Vertical

Deve haver exatamente uma linha em branco entre os métodos para ajudar na clareza visual
e organização. O espaço em branco dentro dos métodos deve separar a funcionalidade, mas
ter muitas seções em um método geralmente significa que você deve refatorar
vários métodos.

## Getters e Setters

Para acesso externo a campos em classes, getters e setters são preferidos
acesso direto aos campos. Os campos raramente devem ser `públicos`.

No entanto, é recomendável usar o campo diretamente ao acessar internamente
(ou seja, de dentro da classe). Esta é uma otimização de desempenho recomendada
pelo Google: http://developer.android.com/training/articles/perf-tips.html#GettersSetters

## Estilo de Braçadeira

Apenas os colchetes finais recebem sua própria linha. Todos os outros aparecem
mesma linha do código anterior:

__RUIM:__

```java
classe MinhaClasse
{
  void fazerAlgo()
  {
    if (someTest)
    {
      // ...
    }
    outro
    {
      // ...
    }
  }
}
```

__BOM:__

```java
classe MinhaClasse {
  void fazerAlgo() {
    if (algumTeste) {
      // ...
    } outro {
      // ...
    }
  }
}
```

As declarações condicionais devem sempre ser colocadas entre chaves,
independentemente do número de linhas necessárias.

__RUIM:__

```java
if (someTest)
  faça alguma coisa();
if (someTest) doSomethingElse();
```

__BOM:__

```java
if (algumTeste) {
  faça alguma coisa();
}
if (someTest) { doSomethingElse(); }
```


## Instruções de troca

As instruções switch falham por padrão, mas isso pode não ser intuitivo. Se você
exigir este comportamento, comente-o.

Sempre inclua o caso `default`.

__RUIM:__

```java
switch (uma entrada) {
  caso 1:
    doSomethingForCaseOne();
  caso 2:
    doSomethingForCaseOneOrTwo();
    quebrar;
  caso 3:
    doSomethingForCaseOneOrThree();
    quebrar;
}
```

__BOM:__

```java
switch (uma entrada) {
  caso 1:
    doSomethingForCaseOne();
    // Cair em
  caso 2:
    doSomethingForCaseOneOrTwo();
    quebrar;
  caso 3:
    doSomethingForCaseOneOrThree();
    quebrar;
  padrão:
    quebrar;
}
```

## Anotações

Anotações padrão devem ser usadas - em particular `@Override`. Isto deveria
aparece a linha antes da declaração da função.

__RUIM:__

```java
void protegido onCreate(Pacote salvadoInstanceState) {
  super.onCreate(savedInstanceState);
}
```

__BOM:__

```java
@Sobrepor
void protegido onCreate(Pacote salvadoInstanceState) {
  super.onCreate(savedInstanceState);
}
```


## Orientação XML

Como o Android usa XML extensivamente além de Java, temos algumas regras
específico para XML.

### Nomes de arquivos XML

Arquivos XML baseados em exibição devem ser prefixados com o tipo de exibição que eles
representar.

__RUIM:__

- `login.xml`
- `main_screen.xml`
- `rounded_edges_button.xml`

__BOM:__

- `activity_login.xml`
- `fragment_main_screen.xml`
- `button_rounded_edges.xml`

### Recuo

Da mesma forma que em Java, a indentação deve ter __dois caracteres__.

### Use arquivos XML específicos de contexto

Sempre que possível, os arquivos de recursos XML devem ser usados:

- Strings => `res/values/strings.xml`
- Estilos => `res/values/styles.xml`
- Cores => `res/color/colors.xml`
- Animações => `res/anim/`
- Drawable => `res/drawable`


### Ordenação de atributos XML

Quando apropriado, os atributos XML devem aparecer na seguinte ordem:

- atributo `id`
- atributos `layout_*`
- atributos de estilo como `gravity` ou `textColor`
- atributos de valor como `text` ou `src`

Dentro de cada um desses grupos, os atributos devem ser ordenados alfabeticamente.


## Linguagem

Use a ortografia do inglês dos EUA.

__RUIM:__

```java
Cor da string = "vermelho";
```

__BOM:__

```java
Cor da string = "vermelho";
```

## Declaração de direitos autorais

A seguinte declaração de direitos autorais deve ser incluída no topo de cada fonte
arquivo:

    /*
     * Direitos autorais (c) 2017 Razeware LLC
     *
     * A permissão é concedida, gratuitamente, a qualquer pessoa que obtenha uma cópia
     * deste software e arquivos de documentação associados (o "Software"), para lidar
     * no Software sem restrições, incluindo, sem limitação, os direitos
     * para usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e/ou vender
     * cópias do Software, e para permitir que as pessoas a quem o Software é
     * fornecido para fazê-lo, sujeito às seguintes condições:
     *
     * O aviso de direitos autorais acima e este aviso de permissão devem ser incluídos em
     * todas as cópias ou partes substanciais do Software.
     *
     * Não obstante o acima, você não pode usar, copiar, modificar, mesclar, publicar,
     * distribuir, sublicenciar, criar um trabalho derivado e/ou vender cópias do
     * Software em qualquer trabalho que seja projetado, destinado ou comercializado para fins pedagógicos ou
     * propósitos instrucionais relacionados à programação, codificação, desenvolvimento de aplicativos,
     * ou tecnologia da informação. Permissão para tal uso, cópia, modificação,
     * fusão, publicação, distribuição, sublicenciamento, criação de obras derivadas,
     * ou a venda é expressamente retida.
     *
     * O SOFTWARE É FORNECIDO "COMO ESTÁ", SEM GARANTIA DE QUALQUER TIPO, EXPRESSA OU
     * IMPLÍCITA, INCLUINDO MAS NÃO SE LIMITANDO ÀS GARANTIAS DE COMERCIALIZAÇÃO,
     * ADEQUAÇÃO PARA UM FIM ESPECÍFICO E NÃO VIOLAÇÃO. EM NENHUM CASO O
     * OS AUTORES OU DETENTORES DOS DIREITOS AUTORAIS SERÃO RESPONSÁVEIS POR QUALQUER REIVINDICAÇÃO, DANOS OU OUTROS
     * RESPONSABILIDADE, SEJA EM UMA AÇÃO DE CONTRATO, ILÍCITO OU DE OUTRA FORMA, DECORRENTE DE,
     * FORA DE OU EM CONEXÃO COM O SOFTWARE OU O USO OU OUTROS NEGÓCIOS EM
     * O SOFTWARE.
     */

## Rosto sorridente

Os rostos sorridentes são um recurso de estilo muito proeminente do site raywenderlich.com!
É muito importante ter o sorriso correto significando a imensa quantidade de
felicidade e entusiasmo pelo tópico de codificação. O colchete de fechamento ] é
usado porque representa o maior sorriso capaz de ser capturado usando ASCII
arte. Um parêntese de fechamento) cria um sorriso tímido e, portanto, não é
preferido.

Ruim:

    :)

Bom:

    :]

## Créditos

Este guia de estilo é um esforço colaborativo dos mais elegantes
membros da equipe raywenderlich.com:

- [Darryl Bayliss](https://github.com/DarrylBayliss)
- [Sam Davies](https://github.com/sammyd)
- [Microfone Pringle](https://github.com/micpringle)
- [Ray Wenderlich](https://github.com/rwenderlich)
