# Energy Label Service
[![Build Status](https://travis-ci.org/UKGovernmentBEIS/energy-label-service.svg?branch=develop)](https://travis-ci.org/UKGovernmentBEIS/energy-label-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/44d631203c5a49d79e2fe3ed828d3c6c)](https://www.codacy.com/app/jamesbarnett91/energy-label-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=UKGovernmentBEIS/energy-label-service&amp;utm_campaign=Badge_Grade)

Digital service allowing users to create energy labels for a variety of products, such as light bulbs, heaters, televisions and ovens.

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
    
## Deployment

Any code committed to the `develop`, `qa`, or `master` branch (via PR, merge or direct commit) will automatically trigger a Travis CI build and deploy to the `dev`, `qa` and `prod` environments respectively.

Changes should first be made on `develop`, merged to `qa` and then finally `master` once completed and QA'd. Merges should be done using the `--ff-only` option in order to avoid merge commits which will de-sync the branches.

E.g. to push the contents of develop to the QA environment run:
```
git checkout qa
git merge develop --ff-only
git push
```

Note that only the production environment has blue/green deployments enabled. Pushes to `develop` or `qa` will briefly take down the respective environment as the application is redeployed.

## Modifying existing products
The vast majority of product 'config' (e.g. field prompts, hint text, validation rules etc) resides in the form object for the given product. 
E.g. to change the on screen text for the Dishwashers, edit the [`DishwashersForm`](https://github.com/UKGovernmentBEIS/energy-label-service/blob/develop/src/main/java/uk/gov/beis/els/categories/dishwashers/model/DishwashersForm.java).

To change a visual aspect of the generated label, modify the SVG template used to render the PDF. E.g. [`dishwashers.svg`](https://github.com/UKGovernmentBEIS/energy-label-service/blob/develop/src/main/resources/labels/dishwashers/dishwashers.svg)


## Adding new products
Adding a new product requires:
* An SVG template used to render the PDF
* A form object which defines all the fields the user must fill in
* A service to populate the SVG template given a form
* Controller routes

If the product is an entirely new category of products, add an entry to the [`ProductCategory`](https://github.com/UKGovernmentBEIS/energy-label-service/blob/develop/src/main/java/uk/gov/beis/els/model/ProductCategory.java) list.

If the new product category also contains sub-categories, add a new implementation of the [`Category`](https://github.com/UKGovernmentBEIS/energy-label-service/blob/develop/src/main/java/uk/gov/beis/els/categories/common/Category.java) interface, and `extend` the [`CategoryController`](https://github.com/UKGovernmentBEIS/energy-label-service/blob/develop/src/main/java/uk/gov/beis/els/controller/CategoryController.java) in your products controller.