Simple-Tank-Game
===============================

Ex8 (Simple-Tank-Game), co-written with Dylan Otto Krider was an open-ended assignment from our Games Programming class using Java and OpenGL. 

Navigation
-----------
[Simple-Tank-Game](#tsimple-tank-game) |
[Abstract](#abstract) |
[Screenshot](#screenshot) |
[Team](#team) |
[Requirements](#requirements) |
[Configuration](#configuration) |
[Running the program](#running the program) |
[License](#license) |
[TODO](#todo)

Purpose
-------
This project aims to create an animated 3D rendering of a 4D cube known as a tesseract. We are using Java's LWJGL library and OpenGL to make the animation, and Gradle/Groovy as a build tools to make the JAR, automate testing, and to link required resources and natives. So, we're kind of doing 4 things here. 

Screenshot
----------
![Picture](http://rabbitfighter.net/wp-content/uploads/2015/04/Simple-Tank-Game.png)

Team
----------------
<ul>
<li>Joshua Michael Waggoner (<a href="https://twitter.com/redragonx">@rabbitfighter81</a>)</li>
<li>Dylan Otto Krider (<a href="https://twitter.com/rabbitfighter81">@memekiller</a>)</li>
</ul>

Requirements
------------
<ul>
<li> Java vers 1.7+ </l1>
<li> Gradle vers 2.0+ (for installation help go to <a href="https://gradle.org/">Gradle's home page</a></li>
</ul>


Configuration
-------------
This program requires LWJGL vers 2.9.3 (Light Weight Java Gaming Library) library as well as natives for Windows, Linux, and OSX that come with it. As per our Gradle build program, these files must be in the correct folders in the project structure or the program will fail. Fortunately, all you need to do is clone this repository to get the project in the proper form. 

Folder Structure
----------------
<pre>
.
├── build.gradle
├── libs
│   ├── jar
│   │   └── lwjgl.jar
│   └── natives
│       ├── linux
│       ├── macosx
│       └── windows
├── LICENSE
├── README.md
├── src
│   └── main
│       └── java
|..         └── net
|..             └── rabbitfighter
|..                 └── game
|..                     └── Basic.java
|..                     └── Box.java
|..                     └── Ex8.java
|..                     └── Tripple.java
.
</pre>

This should be your folder structure before running any commands.

Running the Program
-------------------
<ol>
<li>From the project directory, run <code>gradle build</code>.</li>
<li>Run the program by typing <code>gradle runJar</code></li>
</ol>

Enoy!

Licence
---------
Creative Commons

TODO
----
This game is a work in progress. 
