import java.io.*;

public class Main {

	 public static void main(String[] args) throws IOException {

		 //if (args.length == 2) {
			 String template
					 = "<!doctype html>\n\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>The HTML5 Herald</title>\n<meta name=\"description\" content=\"The HTML5 Herald\">\n<meta name=\"author\" content=\"SitePoint\">\n<link rel=\"stylesheet\" href=\"css/styles.css?v=1.0\">\n\n</head>\n\n";
			 StringBuilder output;
			 String filename = "";
		 	for (String arg : args) {
				filename += arg + " ";
			}
		 	System.out.println("Trying to open " + filename);
			 String outputfile = "";

			 int i = 0;
			 while (filename.charAt(i) != '.') {
				 outputfile = outputfile + filename.charAt(i);
				 i++;
			 }
			 
			 File read = new File(args[0]);
			 FileReader infile = new FileReader(read);
			 FileWriter outfile = new FileWriter(outputfile + ".html");
			 System.out.println("Parsing file");
			 Parser prsr = new Parser(infile);
			 prsr.parse();

			 
			 output = prsr.parse();

			 // write output to file

			 outfile.write(output.toString());

			 System.out.println("Yaata~");
		 //}

		 //System.out.println("No input file \nTerminating...");
	 }
}
