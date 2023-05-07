import java.util.*;


public class PasswordCracker {
    static String password = "ChatGPT and ChatGPT-4";
    static int popSize = 100; //chromosome number
    static int bestCandidates = 18;
    static int luckyCandidates = 5;
    static double mutationPercent = 2;
    static int maxGen = 300;

    public static void main(String[] args) {


        List<String> pop = getFirstGen(popSize, password.length());
        double totalGen=0;
        //int[] array= {25,50,150,200,250};
        //for(int j=0;j<5;j++) {
           //popSize = array[j];
            //System.out.println("******** Population size:" + popSize + " ********");

            //for (int i = 0; i < 3; i++) {

                long start = System.currentTimeMillis();
                totalGen += crackPassword(pop);
                long end = System.currentTimeMillis();
                System.out.println("Time taken: " + (end - start) + " ms");

            //}
            //double avgGen = totalGen / 3;
           // System.out.println("Ortalama generation sayısı: " + avgGen);
           // System.out.println();
        }





    public static int crackPassword(List<String> pop){
        int foundGen=0;

        for (int i = 0; i < maxGen; i++) {
            Map<String, Double> popSort = popFitness(pop, password);
            double chromosomeScore=popSort.entrySet().iterator().next().getValue();
            if (i % 5 == 0) {
                System.out.println("Gen:" + i + " Score: " + chromosomeScore +
                       " Closest Chromosome: " + popSort.entrySet().iterator().next().getKey() );

            }
            if (chromosomeScore == 100.0) {
                System.out.println("Password found! ");
                System.out.println("Gen:" + i + " Score: " + chromosomeScore +
                        " Password: " + popSort.entrySet().iterator().next().getKey() );
                foundGen=i;
                break;
            }

            List<String> nextParents = selectPop(new ArrayList<>(popSort.entrySet()), bestCandidates, luckyCandidates);
            List<String> nextPop = createChildren(nextParents);
            if (nextPop.size() < 2) {
                nextPop = pop;
            } else {
                nextPop = mutate(nextPop, (int) Math.round(mutationPercent * popSize));
            }
            pop = nextPop;

        }
        Map.Entry<String, Double> bestCandidate = popFitness(pop, password).entrySet().iterator().next();
        System.out.println("Last found chromosome is: " + bestCandidate.getKey() + ", Maximum Generation Number: " + maxGen + " Number of attempts: " + maxGen * popSize);
        return foundGen;
    }
    public static double fitness(String password, String testWord) {
        //A function for calculating fitness score for the test words.
        int score = 0;
        if (password.length() != testWord.length()) {
            System.out.println("length do not match");
            return 0.0;
        } else {
            for (int i = 0; i < password.length(); i++) { //Score is increased for each matching letter
                if (password.charAt(i) == testWord.charAt(i)) {
                    score++;
                }
            }
            return (double) (score * 100) / password.length();
        }
    }

    public static String newWord(int length) {
        // Use (random.nextInt(27) + 32) generate random ASCII characters that are either letters, digits or blankspace.
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random random=new Random();
            word.append((char) (random.nextInt(27) + 32));
        }
        return word.toString();
    }


    public static List<String> getFirstGen(int popSize, int length) {
        //Function that creates pop ArrayList to store the new words(first generation)
        List<String> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(newWord(length));
        }
        return pop;
    }


    public static Map<String, Double> popFitness(List<String> pop, String password) {
        //Function returns a map of the strings in the pop array, sorted in descending order of their fitness scores
        // when compared to the given password.
        Map<String, Double> popFit = new HashMap<>();
        for (String i : pop) {
            Double fitnessScore = fitness(password, i); //calculate score for each string
            popFit.put(i, fitnessScore);
        }
        List<Map.Entry<String, Double>> list = new LinkedList<>(popFit.entrySet()); //sort the chromosomes in descending order
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        Map<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }

    public static List<String> selectPop(List<Map.Entry<String, Double>> popSort, int best, double lucky) {

        /**This function selects the next generation of chromosomes to be used in a genetic algorithm based on the fitness scores
         * of the current population.
         @param popSort a sorted list of chromosomes in the current population along with their corresponding fitness scores.
         @param lucky  specifies the number of additional chromosomes to select randomly from the population.(lucky candidates)
         * The selected chromosomes are then shuffled randomly
         @return nextGen
         */

        List<String> nextGen = new ArrayList<>();
        for (int i = 0; i < best; i++) {
            if (i >= popSort.size()) {
                nextGen.add(popSort.get(new Random().nextInt(popSort.size())).getKey());
            } else {
                nextGen.add(popSort.get(i).getKey());
            }
        }
        for (int i = 0; i < lucky; i++) {
            nextGen.add(popSort.get(new Random().nextInt(popSort.size())).getKey());
        }
        Collections.shuffle(nextGen);
        return nextGen;
    }

    public static String createChild(String parent1, String parent2) {
        /**
         * This function takes in two parent strings and creates a new child string by randomly selecting characters from each parent.
         * It iterates through the characters of parent1 using a for loop.For each character in parent1,
         * it generates a random number between 0 and 1.If the random number is greater than 0.5,
         * it selects the corresponding character from parent1 and adds it to the child string.
         * Otherwise, it selects the corresponding character from parent2 and adds it to the child string.

         @param parent1, parent2
         @return child
         */

        String child = "";
        Random rand = new Random();

        for (int i = 0; i < parent1.length(); i++) {
            if (rand.nextDouble() > 0.5) {
                child += parent1.charAt(i);
            } else {
                child += parent2.charAt(i);
            }
        }
        return child;
    }

    public static List<String> createChildren(List<String> parents) {
        //This function creates a list to store the children population in the next generation.
        List<String> nextPop = new ArrayList<String>();

        for (int i = 0; i < parents.size() / 2; i++) {
            for (int j = 0; j < 4; j++) {
                nextPop.add(createChild(parents.get(i), parents.get(parents.size() - 1 - i)));
            }
        }
        return nextPop;
    }


    public static List<String> mutate(List<String> pop, int chance) {
        /**
         * Function performs mutation on the population of strings passed to it, with a given chance of mutation.
         *  for each string in the population, the function randomly decides whether to mutate it based on the given chance.
         @param pop,chance
        */
        Random rand = new Random();
        // If the string is selected for mutation, the function randomly selects a position
        // in the string and replaces the character at
        // that position with a randomly generated letter.
        for (int i = 0; i < pop.size(); i++) {
            if (rand.nextInt(chance*2) < chance) {
                int k = rand.nextInt(pop.get(0).length());
                char c='a';
                if(rand.nextInt(2)==1) {
                    c = (char) (rand.nextInt(26) + 'a');
                }else {
                    c = (char) (rand.nextInt(26) + 'A');
                }
                String word = pop.get(i).substring(0, k) + c + pop.get(i).substring(k + 1);
                pop.set(i, word);
            }
        }
        return pop;
    }


}