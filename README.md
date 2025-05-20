# Projet_genie_logiciel_groupe_16_CY-SIAO
<p align="justify">This is a software engineering project in Java by group 16 on 'CY-SIAO', which involves the development of an application for managing an emergency shelter.</p>

<p>For being able to execute the project, be sure to have the Java 21.0.6 version.
To check it, you can execute the following command on your terminal:<br/>

``java -version``<br/>

Otherwise, install it by executing on your terminal:<br/>

Windows: ``choco install openjdk``<br/>
Linux: ``sudo apt update``, ``sudo apt install default-jdk``<br/>
</p>

<p align="justify">Before continuing, be sure to have a Maven version installed (3.8.1 or later). To check it, you can execute :<br/>

``mvn -v``<br/>

Otherwise, install it by executing: <br/>

Windows: ``choco install maven``<br/>
Linux: ``sudo apt update``, ``sudo apt install maven``</p>

## Project and dependencies initialization:
<p align="justify">First, you need to generate dependencies and the target files (.class) by:<br/>

``mvn compile``<br/>

Then, for executing the Main file, you need to execute the following:<br/>

``mvn exec:java -Dexec.mainClass="com.cy_siao.Main"``<br/>

That's it.</p>


Before staging/committing your changes, be sure to execute the following in order to not push the generated dependencies and target files into remote repository:<br/>

``mvn clean``<br/>

</p>


