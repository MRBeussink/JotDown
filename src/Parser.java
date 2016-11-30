import java.io.*;

/**
 * Created by markbeussink on 11/29/16.
 */
public class Parser {

    StringBuilder output;
    Lexer lxr;

    public Parser(FileReader inputFile) {
        this.lxr = new Lexer(inputFile);

        this.output = new StringBuilder();
    }

    public void parse() {

    }
}
