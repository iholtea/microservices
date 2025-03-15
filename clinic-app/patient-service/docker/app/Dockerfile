
FROM openjdk:17

WORKDIR /app

# docker does not allow to access files outside of the build context
# which in this case is the docker/app folder Solutions: 
#   1. Move the Dockerfile to the root folder of patient-service project
#   2. Leave the Dockerfile here and run the build image from the root folder:
#       docker build -t patient-service:latest -f docker/app/Dockerfile .    
#   3. Move the patient.jar after maven build in this folder
COPY ./patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]

