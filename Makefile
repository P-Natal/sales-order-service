DEBUG=-Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

run: start-db
	mvn spring-boot:run

run-debug: start-db
	mvn spring-boot:run $(DEBUG)

start-db:
	docker-compose up -d

build-image:
	mvn clean package
	docker build -t pedronatal/sales-order-service:1.0 .

build-run: build-image
	docker-compose up

build-run-d: build-image
	docker-compose up -d
