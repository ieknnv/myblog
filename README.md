# Приложение-блог

Представляет собой веб-приложение на **Spring Boot**.

## Сборка и запуск
1. **Установка PostgreSQL и создание базы данных:**
    Необходимо установить PostgreSQL (рекомендуемая версия >= 17.3): https://www.postgresql.org/.<br>
    Затем необходимо подключиться к установленной СУБД и создать базу данных приложения и пользователя к ней используя следующие команды:<br>
    ```
    create user <YOUR_USER> with password '<YOUR_PASSWORD>';
    create database <YOUR_DB_NAME> owner <YOUR_USER>;
    ```
   В созданной базе данных необходимо выполнить скрипт **schema.sql** из **../resources**
2. **Задать настройки подключения к БД в application.yaml файле:**
    Файл свойств находится в каталоге **../resourses**<br>
    В нем необходимо прописать настройки подключения к БД из шага 1.<br>
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DB_NAME>
    spring.datasource.username=<YOUR_USER>
    spring.datasource.password=<YOUR_PASSWORD>
    ```
3. **Сборка проекта:**<br>
    Для сборки проекта необходимо выполнить следующую команду:
    ```
   gradle clean bootJar
    ```
   Результатом сборки проекта является "fat" JAR файл в **../build/libs**.
4. **Запуск:**<br>
    - Запустите полученный на 3 шаге JAR командой:
    ```
   java -jar <JAR_NAME>.jar
    ```
5. **Открыть главную страницу приложения в браузере:**<br>
    ```
   http://localhost:8080/feed
    ```