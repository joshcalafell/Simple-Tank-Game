Simple-Tank-Game
===============================
Simple-Tank-Game (a.k.a. Ex8) was an open-ended assignment from our Games Programming class using Java and OpenGL. 

Navigation
-----------
[Simple-Tank-Game](#tsimple-tank-game) |
[Abstract](#abstract) |
[Screenshot](#screenshot) |
[Team](#team) |
[Requirements](#requirements) |
[Folder Structure](#folder-structure) |
[Configuration](#configuration) |
[Building the program](#building-the-program) |
[Running the Program](#running-the-program) |
[Contact](#contact) |
[Licenses](#licenses) |
[TODO](#todo)

Abstract
--------
This game was a project for a CS390A - Game Programming @ MSU Denver, taught by Professor Jerry Shultz. We were to follow loose guidelines, and create a game using only Java and OpenGL. This program is built, and deployed using Gradle. It is written in Java using Java OpenGL bindings provided by Light Weight Java Gaming Library (LWJGL). 

Screenshot
----------
![Picture](http://rabbitfighter.net/wp-content/uploads/2015/04/Simple-Tank-Game.png)

Team
----------------
<ul>
<li>Joshua Michael Waggoner - on Twitter: (<a href="https://twitter.com/rabbitfighter81">@rabbitfighter81</a>)</li>
<li>Dylan Otto Krider - on Twitter:  (<a href="https://twitter.com/dokrider">@dokrider</a>)</li>
</ul>

Requirements
------------
<ul>
<li> Java vers 1.7+ </l1>
<li> Gradle vers 2.0+ (for installation help go to <a href="https://gradle.org/">Gradle's home page</a>)</li>
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

The folder structure should look like this before building.

Building the Program
--------------------
<ol>
<li>Navigate into the project directory. From the project directory, run <code>gradle build</code>.</li>
</ol>

Running the Program
-------------------
<ol>
<li>Run the program by typing <code>gradle runJar</code> in the root project directory</li>
<li>Enjoy!</li>
</ol>

Contact
-------
<table>
<tr>
<td> Josh: rabbitfighter@cryptolab.net</td>
<td> Dylan: dkrider@comcast.net</td>
</table>

Licences
---------
<ul>
<li>LWJGL sources and binaries: Copyright (c) 2002-2007 Lightweight Java Game Library Project</li>
<li>All other: CCO Licence (See LICENCE-CCO)</li>
</ul>

TODO
----
This game is a work in progress. We hope you have fun, learn something, or at least kill some time. We will add updates, and clean up code as time allows. For now it is working though. Yay!
