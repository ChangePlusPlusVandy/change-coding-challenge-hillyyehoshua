import java.io.*;
import java.util.Scanner;

/**
 * Game that randomly generates a tweet by Elon Musk <3 or Kanye West and asks the user to guess
 * who wrote the tweet. Lets the user know if they are correct or not and generates the statistics
 *
 */
public class tryingTwo {

    public static void main(String[] args) throws FileNotFoundException {
        //file path for my computer --> elon's tweets
        String elonMuskFilePath = "C:\\Users\\zyehoshua\\IdeaProjects\\trying\\src\\Elon_Musk2";
        //scanner for user input
        Scanner scnr = new Scanner(System.in);
        //enter your path for the tweet file
        System.out.println("Enter your file path for Elon Musk's tweets. If you are using Hilly's" +
                " computer's files, type 'Enter'");
        String userFilePath = scnr.nextLine();
        //use my computer's file
        if (userFilePath.equalsIgnoreCase("Enter")) {
            userFilePath = elonMuskFilePath;
        }

        //find the file using the file path
        File Elon_Musk = new File(userFilePath);
        //point a scanner to the file
        Scanner scanElon = new Scanner(Elon_Musk);
        //grab only the information we want from the file and input into an array
        String[] arrayElon = cleanUpElon(scanElon);

       //file path for Kanye
        String kanyeWestFilePath = "C:\\Users\\zyehoshua\\IdeaProjects\\trying\\src\\Kanye_West2";
        System.out.println("Enter your file path for Kanye West's tweets. If you are using " +
                "Hilly's computer's files, type 'Enter'");
        String userFilePath2 = scnr.nextLine();
        //lets user choose a file path
        if (userFilePath2.equalsIgnoreCase("enter")) {
            userFilePath2 = kanyeWestFilePath;
        }

        //get kanye's file and input into an array with the needed info
        File Kanye_West = new File(userFilePath2);
        Scanner scan2 = new Scanner(Kanye_West);
        String[] arrayKanye = cleanUpKanye(scan2);


        //start the game
        System.out.println("Time to start guessing! Are you ready? Type yes if you are!");
        //user response
        String userAgreement = scnr.nextLine();

        boolean continue_go = true;
        int countTimesPlayed = 0;
        int countCorrect = 0;
        while (continue_go) {
            if (userAgreement.equalsIgnoreCase("yes")) {
                String tweetChosen = randomGenMeth(arrayKanye, arrayElon);
                System.out.println("Let us guess... haha just kidding, only you. I know all of " +
                        "the answers");
                System.out.println("Here is the tweet. Brace yourself:\n");
                System.out.println(tweetChosen);
                System.out.println("");
                ++countTimesPlayed;
                System.out.println("Type MUSK if you think Elon created this (he's made just " +
                        "about everything, I don't blame you if you fail. Type YE if you think " +
                        "Kanye, the king, wrote this");
                String userResponse = scnr.nextLine();
                //tell the user if they guessed right or wrong

                if ((UserGuessed(tweetChosen, userResponse, arrayKanye, arrayElon))== false){
                    System.out.println("eek, better luck next time ");
                }else{
                    System.out.println("No Mistakes... HAHA get it?");
                    ++countCorrect;
                }
                 continue_go = continuePlaying(scnr,countTimesPlayed, countCorrect);
            } else { // if originally user was not ready
                System.out.println("Siri, play \"Yikes\" by Kanye West ");
                System.out.println("Looks like you need some more time before guessing.");
                System.out.println("Okay, are you ready now? You are taking a while...");
                //check if the user wants to keep playing
                boolean checkCont =  continuePlaying(scnr,countTimesPlayed, countCorrect);
                while (checkCont == true){
                    String tweetChosen = randomGenMeth(arrayKanye, arrayElon);
                    System.out.println("Let us guess... haha just kidding, only you. I know all of " +
                            "the answers");
                    System.out.println("Here is the tweet. Brace yourself:");
                    System.out.println(tweetChosen);
                    System.out.println("");
                    ++countTimesPlayed;
                    System.out.println("Type MUSK if you think Elon created this (he's made just " +
                            "about everything, I don't blame you if you fail. Type YE if you think " +
                            "Kanye, the king, wrote this");
                    String userResponse = scnr.nextLine();
                    //tell the user if they guessed right or wrong
                    if ((UserGuessed(tweetChosen, userResponse, arrayKanye, arrayElon))== false){
                        System.out.println("Wrong: eek, better luck next time ");
                    }else{
                        System.out.println("Correct: \"No Mistakes\"... HAHA get it?");
                        ++countCorrect;
                    }
                    continue_go = continuePlaying(scnr,countTimesPlayed, countCorrect);
                    checkCont = continue_go;
                }

            }
        }


    }

    /**
     * Take a file that was taken off of PostMan (twitter API for Elon's 3200 tweets), and find
     * the text part of each tweet
     * @param scanElon
     * @return array of elon's tweets
     */
    public  static String[] cleanUpElon(Scanner scanElon){
        String lineElon = "";
        while (scanElon.hasNextLine()) {
            lineElon = lineElon + scanElon.nextLine();
        }


        String[] arrayElon = lineElon.split("\"text\":");

        for (int j = 0; j < arrayElon.length; ++j) {
            int deleteFrom = arrayElon[j].indexOf("\",\"");
            String subString = arrayElon[j];
            String wantedSubString = subString.substring(1, deleteFrom);
            arrayElon[j] = wantedSubString;
            deleteFrom = 0;
            subString = "";
            wantedSubString = "";
            //should not start w this bc in api excluded replies and retweets, but double checking
            if ((arrayElon[j].startsWith("@") || (arrayElon[j].startsWith("RT"))
                    || (arrayElon[j].startsWith("https:"))  )) {
                arrayElon[j] = null;
            }
        }
        arrayElon[0] = null;
        return  arrayElon;
    }

    /**
     * cleanUpKanye --> go through Kanye's tweet file and extract text into an array
     * @param scan2
     * @return array of Kanye's tweets
     */
    public  static String[] cleanUpKanye (Scanner scan2){

        String lineKanye = "";
        while (scan2.hasNextLine()) {
            lineKanye = lineKanye + scan2.nextLine();
        }

        String[] arrayKanye = lineKanye.split("\"text\":");

        for (int j = 0; j < arrayKanye.length; ++j) {
            int deleteFrom = arrayKanye[j].indexOf("\",\"");
            String subString = arrayKanye[j];
            String wantedSubString = subString.substring(1, deleteFrom);
            arrayKanye[j] = wantedSubString;
            deleteFrom = 0;
            subString = "";

            //should not start w this bc in api excluded replies and retweets, but double checking
            if ((arrayKanye[j].startsWith("@") || (arrayKanye[j].startsWith("RT") ||
                    (arrayKanye[j].startsWith("https:"))))) {
                arrayKanye[j] = null;
            }
        }
        arrayKanye[0] = null;
        return arrayKanye;
    }

    /**
     * randomGenMethod : generates a random tweet
     * @param arrayKanye
     * @param arrayElon
     * @return string with the tweet generated
     */
    public static String randomGenMeth(String[] arrayKanye, String[] arrayElon) {

        int kanyeLength = arrayKanye.length;
        int elonLength = arrayElon.length;


        boolean findValidArrayIndex = true;
        String returnString = "";

        while (findValidArrayIndex) {
            double random = Math.random();
            int indexKanye = (int) (Math.random() * kanyeLength);
            int indexElon = (int) (Math.random() * elonLength);
            if ((random >= 0) && (random <= 0.50)) {
                //choose array Kanye
                if ((arrayKanye[indexKanye]) != null) {
                    returnString = arrayKanye[indexKanye];
                    findValidArrayIndex = false;
                } else {
                    findValidArrayIndex = true;
                }
            } else {
                //choose array Elon
                if ((arrayElon[indexElon]) != null) {
                    returnString = arrayElon[indexElon];
                    findValidArrayIndex = false;
                } else {
                    findValidArrayIndex = true;
                }
            }
        }
        return returnString;
    }

    /**
     * UserGuessed boolean if the user guessed correctly
     * @param tweet
     * @param response
     * @param arrayKanye
     * @param arrayElon
     * @return Boolean --> T if guessed correctly, false if not
     */
    public static boolean UserGuessed(String tweet, String response, String[] arrayKanye,
                                      String[] arrayElon) {
        boolean found = false;
        boolean exit = true;
        if (response.equalsIgnoreCase("YE")) {
            for (int i = 0; i < arrayKanye.length && exit; ++i) {
                if ((arrayKanye[i]) == tweet) {
                    found = true;
                    exit = false;
                }
            }
        } else if (response.equalsIgnoreCase("MUSK")) {
            for (int i = 0; i < arrayElon.length && exit; ++i) {
                if ((arrayElon[i]) == tweet) {
                    found = true;
                    exit = false;
                }
            }
        } else {
            found = false;
        }
        return found;
    }

    /**
     * statisticsCalc get the game statistics: percent correct and the number correct
     * @param countCorrect
     * @param count
     * @return String with the statistics
     */
    public static String statisticCalc(int countCorrect, int count) {
        double countGenius = 100 * (double)(countCorrect) / count;
        int guessedRight = countCorrect;
        String returnStatistics;

        returnStatistics =
                "\n--------------------------------------------------\n---------------------GAME " +
                        "OVER-" +
                        "-------------------\nYou guessed right " + guessedRight +
                " times. \nOverall, I would say that you are a " + countGenius + "% genius" +
                        ".\n-------------------" +
                        "--GAME OVER--------------------\n" +
                        "--------------------------------------------------\n";
        if (countGenius > 70) {
            returnStatistics = returnStatistics + "Elon would be proud.";
        } else if (countGenius == 100){
            returnStatistics = returnStatistics + "Are you sure that you did not write these " +
                    "tweets?";
        }else {
            returnStatistics = returnStatistics + "Step up your game, Kanye says you kind" +
                    " of suck.";
        }
        return returnStatistics;
    }


    /**
     * check if the user wants to continue playing
     * @param scnr
     * @param totalCount
     * @param countCorrect
     * @return boolean t or f if the user wants to continue the game
     */
    public  static  boolean continuePlaying (Scanner scnr, int totalCount, int countCorrect){
        boolean continue_go;
        String userAgreement;
        System.out.println("\nIf you are \"Heartless\" and dont want to play anymore, type " +
                "STOP");
        System.out.println("If you want to continue, click \"GO\"");
        userAgreement = scnr.nextLine();
        if (userAgreement.equalsIgnoreCase("STOP")) {
            System.out.println("Siri, play FML by Kanye West. Elon says that you are " +
                    "equivalent to  Toyota, and Ye agrees. He thought you were " +
                    "Stronger:(");
            continue_go = false;
            System.out.println(statisticCalc(countCorrect,totalCount));
        }else if (userAgreement.equalsIgnoreCase("GO")){
            System.out.println("\nOh you shine like the top of a Tesla on a hot summer day. Keep " +
                    "on" +
                    " playing.");
            continue_go = true;
        }else{
           System.out.println("Looks like you entered the wrong key, game is not over until you " +
                   "tell it to be. ");
           continue_go = true;
        }
        return continue_go;
    }



}