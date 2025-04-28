
C:\Users\ionut\AppData\Roaming\Docker\extensions\localstack_localstack-docker-desktop\vm\docker-compose.yaml


From youtube: Patient Management System with Microservices: Java Spring Boot AWS: 
https://www.youtube.com/watch?v=tseqdcFfTUY
https://github.com/chrisblakely01/java-spring-microservices

Docker on Linux
	https://superuser.com/questions/1808237/how-does-docker-desktop-work-on-linux-ubuntu	
	https://docs.docker.com/desktop/troubleshoot-and-support/faqs/linuxfaqs/#what-is-the-difference-between-docker-desktop-for-linux-and-docker-engine
	https://medium.com/@senali/what-docker-desktop-and-docker-engine-in-linux-2ed9f140940
		
	There are 2 options to install and run docker on linux.
	
	1. Docker Desktop which runs a Virtual Machine (VM) 
	It creates and uses a custom docker context: desktop-linux.
	It does this instead of relying on the "native" docker engine for several reasons like
	ensuring a consistent experience accross platforms.
	make use of new kernel features. Inside the VM it controls the kernel.
	enhance security.
	
	We install by downloading the DEB package from docker website and isntall the DEB
	sudo apt-get install ./docker-desktop-<version>-<arch>.deb
	
	An then we start the DockerDesktop GUI app which will start the VM and then we can use
	the docker CLI from out terminal which will talk to the VM via that docker context.
	
	2. Docker engine installed on the machine OS, which is the "native" way in Linux
	
	We install it by installing the docker specific packages
	sudo apt-get install 
		docker-ce 
		docker-ce-cli 
		containerd.io 
		docker-buildx-plugin docker-compose-plugin 
	
	Docker Engine is the fundamental containerization engine that runs on servers and manages containers, 
	while Docker Desktop is a developer-focused tool that includes Docker Engine running in a VM 
	with additional features to simplify the development and testing of containerized applications.
	
	NOTE: since Docker Desktop actually start a VM inside which the docker engine runs, 
	there is no docker service running directly on our machine, it is inside the VM
	
	We run Docker Desktop app from GUI, it will have in icon in the top-right bar of the screen
	once started. It can be configured to start automatically when we log in.
	
	Theoretically we could have both Docker Desktop and Docker Engine on our machine
	but it's not necessarily recomended. And we use the docker CLI command
	and we can switch between these profiles, default for native engine or desktop-linux.
	
	sudo systemctl stop docker docker.socket containerd // stop native docker engine
	docker context ls  // see the contexts
	docker context use desktop-linux
	docker context use default
	
	///////////////////////////////////////////////
	
	docker-compose vs docker compose : Use the one with space on Linux
	
	docker-compose version // result: docker-compose version 1.25.0
	docker compose version // result: Docker Compose version v2.34.0-desktop.1 
	
	It seems that docker-compose is an older tool that is replaced by docker compose
	On Windows the docker-compose seem up-to-date, 
	but on Linux certain docker-compose.yaml files might not work.
	This might be fixed by specifying in the compose file the version.
	But on Linux we will be better of using docker compose
	
	https://stackoverflow.com/questions/66514436/difference-between-docker-compose-and-docker-compose
	The docker compose (with a space) is a newer project to migrate compose 
	to Go with the rest of the docker project. 
	This is the v2 branch of the docker/compose repo.
	
	The original python project, called docker-compose, aka v1 of docker/compose repo, 
	has now been deprecated and development has moved over to v2. 
	
	Linux installs have been updated by Docker to include compose v2 
	and docker-compose v1 is unlikely to receive any more updates.

	
Linux services and packages related commands
	
	apt list --installed // list all packages
	apt list --installed | grep docker
	
	apt show docker // more info about the docker package
	
	To list services:
	service --status-all
	systemctl list-units --type service --all	

//////////////////////////////////

patient-service

Why use separate Entity Data classes in Repository layer ( domain classes )
And other classes in Controller to be passed to client - named DTOs ?
( the conversion DTO <-> Entity is done on the Service layer )
	-	keep the domain model( database entities ) hidden from the client.
	-   allow to send or receive only the fields relevant to the client 
		( this could be done be JSON serialization - @JSONIgnore kind of stuff ?? 
		probably it is clearer/safer to use sepparate classes  )
	-   allow to validate client input. 
		( hmm, this could be done with de Domain Entites also :) )
	-	the DTO can be populated only with "more basic" types like string and numeric
		as more complicated types from DB Entity like Date, UUID might be a problem for JSON serialization
	-	we might need slightly different validations for request/response DTOs vs the Entity	
	
Populate the database with data.
	
	# instructs Spring to update the database table definitions if it detects
	# changes versus the JPA Entity classes 
	spring.jpa.hibernate.ddl-auto=update
	# instructs Spring to perform the check if there are changes between
	# database tables and JPA Entities every time the application starts
	spring.sql.init.mode=always
	
	Providing a data.sql file in src/main/resources will make spring boot execute the sql code from it.
	We should use create if not exists, insert if not exist type of statements.
	Probably this step happens before Spring trying to create or update
    the database tables according to spring.jpa.hibernate.ddl-auto property
		
		
Swagger / Open API documentation 
	
	( https://swagger.io/ https://springdoc.org/ )		
	a tool and a standard for designing, documenting and interracting with APIs	
	
	To enable the generation of API documentation we must annotate the REST Controller and its methods.
	For this we need an additional dependency from https://springdoc.org/ 
    	org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5
   
	@RestController
	@RequestMapping("/patients")
	@Tag(name = "Patinet", description = "API for managing Patients") 
	public class PatientResource {
		
		@GetMapping
		@Operation(summary = "Get All Patients")
		public ResponseEntity<List<PatientResponseDTO>> getPatients() {
		
	The documentation will be available in JSON format on:  http://localhost:4000/v3/api-docs	
	We can see a nicer view af the information by getting the contents of the JSON 
	by loading into in the Online Swagger Editor at https://swagger.io/tools/swagger-editor/
		
docker build -t patient-service:latest .	
docker run -itd --name patient-service patient-service:latest	
docker exec -it my_new_container /bin/bash	
docker exec -it patient-service /bin/bash	
docker stop patient-service
docker container rm patient-service
docker run -d --name patient-service -p 4000:4000 patient-service:latest	
docker network create -d bridge clinic-network

	
	
