import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mark on 12/6/16.
 */
public class JDLexer {

    private BufferedReader reader;

    // private String lexeme;
    private String line;
    private int position;
    private JDLexerToken nextToken;

    private static final String WHITESPACE = "(\\s)";

    /** Creates JDLexer from file reader
     *
     * @param fileReader the file for the lexer's buffered reader
     */
    public JDLexer(FileReader fileReader){

        this.reader = new BufferedReader(fileReader);
    }

    /** Reads the input file and returns the next token
     *
     * @return the next token
     */
    public JDLexerToken lex() {

        // check that line is not null
        // true -
        //        get next line from reader
        //      catch -
        //        EoF
        // else -
        //        go to next word
        if (line == null) {

            try {
                line = reader.readLine();
                position = 0;
            } catch (IOException e) {
                nextToken = JDLexerToken.EoF;
            }
        }

        // if line is still null
        if (line == null) {
            nextToken = JDLexerToken.EoF;
        }

         else if (isAtEnd()) {
            nextToken = JDLexerToken.NewLine;
            line = null;
            position = 0;
        }

        else {


            // go to next word
            String lexeme = getLexeme();

            switch (lexeme) {

                case "#1":
                    nextToken = JDLexerToken.H1;
                    break;
                case "#2":
                    nextToken = JDLexerToken.H2;
                    break;
                case "#3":
                    nextToken = JDLexerToken.H3;
                    break;
                case "#4":
                    nextToken = JDLexerToken.H4;
                    break;
                case "#5":
                    nextToken = JDLexerToken.H5;
                    break;
                case "#6":
                    nextToken = JDLexerToken.H6;
                    break;
                case "***":
                    nextToken = JDLexerToken.HrzRule;
                    break;
                case "_":
                    nextToken = JDLexerToken.Ital;
                    break;
                case "*":
                    nextToken = JDLexerToken.Bold;
                    break;
                case "-":
                    nextToken = JDLexerToken.Strike;
                    break;
                case "`":
                    nextToken = JDLexerToken.Code;
                    break;
                case "```":
                    nextToken = JDLexerToken.CBlock;
                    break;
                case ">>":
                    nextToken = JDLexerToken.QBlock;
                    break;
                case "[":
                    nextToken = JDLexerToken.LinkOpen;
                    break;
                case "]":
                    nextToken = JDLexerToken.LinkClose;
                    break;
                case "{":
                    nextToken = JDLexerToken.ImgOpen;
                    break;
                case "}":
                    nextToken = JDLexerToken.ImgClose;
                    break;
                case ":":
                    nextToken = JDLexerToken.Split;
                    break;
                case "- ":
                    nextToken = JDLexerToken.OList;
                    break;
                case "* ":
                    nextToken = JDLexerToken.UList;
                    break;
                default:
                    nextToken = JDLexerToken.Text;
                    nextToken.setValue(lexeme);
                    break;
            }
        }

        return nextToken;
    }

    /**
     * assigns the next lexeme
     */
    private String getLexeme() {

        // set position to beginning of next lexeme
        gotoLexeme();   //same as getNonBlank
        // tokenize lexeme

        StringBuilder lexemeBldr = new StringBuilder();

        //System.out.println('\\' == line.charAt(position));

        switch (line.charAt(position)) {

            // \

            // whitespace


            // #
            case '#':

                //check that there is room
                if (isThereRoom()) {

                    // there are two cases
                    // - it is header
                    // - it is plain text

                    // check if next character is valid header
                    if(isHeaderNumber(line.charAt(position + 1))) {

                        // add character to lexeme
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                }

                // cannot be header
                // therefore append
                lexemeBldr.append(line.charAt(position));
                advancePosition();
                break;

            // *
            case '*':

                // There are tree cases
                // - it is unordered list
                // - it is bold
                // - it is horizontal rule

                // check if there is room
                if (isThereRoom()) {

                    // check that it is first character and that next character is space
                    if ((position == 0) && (' ' == line.charAt(position + 1))) {

                        // therefore is unordered list
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }

                    // check that the first character is *, and * and endofline
                    else {

                        if (line.charAt(position + 1) == '*') {
                            lexemeBldr.append(line.charAt(position));
                            advancePosition();
                        }
                        if ((isThereRoom()) && (line.charAt(position + 1) == '*')) {
                            lexemeBldr.append(line.charAt(position));
                            advancePosition();
                        }
                        if ((isThereRoom()) && (line.charAt(position + 1) == '*')) {
                            lexemeBldr.append(line.charAt(position));
                            advancePosition();
                            break;
                        }

                    }
                }

                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;

            // _
            case '_':

                // check if next character is space
                if(isThereRoom()) {

                    if (line.charAt(position + 1) == ' ') {
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                }

                lexemeBldr.append(line.charAt(position));
                advancePosition();
                break;
            // -
            case '-':

                // Two cases
                // - ordered list
                // - strikethrough

                // check that it is the first character and that next character is space
                if (isThereRoom()) {

                    if ((position == 0) && (' ' == line.charAt(position + 1))) {

                        // therfore this is ordered list
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                }

                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;
            // `
            case '`':

                // two cases
                // - inline code
                // - code block

                if (isThereRoom()) {

                    // check that first character is `, and ` and `
                    if (line.charAt(position + 1) == '`') {
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                    if ((isThereRoom()) && (line.charAt(position + 1) == '`')) {
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                    if ((isThereRoom()) && (line.charAt(position + 1) == '`')) {
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                         break;
                    }
                }

                lexemeBldr.append(line.charAt(position));
                advancePosition();
                break;

            // >

            case '>':

                // if first character, and second character
                if (isThereRoom()) {

                    //check second character
                    if (line.charAt(position + 1) == '>') {

                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                }

                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;
            // [


            case '[':
                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;

            // ]

            case ']':
                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;
            // {

            case '{':
                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;

            // }
            case '}':
                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;
            // :
            case ':':
                lexemeBldr.append(line.charAt(position));
                advancePosition();

                break;

            default:

                /*
                    loop until reach special character or new line
                        - check if character is \
                            - if character after it, check if special character
                                - append to StringBuilder
                                - advance position

                            - append to StringBuilder
                            - advance position
                 */

                while ((!isAtEnd()) && (!isSpecialCharacter(line.charAt(position)))) {

                    if ((line.charAt(position) == '\\') && (isThereRoom())){

                        if (isSpecialCharacter(line.charAt(position + 1))){

                            lexemeBldr.append(line.charAt(position));
                            advancePosition();
                        }

                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }

                    else {
                        lexemeBldr.append(line.charAt(position));
                        advancePosition();
                    }
                }
                /*
                // check that not \
                // check that there is room
                if (isThereRoom()) {

                    // there are two cases
                    // - it is escape character
                    // - it is plain text

                    // check that next character is special
                    if (line.charAt(position) == '\\') {

                        if (isSpecialCharacter(line.charAt(position + 1))) {

                            // add next character to lexeme
                            lexemeBldr.append(line.charAt(position));
                            //lexemeBldr.append(line.charAt(position + 1));

                            // advance position
                            advancePosition();
                        }
                    }
                }

                // cannot be escape
                // therefore append
                lexemeBldr.append(line.charAt(position));
                advancePosition();


                while ((isThereRoom()) && (!isSpecialCharacter(line.charAt(position)))) {

                    //while (!isSpecialCharacter(line.charAt(position))) {

                        if (line.charAt(position) == '\\')
                            // there are two cases
                            // - it is escape character
                            // - it is plain text

                            // check that next character is special
                            if (isSpecialCharacter(line.charAt(position + 1))) {

                                // add next character to lexeme
                                lexemeBldr.append(line.charAt(position));
                                //lexemeBldr.append(line.charAt(position + 1));

                                // advance position
                                advancePosition();
                            }

                        lexemeBldr.append(line.charAt(position));
                        advancePosition();

                        if (isAtEnd()) {
                            break;
                        }

                    //}

                    if (isAtEnd()) {
                        break;
                    }

                    break;
                }

                break;*/
        }

        return lexemeBldr.toString();
    }

    /**
     * Returns whether or not the character is special
     * @param c the character to check
     * @return true if special
     */
    private boolean isSpecialCharacter(char c){

        //System.out.println(" - - - - - - checking char:" + c);
        switch (c) {

            case '#':
            case '*':
            case '-':
            case '_':
            case '`':
            case '>':
            case '[':
            case '{':
            case '}':
            case ']':
            case ':':
                //System.out.println(" - - - - - - FOUND SPECIAL CHARACTER!");
                return true;
            /*
            case '\\':
                System.out.println(" - - - - - - FOUND ESCAPE CHARACTER");
                */

            default:
                //System.out.println(" - - - - - - Did not find special character.");
                return false;
        }
    }


    private boolean isHeaderNumber(char c) {

        //System.out.println("- - - - - - checking if " + c + " is a number");

        switch (c) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
               //System.out.println(" - - - - - - YES!");
                return true;

            default:
                return false;
        }
    }

    private boolean isThereRoom() {
        return (position < line.length() - 1);
    }

    private boolean isAtEnd() {
        return (position + 1 > line.length());
    }

    private void gotoLexeme() {
        while (isThereRoom()) {

            String s = line.substring(position, position + 1);

            if (s.matches(WHITESPACE)) {
                advancePosition();
                continue;
            }

            return;
        }
    }


    private void advancePosition() {

        //if(!isAtEnd())
            position++;
    }



    public static void main(String[] args){

    }



}
