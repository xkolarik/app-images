Building  a Docker image consumer

1 - mvn clean install  <br /> 
    utilizar caminho: /consumer <br />  

2 - docker build -t consumer-image .   <br /> 
    utilizar caminho: /consumer <br />  

3 - docker run -p 8080:8080 consumer-image  <br />
    utilizar caminho: /consumer <br />  

4 - Rodar o docker-compose.yaml <br />  
    utilizar caminho: /app-image  <br /> 
    - mongodb:       <br /> 
    - mongo-express: <br /> 
    - minio:         <br /> 
    - zookeeper:     <br /> 
    - kafka:         <br /> 
<br />
-------------------------------------------- <br />
<br />
Building  a Docker image producer  <br />
5  - mvn clean install  <br />
       utilizar:  /producer  <br />

6  - Executar o producer local <br />
       utilizar:  /producer  <br />

7  - Testar via postmam  <br />
     - POST: http://localhost:8080/api/upload?contentType=image/jpeg&fileName=end-to-end-1  <br />
     - body - fomr-data { key: image, value: arquivo.jpg }  <br />
       utilizar:  /producer  <br />
     
8  - acessar http://localhost:9001/  -   <br />
   - minio.bucket.name=images  ->  criar bucket com nome: images ou de sua escolhar porem terá de alterar no arquivo aplication.properties  <br />
   - minio.access.key=minio    -> username  <br />
   - minio.secret.key=minio123 -> password  <br />
   
9  - acessar http://localhost:8081/ mongo-express  <br />

10 - Assim que for executado o passo 7 voce poderá conferir o insert da na tabela TBL_Dados_Image database images ou conforme suas configurações!  <br />

![image](https://user-images.githubusercontent.com/16133605/218030285-75164a37-d776-4449-ad3c-b0586cd669bc.png) 


!!!
 
  

