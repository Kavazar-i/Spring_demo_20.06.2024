Для запуска этого приложения необходима база данных PostgreSQL и есть 2 варианта запуска.

1) Использовать Docker для запуска PostgreSQL: вы можете использовать docker-compose для запуска PostgreSQL если у вас уже установлен Docker.
2) Установить PostgreSQL локально и настроить его в приложении.
   * Создайте базу данных.
   * Измените имя пользователя и пароль в файле `src/main/resources/application.properties` в соответствии с вашей установкой PostgreSQL (по умолчанию пользователь `postgres` и пароль `postgres`).
   * Измените spring.datasource.url=jdbc:postgresql://localhost:5432/{имя вашей базы данных} (по умолчанию postgres).
3) Запустите приложение.
4) Перейдите на `http://localhost:8080`.

Приятного использования!