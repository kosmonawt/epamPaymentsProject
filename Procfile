web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
heroku ps:scale worker=1
