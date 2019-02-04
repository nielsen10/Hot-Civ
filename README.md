# HotCiv
Course: [Software Engineering and Architecture](https://kursuskatalog.au.dk/en/course/82734/Softwarekonstruktion-og-softwarearkitektur).

A Java HotCiv project following the assignments from the book, [Flexible, Reliable, Software](http://www.baerbak.com/).
The project contains 8 different versions, AlphaCiv, BetaCiv, GammaCiv, DeltaCiv, EpsilonCiv, ZetaCiv, SemiCiv and ThetaCiv. 
For this we decided to use Factory Pattern, and Strategy Pattern. The Factory Pattern is used to Instantiate the Strategies, which contain the methods that differ between the different versions. The GameImpl is created with a Factory (eg. AlphaCivFactory), and it then creates the Strategies from the Factory. These Strategies then uses Dependency Injection, to make the different algorithms (eg. yearUpdate), so the GameImpl works for every version.

# What I Learned
* Test Driven Development
* Object-oriented Programming
* Dependency Injection
