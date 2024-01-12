APP_NAME=`heroku info | grep  "=== .*" |sed "s/=== //"`
mvn clean package\
  -Dquarkus.container-image.build=true\
  -Dquarkus.container-image.group=registry.heroku.com/$APP_NAME\
  -Dquarkus.container-image.name=web\
  -Dquarkus.container-image.tag=latest\
  -Dnative\
  -Dquarkus.native.container-build=true
