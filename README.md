# password-cracker-with-genetic-algorithm
A genetic algorithm for password cracking written in Java based on this website: https://www.analytics-link.com/post/2019/02/14/password-cracking-with-a-genetic-algorithm


## Built With
- Java
## Description
This is a basic algorithm for understanding genetic algorithms. 
Even though it is written in Java, there is no class structure in this project since it is for experimental purposes.
The algorithm implemented in only one class: ```` PasswordCracker```` .

It takes five initial parameters. The purpose is to reach to the given password in the minimum possible generation numbers , given the range.
Inıtial Parameters are:

-  ```` String password ```` : the password needs to be cracked.
-  ```` int popSize ```` : population(chromosome/word) size to generate in the first generation.
-  ```` int bestCandidates ```` : the chromosome(word) number to be chosen for next generation(according to the fitness score)
-  ```` int luckyCandidates ```` : the chromosome(word) number to be chosen for next generation(randomly chosen)
-  ```` double mutationPercent ```` : it is a parameter for mutate function
-  ```` int maxGen ```` : maximum generation number we can try


## Project Requests
- Write a method that compares the characters one by one and returns how many characters the given chromosome differs from the password as a fitness score.
- Solve the problem 3 times for the same password. 
- Change the popoulation size and repeat these steps. 

Outputs:

In each generation:
- Generation numbers,
- The result closest to the solution 

For total 3 solutions :
- Generation average, 
- Processing times of each solution by calculating in milliseconds. 
