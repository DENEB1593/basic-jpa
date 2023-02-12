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

#### [참고사항]
- DB상 생성되는 unique constraint 제어는 @UniqueConstraint를 이용한다.
- @Query를 사용하여 JPQL 규칙을 이용한 기능을 구현한다. native옵션을 통해 사용하는 DBMS 문법과 호환되는 query를 작성할 수 있다.
- @Param을 사용하여 JPQL의 매개변수명을 지정할 수 있다.
- [Spring Documentation JPQL](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

#### [이력]
* 23.02.10 - 최초 커밋
* 23.02.12 - @Query 추가
