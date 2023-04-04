 <h1 align="center">
    <img src="https://www.iths.se/wp-content/uploads/2016/02/thumbnails/ithslogoliggandepayoffrgb-4601-1280x450.png" height="150" alt="ITHS">
</h1>

<section>
<div align="center">
    <a href="https://github.com/ithsjavagroupd/project/actions/workflows/maven.yml">
        <img src="https://github.com/ithsjavagroupd/project/actions/workflows/maven.yml/badge.svg" alt="Java CI with Maven workflow badge"/>
    </a>

</div>
<div align="center">
    <a href="https://github.com/ithsjavagroupd/project/issues">
        <img src="https://img.shields.io/github/issues-raw/ithsjavagroupd/project" alt="Open issues workflow badge"/>
    </a>
    <a href="https://github.com/ithsjavagroupd/project/pulls">
        <img src="https://img.shields.io/github/issues-pr/ithsjavagroupd/project" alt="Pull request workflow badge"/>
    </a>
    <a href="https://github.com/ithsjavagroupd/project/issues?q=is%3Aissue+is%3Aclosed">
        <img src="https://img.shields.io/github/issues-closed-raw/ithsjavagroupd/project" alt="Open issues workflow badge"/>
    </a>
</div>
<div align="center">
    <a href="https://github.com/ithsjavagroupd/project/pulse">
        <img src="https://img.shields.io/github/commit-activity/m/ithsjavagroupd/project" alt="Commit activity workflow badge"/>
    </a>
    <a href="https://github.com/ithsjavagroupd/project/graphs/contributors">
        <img src="https://img.shields.io/github/contributors/ithsjavagroupd/project" alt="Contributors workflow badge"/>
    </a>
    <img src="https://img.shields.io/github/languages/top/ithsjavagroupd/project" alt="Language workflow badge"/>
</div>
</section>

<h1 style="text-align: center"> 
  Group - D Project (JU2022)
</h1>

<div align="center">
    <img src="https://cdn.dribbble.com/users/2069402/screenshots/5574718/gif-4mb.gif" height="250" alt="Team of coders working together">
</div>

## Description

Welcome to our group project! This is a web application built using Spring Boot that provides both a web interface and a REST API. The application manages data related to a fictional chain of stores, including members. The purpose of this demo application is that the clients can effortlessly manage their data related to the stores and their respective members, helping them stay organized. We hope you enjoy our project, and we look forward to your contributions!

## Technologies used

1. Spring Boot 3+
2. Thymeleaf for HTML templating
3. Spring security for authentication and authorization
4. MySQL for data storage
5. RabbitMQ for message queuing

## Setup

To run this application locally, you'll need to have Java and MySQL installed on your machine. Here are the steps to get started:
1. Clone this repository to your local machine.
2. Create a MySQL database and update the application.properties file with your database credentials.
3. Run application using './mvnw spring-boot:run' or by importing the project into your preferred IDE and running it from there.

## Usage

The application also provides a web interface for managing data related to stores and their members. To access the web interface, navigate to ''http://localhost:8080' in your preferred web browser.
The application also provides a Rest API for managing data programmatically. Here are some example endpoints that you can test with Insomnia or Postman:
- 'GET /api/chains': retrieves a list of chains.
- 'POST /api/stores': creates a new store
- 'PUT /api/members': creates a new store
To use REST API, you'll need to authenticate with a valid username and password. You can create a new user by running the 'register' endpoint with a POST request.

## Learning Objectives

1. Gain proficiency in using SpringBoot to build a web application. 
2. Understand the concept of entities and their relationships. 
3. Learn how to implement authentication and authorization in a web application.
4. Learn how to create and display HTML pages using Thymeleaf.
5. Develop an understanding of RESTful APIs and how to manage information from other applications or SPAs.
6. Learn how to use message queues for asynchronous processes such as sending emails, processing uploaded information/images and indexing information.
7. Develop skills in collaborating with a team through issues, pull requests and code reviews.
8. Gain experience in continuous integration (CI) by automating the execution of compilation and tests.
