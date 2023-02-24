# Escape - Prison Adventures

A 2D mini-roguelike about a wrongly-accused prisoner collecting documents to prove their innocence and escape from prison.

## Getting Started

### Prerequisites

This project requires Maven and JDK 13 or newer.

### Running from Prebuilt Binary

There is a prebuilt binary included in the repository at `bin/prison-escape.jar`.

Run the game using the prebuilt binary:
```
$ java -jar bin/prison-escape.jar
```

### Installing/Compiling

Clone the source code and assets with git:
```
$ git clone git@csil-git1.cs.surrey.sfu.ca:276-group-8/project.git
$ cd project
```

Compiling the game:
```
$ mvn compile package
```

If this fails ensure that you have JDK 13 or higher installed.

This will output a jar file which can be used to run the game with the following command:
```
$ java -jar target/prison-escape-1.0-SNAPSHOT.jar
```

Alternatively, you can run and compile the game in one command:
```
$ mvn compile exec:java
```

### Running Tests

To run tests:

```
$ mvn compile test
```

A code coverage report is automatically generated at `target/site/jacoco/index.html` after running tests.

## Documentation

Documentation is in the form of comments throughout the code and JavaDocs.

### Generating JavaDocs

JavaDocs can be generated with Maven:

```
$ mvn javadoc:javadoc
```

To view them, open the file `target/site/apidocs/index.html` with the web browser of your choice.

## Playing the Game

Instructions on how to play the game are available in the "How To Play" section of the main menu.

## Acknowledgments

* [Grass sprite](http://pixelartmaker.com/art/98f98269b16f5d9)
* [Prison sprites](https://adarshs.itch.io/prison-game-asset-pack)
* [Documents sprite](https://www.shutterstock.com/image-vector/business-documents-office-pixel-art-icons-1036088941)
* [Money sprite](https://dribbble.com/shots/1667568-Dollar-Gif-Rock)
* [README template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [Prisoner model for background](https://www.freepik.com/free-photos-vectors/business)
* [Background music](https://www.bensound.com/)
