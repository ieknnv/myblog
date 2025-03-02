# Приложение-блог

Представляет собой веб-приложение на **Spring MVC**.

## Сборка и запуск
1. **Установка PostgreSQL и создание базы данных:**
    Необходимо установить PostgreSQL (рекомендуемая версия >= 17.3): https://www.postgresql.org/.<br>
    Затем необходимо подключиться к установленной СУБД и создать базу данных приложения и пользователя к ней используя следующие команды:<br>
    ```
    create user <YOUR_USER> with password '<YOUR_PASSWORD>';
    create database <YOUR_DB_NAME> owner <YOUR_USER>;
    ```
   В созданной базе данных необходимо выполнить скрипт **schema.sql** из **../resources**
2. **Задать настройки подключения к БД в application.properties файле:**
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
   mvn clean install
    ```
   Результатом сборки проекта является **myblog.war** файл в **../target**.
4. **Запуск в Tomcat:**<br>
    - Запустите сервер Tomcat.
    - Полученный на третьем шаге WAR файл необходимо поместить в папку **webapps** вашего Tomcat.
5. **Открыть главную страницу приложения в браузере:**<br>
    ```
   http://localhost:8080/myblog/feed
    ```