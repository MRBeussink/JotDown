import java.io.*;
import java.util.*;

/**
 * Created by markbeussink on 11/29/16.
 */
public class Parser {

    private StringBuilder output;
    private Lexer lxr;

    private ArrayList BodyList;



    public Parser(FileReader inputFile) {
        this.lxr = new Lexer(inputFile);

        this.output = new StringBuilder();
    }

    public StringBuilder parse() {


        ArrayDeque<ParserState> states = new ArrayDeque<>();    // Create new deque to store lexemes
        states.add(ParserState.Body);                           // Initial state

        LexerToken tkn;

        // loop over lexemes until EoF
        do {

            tkn = lxr.lex();
            ParserState state = states.getLast().transition(tkn);   // transition the last state

            // ToDo: fix this
            // change to text with
            while (tkn == null) {
                System.out.printf("Cannot transition from State: %s, via token: %s",
                        states.getLast().name(), tkn.name());

                LexerToken txt = LexerToken.Text;
                txt.setValue(tkn.getValue());
                state = states.getLast().transition(txt);
            }

            states.addLast(state);                                  // store the new state

        } while (!states.getLast().equals(ParserState.End.name()));


        // translate

        return output;
    }
}
