import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private String [] combinatinos = new String[]{};
    private int userMove;
    Strings strings = new Strings();
    Security security = new Security();

    public void setParameters(String [] array){
        for(int i = 0; i < array.length; i++)
            for(int j = i + 1; j < array.length; j++)
                if(array[i].equals(array[j])) {
                    strings.soutError();
                    System.exit(0);
                }
        if (array.length % 2 != 0 & array.length >= 3)
            combinatinos = array;
        else {
             strings.soutError();
            System.exit(0);
        }
    }

    public void start() {
        try {
            security.createKey(new byte[16]);
            byte [] byteKey = security.key.getBytes(StandardCharsets.UTF_8);

            String moveComputer = combinatinos[new Random().nextInt(combinatinos.length)];

            security.HMAC(moveComputer, byteKey);
            printMenu();
            validation();

            if(userMove == 0)
                System.exit(0);

            System.out.println("Enter your move: " + userMove);
            System.out.println("Your move: " + combinatinos[userMove - 1]);
            System.out.println("Computer move: " + moveComputer);

            checkingMoves(moveComputer);
        } catch (Exception e) {
        }
    }

    private void checkingMoves(String moveComputer){
        int step = (combinatinos.length - 1) / 2;

        if(combinatinos[userMove - 1].equals(moveComputer)){
            System.out.println("Draw.");
            strings.soutLine();
            start();
        }

        boolean win = true;
        if (userMove > step) {
            for (int i = userMove - 2; i >= userMove - step - 1; i--)
                if (combinatinos[i].equals(moveComputer)) {
                    strings.soutWin();
                    win = false;
                    break;
                }
            if(win)
                strings.soutLose();
        }else {
            for (int i = userMove + 1; i <= userMove + step; i++)
                if (combinatinos[i].equals(moveComputer)) {
                   strings.soutLose();
                    win = false;
                    break;
                }
            if (win)
                strings.soutWin();
        }
        System.out.println("HMAC key: " + security.key);
        strings.soutLine();
        start();
    }
    private void printMenu(){
        System.out.println("Available moves:");
        for(int i = 0; i < combinatinos.length; i++){
            System.out.println(i + 1 + " - " + combinatinos[i]);
            if(i == combinatinos.length - 1)
                System.out.println(0 + " - Exit");
        }
    }
    private void validation(){
        Scanner scanner = new Scanner(System.in);
        userMove = scanner.nextInt();
        if (userMove > combinatinos.length | userMove < 0) {
            strings.soutIncorrChoice();
            printMenu();
            validation();
        }
    }

}
