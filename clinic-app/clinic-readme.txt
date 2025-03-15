
C:\Users\ionut\AppData\Roaming\Docker\extensions\localstack_localstack-docker-desktop\vm\docker-compose.yaml

Why use separate Entity Data classes in 
Repository layer - named domain classes
And other classes in Controller to be passed to client - named DTOs ?
	-   keep the domain model( database entities ) hidden from the client.
	=   allow to send or receive only the fields relevant to the client 
		( this could be done be JSON serialization - @JSONIgnore kind of stuff ?? 
		probably it is clearer/safer to use sepparate classes  )
	-   allow to validate client input. 
		( hmm, this could be done with de Domain Entites also :) )
	-	the DTO can be populated only with "more basic" types like string and numeric
		as more complicated types from DB Entity like Date, UUID might be a problem for JSON serialization
	-	we might need slightly different validations for request/response DTOs vs the Entity	
		
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
