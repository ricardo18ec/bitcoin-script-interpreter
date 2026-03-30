# Bitcoin Script Interpreter

## Descripción

Este proyecto implementa un intérprete simplificado de Bitcoin Script en Java.  
El objetivo es simular el proceso de validación de scripts utilizando un modelo basado en pila.

El intérprete ejecuta instrucciones de forma secuencial y determina si un script es válido en función del valor final en la pila.

---

## Funcionalidades

- Evaluación de scripts basados en pila
- Soporte para operaciones básicas:
  - OP_DUP
  - OP_DROP
  - OP_EQUAL
  - OP_EQUALVERIFY
- Operaciones criptográficas simuladas:
  - OP_HASH160
  - OP_CHECKSIG
- Control de flujo:
  - OP_IF
  - OP_ELSE
  - OP_ENDIF
- Modo trace para visualizar el estado de la pila paso a paso
- Validación del esquema P2PKH (caso simplificado)

---

## Estructura del proyecto

- ScriptInterpreter: ejecuta el script y controla la lógica
- StackMachine: implementa la pila
- Instruction: representa una instrucción del script
- Opcode: define las operaciones disponibles
- CryptoMock: simula funciones criptográficas
- Main: contiene ejemplos de ejecución

---

## Cómo ejecutar

1. Compilar el proyecto:

javac src/script/*.java

2. Ejecutar el programa:

java -cp src script.Main

---

## Ejemplos incluidos

El programa muestra:

- Validación de P2PKH correcta
- Validación de P2PKH incorrecta
- Ejemplo de control de flujo con IF / ELSE

---

## Pruebas

El proyecto incluye pruebas para validar:

- Operaciones de pila
- Comparaciones
- Validación de scripts
- Control de flujo (IF / ELSE)

Para ejecutar las pruebas:

javac src/script/*.java test/script/*.java  
java -cp src;test script.ScriptInterpreterTest

---

## Notas

- No se utiliza criptografía real, solo simulaciones.
- El objetivo del proyecto es educativo, centrado en la lógica de ejecución.

---

## Autores

- Ricardo Escobar 25891
- María Renée Mazariegos 25598
