# Jornada DevOps PUCPR
<p align="center">	
<a href="http://www.rodrigofujioka.com/" target="_blank"><img src="https://github.com/rodrigofujioka/javabasico/blob/master/resources/javaspion.png" alt="Javaspion" /></a>
</p>

  
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papw&metric=bugs)](https://sonarcloud.io/dashboard?id=rodrigofujioka_papw) 
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papw&metric=code_smells)](https://sonarcloud.io/dashboard?id=rodrigofujioka_papw) 
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papwb&metric=alert_status)](https://sonarcloud.io/dashboard?id=rodrigofujioka_papw) 
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papw&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=rodrigofujioka_papw) 
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papw&metric=ncloc)](https://sonarcloud.io/dashboard?id=rodrigofujioka_papw) 
![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=rodrigofujioka_papw&metric=vulnerabilities)

Olá, seja bem vindo a mais um curso com o professor Rodrigo Fujioka. 

* Meu linkedin: [@rodrigofujioka](https://www.linkedin.com/in/rodrigofujioka/)
* Meu instagram: [@rodrigofujioka](https://www.instagram.com/rodrigofujioka) 
* Meu facebook: [@rodrigofujioka](https://www.facebook.com/rodrigofujioka)

Repositório Programação Web: https://github.com/rodrigofujioka/devops

- [Intellij Idea](https://www.jetbrains.com/idea/) 
- [Spring Tools](https://spring.io/tools)  
- [JDK](https://jdk.java.net/java-se-ri/11)

- ```Outras ferramentas como NetBeans ou VS Code podem ser utilizadas)```

## Cursos recomendados

Cursos recomendados para alunos com dificuldade em assimilar o conteúdo. 

#### GIT
- [GIT para iniciantes](https://www.udemy.com/git-e-github-para-iniciantes/)
- [GIT - DevMedia](https://www.devmedia.com.br/guia/git-e-github/37585)

## Execução do ambiente 

### Jenkins
1.  docker run -it --name jenkins -p8080:8080  rodrigofujioka/jenkins_sonar_postgres:latest 
2.  service jenkins start fujioka:fujioka

### Sonar
1. docker run -it --name sonar -p9000:9000  rodrigofujioka/jenkins_sonar_postgres:latest
2. sudo su sonar 
3. service sonar start admin:admin