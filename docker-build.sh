#!/bin/bash
MODULE_NAME=""

build_app() {
  echo "Creating ${MODULE_NAME} .jar artifact..."
  sleep 3s
  mvn clean package -Dmaven.test.skip=true
  sleep 1s
}

ensure_jar_access() {
  echo "Ensuring access to jar file..."
  sleep 1s
  chmod +x target/${MODULE_NAME}-0.0.1-SNAPSHOT.jar
  sleep 1s
}

cd springboot-customer-api
MODULE_NAME="springboot-customer-api"
build_app
ensure_jar_access
cd ../springboot-product-api
MODULE_NAME="springboot-product-api"
build_app
ensure_jar_access
cd ../springboot-credit-api
MODULE_NAME="springboot-credit-api"
build_app
ensure_jar_access
cd ..
echo "Running application..."
sleep 3s
docker-compose up