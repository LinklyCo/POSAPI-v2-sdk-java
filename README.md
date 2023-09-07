# linkly-posApi-sdk
This package provides an SDK to simplify the development of POS applications which use the [Linkly Online API](https://linkly.com.au/resources-support/developer-apis). The solution contains the following projects:

## linkly-posApisdk
The SDK class library for interacting with the Linkly API. Compatible with Java 1.8 and above.

### Requirements
* Java 8 or higher

### Libraries
* org.asynchttpclient:async-http-client
* com.squareup.moshi:moshi
* com.squareup.moshi:moshi-adapters
* com.squareup.moshi:json

## linkly-posApi-test
Unit tests the functionality in linkly-posApi-sdk. Compatible with Java 8 or higher


## linkly-posApi-demoPos
Sample Web-based POS application which showcases the functionality in linkly-posApi-sdk and can be used as a reference when writing a POS implementation using the SDK. Compatible with Java 11.

Java is backwards compatible. Demo app uses Java 11 while Java 8 is built using Java 8. This is to show that the SDK will work using higher versions of Java. If you opted to use Java 8 for the Demo App, minor code changes must be applied.

Add JAVA_HOME to environment variables with the installed Java bin path.

### Requirements
* [Java 11](https://www.oracle.com/ph/java/technologies/javase/jdk11-archive-downloads.html)
* [Spring Tool Suite (STS)](https://spring.io/tools)
* [Maven](https://maven.apache.org/download.cgi)

## Installation
1. Press the **Fork** button (top right the page) to save copy of this project on your account.

2. Download the repository files (project) from the download section or clone this project by typing in the bash the following command:

       git clone [https://github.com/LinklyCo/POSAPI-v2-sdk-java.git](https://github.com/LinklyCo/POSAPI-v2-sdk-java.git)
3. Imported it in STS.
4. Find main class and Run as SpringBoot Application.

# Version History
- 1.0.1
  - Initial release.
