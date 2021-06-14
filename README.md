# Wishlist

## Subindo
Adequar o application.properties com a url de conexão ao mongo e subir com `mvn spring-boot:run`

## WishlistService.add
O `POST /wishlist/{productId}` trata a tentativa de adicionar um produto que já está na lista não como um erro mas como uma atualização, a fim de evitar disparar uma exceção desnecessária.

## WebSecurityconfig
WebSecurityconfig básico (com http basic) criado apenas para poder fornecer o usuário através do header e não recebê-lo por param

Foi também criado um usuário user/user com um auth provider que aceita qualquer coisa pra poder fazer algum teste funcional

## Métrica
Usado o micrometer pra uma métrica simples de quantidades de updates, inserts e deletes executados

## Testes
Os testes de integração testam o processo geral em uma ordem planejada e os testes unitários testam os casos e regras mais específicas de forma mais independente

Editar o .properties para corrigir a url de conexão ao mongodb

Para usar o Embedded Mongodb nos testes, basta descomentar a linha `@Import(EmbeddedMongoAutoConfiguration.class)`
