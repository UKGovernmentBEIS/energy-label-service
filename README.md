# Energy Label Service
[![Build Status](https://travis-ci.org/UKGovernmentBEIS/energy-label-service.svg?branch=develop)](https://travis-ci.org/UKGovernmentBEIS/energy-label-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/44d631203c5a49d79e2fe3ed828d3c6c)](https://www.codacy.com/app/jamesbarnett91/energy-label-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=UKGovernmentBEIS/energy-label-service&amp;utm_campaign=Badge_Grade)

Digital service allowing users to generate energy labels for a variety of products.

## Running locally

The service is built using the [Spring Boot](https://spring.io/projects/spring-boot) framework.

First, ensure you have the following prerequisites installed: 
* Java 8 (or higher)
* NPM

Then:
1.  Clone the project
    ```
      git clone https://github.com/UKGovernmentBEIS/energy-label-service.git
      cd energy-label-service
    ```
  
2.  Build the frontend 
    ```
      npm install && npx gulp buildAll
    ```
  
3.  Run with Gradle
    ```
      ./gradlew bootRun
    ```