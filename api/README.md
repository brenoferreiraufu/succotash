# Succotash 

## Tecnologias Utilizadas

- Java
- Spring Boot
- JPA
- Hibernate
- Postgresql
- Lombok
- Junit
- Postman
- Heroku

## Ambiente de desenvolvimento

  1. Clone o repósitorio e abra-o em uma IDE (Ex: Intellij)
  2. Instale o docker (https://www.docker.com/products/docker-desktop/) e abra-o
  3. Navegue pelo terminal até src/main/resources ou navegue pela IDE até src/main/resources/docker-compose.yaml
  4. Execute o comando docker-compose up -d ou aperte o botão play localizado ao lado do campo services
  5. Navegue pela IDE até ApiPagamentoApplication 
  6. Aperte o botão play localizado ao lado de "public class SuccotashApplication"
  7. Acesse o postman (https://web.postman.co/l) e importe a coleção succotash.postman_collection.json localizada na pasta api 
  8. Faça a requisição desejada
 
 ## Ambiente de homolagação
 
  1. Acesse https://dashboard.heroku.com/
  2. Realize o login
  3. Crie um app
  4. Clone o repósitorio e abra-o em uma IDE (Ex: Intellij)
  5. Navegue pelo terminal até a pasta https://github.com/brenoferreiraufu/succotash/tree/main/api
  6. Digite heroku login
  7. Realize o login 
  8. Digite heroku git:clone -a ufu-succotash 
  9. Digite git add .
  10. Digite commit -am "Update"
  11. Digite git push heroku head:master
  12. Para atualizar, repita o passo 9, 10 e por fim digite git push heroku main:main
  
 ## Funcionalidades
 
 1. GenerateAuthenticationToken -> /api/v1/auth
 2. FindItemByRestaurant -> /api/v1/item
 3. EditOrder -> /api/v1/order/{orderId}
 4. newOrder -> /api/v1/order/
 5. findOrder -> /api/v1/order/{orderId}
 6. payOrder -> /api/v1/order/{orderId}/pay
 7. cancelOrderItem -> /api/v1/order/{orderItemId}
 8. findTableByRestaurant ->  /api/v1/table
 9. getTableLastOrder -> /api/v1/table/{tableId}/order
 10. findTableByid -> /api/v1/table/{tableId}
 11. newUser -> /api/v1/user
 12. findUser -> /api/v1/user/{userId}
