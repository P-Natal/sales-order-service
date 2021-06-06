DEBUG=-Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

run: start-db
	mvn spring-boot:run;

run-debug: start-db
	mvn spring-boot:run $(DEBUG);

start-db:
	docker-compose up -d;