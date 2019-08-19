# Advanced-Algorithms

## About

Advanced-Algorithms contains the programming prompts prompts and my custom solutions to Coursera's "Advanced Algorithms and Complexity" course. I was selected by Google to be a student of their Computer Science Summer Institute-Coursera program, and completed this sponsored online course as part of Google's Data Structures and Algorithms curriculum.

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [Week One - Flows in Networks](#week-one---flows-in-networks)
  * [Evacuation](#evacuation)
  * [Airline Crews](#airline-crews)
* [Week Two - Linear Programming](#week-two---linear-programming)
  * [Energy Values](#energy-values)
  * [Diet](#diet)
* [Week Three - NP-Complete Problems](#week-three---np-complete-problems)
  * [GSM Network](#gsm-network)
  * [Cleaning Apartment](#cleaning-apartment)
* [Week Four - Coping with NP-Completeness](#week-four---coping-with-np-completeness)
  * [Circuit Design](#circuit-design)
  * [Plan Party](#plan-party)
* [Contact](#contact)


<!-- Week One -->
## Week One - Flows in Networks
In this week, I learned about network flows, how to calculate MaxFlow and MinCut using algorithms such as Ford-Fulkerson and Edmonds-Karp, and how these algorithms could be applied to real-life scenarios.

### Evacuation
Evacuation is a program that employs the Edmonds-Karp algorithm to find the MaxFlow given the edges of a network. In this program, I not only learned how to code the Edmonds-Karp algorithm, but in the process learned how to implement a breadth-first-search.

### Airline Crews
AirlineCrews is a program that employs a Maximum Bipartite Matching algorithm to find the maximum number of airline crews that can be assigned to unique aircrafts when given a boolean array. In this program, I learned to convert a bipartite matching problem into a MaxFlow problem and then apply the Edmonds-Karp algorithm.

<!-- Week Two -->
## Week Two - Linear Programming
In this week, I learned about how linear programming can be applied to solve Gaussian Elimination via the Simplex and Ellipsoid algorithms. I also was exposed to ideas of duality and complementary slackness and how these ideas could help simplify such a problem.

### Energy Values
EnergyValues is a program that employs Gaussian Elimination to solve a system of equations in an N x (N+1) matrix.

### Diet
Diet is a program that employs the Simplex Algorithm to solve a set of linear equations with constraints and return whether the system has no solution, infinite solutions, or a bounded solution (and the values).  

<!-- Week Three -->
## Week Three - NP-Complete Problems
In this week, I learned about the various types of search problems and the distinction between P and NP. I learned that while NP problems do not have an efficient polynomial-time solution, there are special cases where you can apply a reduction and simplify the problem into a problem which can be solved with a polynomial-time algorithm.

### GSM Network
GSMNetwork is a program that employs a 3SAT reduction to solve an independent set problem when given a set of vertices and edges.

### Cleaning Apartment
CleaningApartment is a program that employs a 3SAT reduction to solve a variant of the classic Hamiltonian path problem.

<!-- Week Four -->
## Week Four - Coping with NP-Completeness
In this week, I learned about the modern methods to solve NP-Complete problems: Special cases, Exact algorithms, and Approximation algorithms.

### Circuit Design
CircuitDesign is a program that employs a 2SAT reduction (backtracking) to determine whether the given Conjunctive Normal Form (CNF) is satisfiable, and if so, what the solution is. To solve this prompt, I learned about Strongly-Connected-Components (SCCs) and the different algorithms which could be used to solve this type of problem.

### Plan Party
PlanParty is a program that employs the Branch-and-Bound algorithm to find the Maximum Weighted Independent Set in a Tree.

<!-- CONTACT -->
## Contact

Jason Math - [https://www.linkedin.com/in/jason-math](https://www.linkedin.com/in/jason-math) - jasonmath@utexas.edu

Project Link: [https://github.com/jason-math/Advanced-Algorithms](https://github.com/jason-math/Advanced-Algorithms)
