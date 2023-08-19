How To Build and Run Docker .
=============================================

1-For Build Docker file use the below command : 

docker build -t fxdeal .


2- For Run Docker Postgress Image use the below command :
 docker run -itd -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=sgbj@2022 -p 5432:5432 -v /data:/var/lib/postgresql/data  
--name postgresql postgres

3-For Run Docker Application Image use The below Command :

docker run -itd -p 8094:8094 -e HOST=localhost -e PORTDB=5432 -e USERNAME=postgres -e PASSWORD=sgbj@2022 -e PORT=8094 --network host fxdeal
