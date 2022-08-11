FROM openjdk:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.password=${DB_PASSWORD}", "-Dspring.datasource.url=${DB_URL}", "-Dspring.redis.host=${REDIS_HOST}", "-Dspring.redis.port=${REDIS_PORT}", "-Dspring.redis.password=${REDIS_PASSWORD}", "-Dminio.endpoint=${MINIO_ENDPOINT}", "-Dminio.bucket=${MINIO_BUCKET}", "-Dminio.access_key=${MINIO_ACCESS_KEY}", "-Dminio.secret_key=${MINIO_SECRET_KEY}", "-Djwt.secret=${JWT_SECRET}", "/app.jar"]
