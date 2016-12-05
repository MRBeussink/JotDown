import java.io.*;
import java.util.*;

/**
 * Created by markbeussink on 11/29/16.
 */
public class Parser  {

    private StringBuilder output;
    private Lexer lxr;

    private Vector<ParserState> states;
    private DOMTree stateTree;

    public class DOMTree {

        private ParserState root;

        Vector<DOMNode> children;

        public DOMTree(ParserState root) {
            this.root = root;
            children = new Vector<>();
        }

        public void addChild(DOMNode node) {
            children.add(node);
        }

        public void getChildAt(int index){
            children.elementAt(index);
        }
    }

    public class DOMNode {

        ParserState value;

        protected DOMNode next;
        protected DOMNode nested;

        public DOMNode(ParserState value) {
            this.value = value;
        }
    }


    public Parser(FileReader inputFile) {
        this.lxr = new Lexer(inputFile);

        this.output = new StringBuilder();
    }

    public StringBuilder parse() {


        states = new Vector<>();        // Create new deque to store lexemes
        states.add(ParserState.Body);   // Initial state

        LexerToken tkn;

        // loop over lexemes until EoF
        do {

            tkn = lxr.lex();
            ParserState state = states.lastElement().transition(tkn);   // transition the last state

            // ToDo: fix this
            // change to text with
            while (tkn == null) {
                System.out.printf("Cannot transition from State: %s, via token: %s",
                        states.lastElement().name(), tkn.name());

                LexerToken txt = LexerToken.Text;
                txt.setValue(tkn.getValue());

                state = states.lastElement().transition(txt);
            }

            state.setToken(tkn);                                // store original token
            states.add(state);                                  // store the new state

        } while (!states.lastElement().equals(ParserState.End.name()));

        // build a tree
        stateTree = new DOMTree(states.firstElement());
        states.removeElementAt(0);

        // loop over remaining states 1 by 1
        // removing when the translation is understood

        while (!states.isEmpty()) {

            buildTree();
        }

        // translate


        return output;
    }

    private void buildTree() {

        if (states.isEmpty()) { return; }

        if (states.firstElement().allowsNesting()) {

            // find match
            ParserState match = findMatch(states.firstElement());

            // if match found
            if (match != null) {
                DOMNode node = new DOMNode(states.firstElement());  // create node
                states.removeElementAt(0);

                node.nested = buildTreeUntilMatch();
            }

            // if match not found
            else {
                LexerToken txt = LexerToken.Text;
                txt.setValue(states.firstElement().getToken().getValue());
            }
        }
    }

    private ParserState findMatch(ParserState key) {

        for (ParserState s : states) {
            if (key.isClosingState(s)) {
                return s;
            }
        }

        return null;
    }

}
