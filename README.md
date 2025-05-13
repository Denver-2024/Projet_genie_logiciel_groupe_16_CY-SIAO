# Projet_genie_logiciel_groupe_16_CY-SIAO
Ceci est un projet de géniel logiciel sur java du groupe 16 sur "CY-SIAO" une application de gestion d'un hebergement d'urgence

Pour l'execution s'assurer d'avoir Java 21.0.6

## Initialisation du projet et des dépendances
Generation des dépendances

``mvn compile``

Avant de push votre travail, executer

``mvn clean``

Ce qui permet de ne pas push les dependanes et les target generer  l'execution

Pour lancer le Main, pour l'instant je n'arrive pas encore à l'automatiser alors il faut,

``mvn exec:java -Dexec.mainClass="com.cy_siao.Main"
``