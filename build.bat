@echo off
for /f "tokens=2 delims= " %%a in ('heroku info ^| find "==="') do set APP_NAME=%%a

./mvnw clean package
  -Dquarkus.container-image.build=true
  -Dquarkus.container-image.group=registry.heroku.com/%APP_NAME%
  -Dquarkus.container-image.name=web
  -Dquarkus.container-image.tag=latest
  -Dnative
  -Dquarkus.native.container-build=true

heroku container:push web --app %APP_NAME%
heroku container:release web --app %APP_NAME%

heroku logs --app %APP_NAME% --tail

heroku open