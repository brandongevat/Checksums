/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Brandon Gevat
| Language: c, c++, Java, GO, Python
|
| To Compile: javac pa02.java
| gcc -o pa02 pa02.c
| g++ -o pa02 pa02.cpp
| go build pa02.go
| python pa02.py //Caution - expecting input parameters
|
| To Execute: java -> java pa02 inputFile.txt 8
| or c++ -> ./pa02 inputFile.txt 8
| or c -> ./pa02 inputFile.txt 8
| or go -> ./pa02 inputFile.txt 8
| or python-> python pa02.py inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Spring 2022
| Instructor: McAlpin
| Due Date: per assignment
|
+=============================================================================*/
import java.util.*;
import java.io.*;

public class pa02 {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        File file = new File(fileName);
        int bitCount = Integer.parseInt(args[1]);

        // Check for incorrect arguments
        if (bitCount != 8 && bitCount != 16 && bitCount != 32)
            System.err.println("Valid checksum sizes are 8, 16, or 32");

        // Read in input file
        String line;
        ArrayList<Character> arr = new ArrayList<Character>();
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            for (int i = 0; i < line.length(); i++) {
                arr.add(line.charAt(i));
                if (i % 80 == 0)
                    System.out.println();
                System.out.print(arr.get(i));
            }
            arr.add('\n');
            System.out.println();
        }

        // Echo the input read @ 80 characters per row
        // for (int i = 0; i < arr.size(); i++) {
        // if (arr.get(i) == '\u0000')
        // break;
        // if (i % 80 == 0 && i != 0)
        // System.out.println();
        // System.out.print(arr.get(i));
        // }

        int blockSize;
        int pad = 0;
        long checksum = 0;
        if (bitCount == 8) {
            blockSize = 1;
            for (int i = 0; i < arr.size(); i++) {
                if ((int) arr.get(i) == 0)
                    break;
                checksum += (int) arr.get(i);
            }
        }

        if (bitCount == 16) {
            blockSize = 2;
            if (arr.size() % blockSize != 0)
                pad = blockSize - arr.size() % blockSize;
            // System.out.println(pad);
            for (int i = 0; i < pad; i++)
                arr.add('X');
            // System.out.println(arr.toString());
            for (int i = 0; i < arr.size(); i += 2) {
                checksum += (int) (arr.get(i) << 8) + (int) arr.get(i + 1);

                // checksum += 256 * (int) arr.get(i) + (int) arr.get(i + 1);
            }
        }

        if (bitCount == 32) {
            blockSize = 4;
            if (arr.size() % blockSize != 0)
                pad = blockSize - arr.size() % blockSize;
            // System.out.println(pad);
            for (int i = 0; i < pad; i++)
                arr.add('X');
            // System.out.println(arr.toString());
            for (int i = 0; i < arr.size(); i += 4) {
                checksum += (long) (arr.get(i) << 24) + (long) (arr.get(i + 1) << 16)
                        + (long) (arr.get(i + 2) << 8) + (long) arr.get(i + 3);
                // checksum += (long) Math.pow(2, 24) * (long) arr.get(i) + (long) Math.pow(2, 16) * (long) arr.get(i + 1)
                //         + 256 * (long) arr.get(i + 2) + (long) arr.get(i + 3);
            }
            // System.out.println(checksum);
        }
        for (int i = 0; i < pad; i++)
            System.out.print("X");
        System.out.println();

        System.out.println(String.format("%2d bit checksum is %8x for all %4d chars", bitCount,
                (long) checksum % (long) Math.pow(2, bitCount), arr.size()));

        sc.close();
    }

}

/*
 * =============================================================================
 * | I [Brandon Gevat] ([br866262]) affirm that this program is
 * | entirely my own work and that I have neither developed my code together
 * with
 * | any another person, nor copied any code from any other person, nor
 * permitted
 * | my code to be copied or otherwise used by any other person, nor have I
 * | copied, modified, or otherwise used programs created by others. I
 * acknowledge
 * | that any violation of the above terms will be treated as academic
 * dishonesty.
 * +============================================================================
 * =
 */