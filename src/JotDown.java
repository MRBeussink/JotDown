import java.io.*;

/**
 * Created by Mark on 12/6/16.
 */
public class JotDown {

    private static String templateOpen = "<!DOCTYPE html> \n" +
                              "<html>\n\n" +
                              "<head>\n" +
                              "<title>JotDownTest</title>\n"+
                              "</head>\n\n" +
                              "<body>\n\n";

    private static String templateClose = "\n\n</body>\n\n</html>";


    public static void main(String[] args) {

        String filename = args[0];
        String outputName;

        String output;

        File file;
        FileReader fr;
        FileWriter fw;

        try {

            file = new File(args[0]);
            fr = new FileReader(file);

        } catch (FileNotFoundException fnfe) {

            System.out.println("Could not find such file " + filename);
            System.out.println("Terminating.");
            return;
        }

        JDLexer lxr = new JDLexer(fr);
        JDParser prsr = new JDParser(lxr);

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        try {

            prsr.parse();

            System.out.println("Yaata~");
        } catch (JDParser.JDParseError e) {

            System.out.println(e);
        }

=======
>>>>>>> Stashed changes
        output = prsr.parse();
        
        System.out.println(templateOpen + output + templateClose);
        
<<<<<<< Updated upstream
=======
>>>>>>> origin/master
>>>>>>> Stashed changes
        /*
        // System.out.println(filename.substring(0, filename.length() - 4));
        outputName = filename.substring(0, filename.length() - 4) + ".html";


        File outputFile = new File(outputName);
<<<<<<< Updated upstream
		*/
=======
<<<<<<< HEAD
        */
=======
		*/
>>>>>>> origin/master
>>>>>>> Stashed changes

    }
}
