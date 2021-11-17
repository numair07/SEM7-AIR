import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ChatBot {
    private HashMap<String, Organisation> Stocks;
    private HashMap<String, Organisation> NFT;
    private HashMap<String, Organisation> cryptoCurrencies;
    private HashMap<String, String> basicQuestions;
    private Set<String> events;
    Scanner scanner;

    public ChatBot() {
        Stocks = new HashMap<>();
        NFT = new HashMap<>();
        cryptoCurrencies = new HashMap<>();
        basicQuestions = new HashMap<>();
        events = new HashSet<>();
        scanner = new Scanner(System.in);
    }

    public void createRules() {
        // add financial events
        events.add("acquisitions");
        events.add("roi");
        events.add("q4");
        events.add("revenue");
        events.add("profits");
        events.add("risk");
        events.add("buyrate");

        //basic questions and their answers
        basicQuestions.put("hello", "Hello There!");
        basicQuestions.put("your name", "My Name is Gideon, and I am an intelligent Chat Bot. I will assist you in making financial decisions and investments.");
        basicQuestions.put("who are you", "My Name is Gideon, and I am an intelligent Chat Bot. I will assist you in making financial decisions and investments.");
        basicQuestions.put("can you", "I can do a lot of things for you. For example. show you all the current trends in the stock market.");

        Organisation organisation;

        //Add Stocks
        organisation = new Organisation("TCS");
        organisation.addEvent("acquisitions", "TCS has recently acquired XYZ Technologies for 3.1 Billion US$.");
        organisation.addEvent("roi", "TCS shares have given a 5.2% Return on Investment (ROI) in the past year.");
        organisation.addEvent("revenue", "TCS revenue stands at 28 Billion US$");
        organisation.addEvent("profits", "TCS profits stands at 4.3 Billion US$");
        organisation.addEvent("q4", "TCS Q4 data has been very encouraging. \n They have reported over 21% rise in profits.");
        Stocks.put("tcs", organisation);

        organisation = new Organisation("Wipro");
        organisation.addEvent("roi", "Wipro shares have given a 8.6% Return on Investment (ROI) in the past year.");
        organisation.addEvent("revenue", "Wipro revenue stands at 43 Billion US$");
        organisation.addEvent("profits", "Wipro profits stands at 11 Billion US$");
        organisation.addEvent("q4", "Wipro Q4 data has been extremely encouraging. \n They have reported over 262% rise in profits.");
        Stocks.put("wipro", organisation);


        //add NFTs
        organisation = new Organisation("Pikachu");
        organisation.addEvent("roi", "Pikachu NFTs have given over 540% Return on Investment in the past year.");
        organisation.addEvent("risk", "Pikachu NFTs are high risk NFTs. Their prices have been highly volatile.");
        NFT.put("pikachu", organisation);

        organisation = new Organisation("Charizard");
        organisation.addEvent("roi", "Charizard NFTs have given over 12% Return on Investment in the past year.");
        organisation.addEvent("risk", "Charizard NFTs are low risk NFTs. Their prices have remained highly stable.");
        NFT.put("charizard", organisation);

        //Add crypto
        organisation = new Organisation("Bitcoin");
        organisation.addEvent("roi", "Bitcoin has given over 54% Return on Investment in the past year.");
        organisation.addEvent("risk", "BTC is a medium to low risk cryptocurrency");
        organisation.addEvent("buyrate", "The buy rate for BTC is extremely high. It is highly popular.");
        cryptoCurrencies.put("bitcoin", organisation);

        organisation = new Organisation("Ethereum");
        organisation.addEvent("roi", "ethereum has given over 3000% Return on Investment in the past year.");
        organisation.addEvent("risk", "ethereum is a extremely high risk cryptocurrency.");
        cryptoCurrencies.put("ethereum", organisation);
    }

    public void run() {
        System.out.println("Welcome. I am Gideon, an Investment advisor. You can ask me anything on NFTs, Stocks & Crypto Currencies.");
        String userInput = "";
        String[] splitString;
        System.out.println("Please type in your queries.  Enter quit to exit.");
        Organisation currentEntity = null;
        boolean basicQuestionsFlag = false;
        while (true) {
            basicQuestionsFlag = false;
            userInput = scanner.nextLine();
            userInput = userInput.toLowerCase();
            for(String basic : basicQuestions.keySet()) {
                if(userInput.contains(basic)) {
                    System.out.println(basicQuestions.get(basic));
                    basicQuestionsFlag = true;
                }
            }
            if(basicQuestionsFlag == true) {
                currentEntity = null;
                continue;
            }
            splitString = userInput.split(" ");
            for(String s : splitString) {
                if(s.contains("nft")) {
                    System.out.println("Which NFT would you like to gain information about? \n 1. Pikachu \n 2. Charizard");
                    currentEntity = null;
                    break;
                }
                if(s.contains("crypto")) {
                    System.out.println("Which Crypto Currency would you like to gain information about? \n 1. Bitcoin \n 2. Ethereum");
                    currentEntity = null;
                    break;
                }
                if(s.contains("stock") || s.contains("share")) {
                    System.out.println("Which stock would you like to gain information about? \n 1. TCS \n 2. Wipro");
                    currentEntity = null;
                    break;
                }
                if(Stocks.containsKey(s)) {
                    currentEntity = Stocks.get(s);
                    System.out.println("What would you like to know about " + s + " ?");
                    break;
                }
                if(cryptoCurrencies.containsKey(s)) {
                    currentEntity = cryptoCurrencies.get(s);
                    System.out.println("What would you like to know about " + s + " ?");
                    break;
                }
                if(NFT.containsKey(s)) {
                    currentEntity = NFT.get(s);
                    System.out.println("What would you like to know about " + s + " ?");
                    break;
                }
                if(events.contains(s) && currentEntity!=null) {
                    if(currentEntity.containsDescription(s)) {
                        System.out.println(currentEntity.returnDescription(s));
                        break;
                    }
                    else {
                        System.out.println("Sorry, I do not have any information on that.");
                        break;
                    }
                }
                System.out.println("Sorry, I didn't get that. Please try again.");
            }
            if(splitString[0].equals("quit")) {
                System.out.println("Thank you. Goodbye.");
                break;
            }
        }
    }

}