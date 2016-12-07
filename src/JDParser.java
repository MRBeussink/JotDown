/**
 * Created by Mark on 12/6/16.
 */
public class JDParser {

    JDLexer lexer;

    public JDParser(JDLexer lexer) {
        this.lexer = lexer;
    }

    public String parse() {

        JDLexerToken token; // = lexer.lex();

        /*
        do {
            token = lexer.lex();

            System.out.print(token.name() + " ");

            if (token == JDLexerToken.NewLine) {
                System.out.println();
            }

        } while (token != JDLexerToken.EoF);

        return "";
        */

        return "";
    }
}
