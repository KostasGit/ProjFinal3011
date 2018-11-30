import java.util.Scanner;


public class ReadToWriteToInput {

    Scanner keyboard = new Scanner(System.in);


    public ReadToWriteToInput() {

    }


    public int readFrom() {
        System.out.println("--Where you want to bring data from\n*1 Csv\n*2 DB");

        int readFrom = keyboard.nextInt();
        return readFrom;
    }

    public int writeTo() {
        System.out.println("--Where you want to write data to\n*1 Csv\n*2 DB");

        int writeTo = keyboard.nextInt();
        return writeTo;
    }

}
