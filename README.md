# JPA 복기

### JPA의 기본적인 기능을 구현한다.

#### [개발환경]
* JDK 17
* MySQL 8.0

#### [DB설정]
docker compose up -d
docker-compose exec db /bin/bash
CREATE USER 'deneb'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'deneb'@'%' WITH GRANT OPTION;
flush privileges; 

#### [이력]
* 23.02.10 - 최초 커밋
