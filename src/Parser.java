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

    private int headerOrthoginality;

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

            try {
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
            } catch (IOException e) {
                System.out.println("IOException while lexing. \n\t ending early");

                states.addElement(ParserState.End);
            }
        } while (!states.lastElement().equals(ParserState.End));

        // build a tree
        stateTree = new DOMTree(states.firstElement());
        states.removeElementAt(0);

        // loop over remaining states 1 by 1
        // removing when the translation is understood

        while (!states.isEmpty()) {

            buildTree();
        }

        // translate

        output = translate(stateTree.children);


        return output;
    }

    private void buildTree() {

        if (states.isEmpty()) { return; }

        DOMNode node;

        if (states.firstElement().allowsNesting()) {

            // find match
            ParserState match = findMatch(states.firstElement());

            // if match found
            if (match != null) {
                node = new DOMNode(states.firstElement());  // create node
                states.removeElementAt(0);

                node.nested = buildTreeUntilMatch(match);
            }

            // if match not found
            else {
                LexerToken txt = LexerToken.Text;
                txt.setValue(states.firstElement().getToken().getValue());

                ParserState state = ParserState.Text;
                state.setToken(txt);

                states.removeElementAt(0);

                node = new DOMNode(state);

                node.next = buildTreeUntilNewLine();
            }
        }
        else {
            node = new DOMNode(states.firstElement());
            states.removeElementAt(0);

            node.next = buildTreeUntilNewLine();
        }

        stateTree.addChild(node);
    }

    private DOMNode buildTreeUntilMatch(ParserState key) {

        DOMNode node;
        if (states.firstElement() == key) {

            node = new DOMNode(states.firstElement());
            states.removeElementAt(0);
        }

        else {
            if (states.isEmpty()) { node = null; }

            if (states.firstElement().allowsNesting()) {

                // find match
                ParserState match = findMatch(states.firstElement());

                // if match found
                if (match != null) {
                    node = new DOMNode(states.firstElement());  // create node
                    states.removeElementAt(0);

                    node.nested = buildTreeUntilMatch(match);
                }

                // if match not found
                else {
                    LexerToken txt = LexerToken.Text;
                    txt.setValue(states.firstElement().getToken().getValue());

                    ParserState state = ParserState.Text;
                    state.setToken(txt);

                    states.removeElementAt(0);

                    node = new DOMNode(state);

                    node.next = buildTreeUntilNewLine();
                }
            }
            else {
                node = new DOMNode(states.firstElement());
                states.removeElementAt(0);

                node.next = buildTreeUntilNewLine();
            }
        }

        return node;
    }

    private DOMNode buildTreeUntilNewLine() {

        DOMNode node;
        if (states.firstElement().equals(ParserState.NewLine)) {

            node = new DOMNode(states.firstElement());
            states.removeElementAt(0);
        }

        else {
            if (states.isEmpty()) { node = null; }

            if (states.firstElement().allowsNesting()) {

                // find match
                ParserState match = findMatch(states.firstElement());

                // if match found
                if (match != null) {
                    node = new DOMNode(states.firstElement());  // create node
                    states.removeElementAt(0);

                    node.nested = buildTreeUntilMatch(match);
                }

                // if match not found
                else {
                    LexerToken txt = LexerToken.Text;
                    txt.setValue(states.firstElement().getToken().getValue());

                    ParserState state = ParserState.Text;
                    state.setToken(txt);

                    states.removeElementAt(0);

                    node = new DOMNode(state);

                    node.next = buildTreeUntilNewLine();
                }
            }
            else {
                node = new DOMNode(states.firstElement());
                states.removeElementAt(0);

                node.next = buildTreeUntilNewLine();
            }
        }

        return node;
    }

    private ParserState findMatch(ParserState key) {

        for (ParserState s : states) {
            if (key.isClosingState(s)) {
                return s;
            }
        }

        return null;
    }

    private StringBuilder translate(Vector<DOMNode> nodes) {

        StringBuilder output = new StringBuilder();
        output.append("<body> \n");
        for (DOMNode node : nodes) {

            switch (node.value) {

                case Ital:
                case Bold:
                case Strike:
                case Code:
                case Link:
                case Image:
                    output.append("<p>\n");
                    break;

                case OrderedList:
                    output.append("<ol>\n");
                    break;

                case UnorderedList:
                    output.append("<ul>\n");
                default:
                    break;

            }
            output.append(translateSubtree(node));

            switch (node.value) {

                case Ital:
                case Bold:
                case Strike:
                case Code:
                case Link:
                case Image:
                    output.append("</p>\n");
                    break;
                default:
                    break;
            }
        }

        output.append("</body> \n");
        return output;
    }

    private StringBuilder translateSubtree(DOMNode node) {

        StringBuilder output = new StringBuilder();

        switch (node.value) {

            case HorizontalRule:
                output.append("<hr>");
                break;

            case Header:
                String num = node.value.getToken().getValue();
                output.append("<h" + num + "> ");
                output.append(translateSubtree(node.next));
                output.append("</h" + num + "> ");
                break;

            case Ital:
                if (node.nested == null) {
                    output.append("</i> ");
                }
                else {
                    output.append("<i>");
                    output.append(translateSubtree(node.nested));
                    output.append(translateSubtree(node.next));
                }
                break;

            case Bold:
                if (node.nested == null) {
                    output.append("</b> ");
                }
                else {
                    output.append("<b>");
                    output.append(translateSubtree(node.nested));
                    output.append(translateSubtree(node.next));
                }
                break;

            case Strike:
                if (node.nested == null) {
                    output.append("</strikethrough> ");
                }
                else {
                    output.append("<strikethrough>");
                    output.append(translateSubtree(node.nested));
                    output.append(translateSubtree(node.next));
                }
                break;

            case Code:
                if (node.nested == null) {
                    output.append("</code>");
                }
                else {
                    output.append("<code>");
                    output.append(translateSubtree(node.nested));
                    output.append(translateSubtree(node.next));
                }
                break;

            case QuoteBlock:

                output.append("<blockquote> \n");

                while (!node.next.value.equals(ParserState.QuoteBlock)) {
                    node = node.next;

                    output.append("<p> ");

                    if (node.nested == null) {

                        if (node.next.equals(ParserState.NewLine)) {
                            output.append("</p>\n");
                        }
                        else {
                            output.append(node.value.getToken().getValue());
                        }
                    }
                    else {
                        output.append(translateSubtree(node));
                    }
                }
                output.append("</blockquote> \n\n");
                break;

            case CodeBlock:

                output.append("<pre>\n");

                while (!node.next.value.equals(ParserState.CodeBlockClose)) {
                    node = node.next;

                    output.append(node.value.getToken().getValue());
                }

                output.append("</pre> \n\n");

                break;

            case OrderedList:

                while (!node.next.value.equals(ParserState.NewLine)){

                    if (node.nested == null){
                        output.append(translateSubtree(node.next));
                    }
                    else {
                        output.append("<li> \n<p>");
                        output.append(translateSubtree(node.nested));
                        output.append("</p>\n</li>\n");
                        node = node.next;

                        /// I need the nested list item to have open and close tags
                    }
                }

                output.append("</ol>\n\n");

                break;

            case UnorderedList:

                while (!node.next.value.equals(ParserState.NewLine)){

                    if (node.nested == null){
                        output.append(translateSubtree(node.next));
                    }
                    else {
                        output.append("<li> \n<p>");
                        output.append(translateSubtree(node.nested));
                        output.append("</p>\n</li>\n");
                        node = node.next;

                        /// I need the nested list item to have open and close tags
                    }
                }

                output.append("</ul>\n\n");

                break;

            case Link:

                String open = "<a href=\"";
                String url = "missing url";
                String middle = "\">";
                String text = "missing text";
                String close = "</a>";

                DOMNode findingNode = node.nested;

                // get url
                while (!findingNode.value.equals(ParserState.LinkURL) && findingNode.next != null) {

                    findingNode = node.next;
                }

                if (findingNode.value.equals(ParserState.LinkURL) && findingNode.next != null) {
                    url = findingNode.value.getToken().getValue();
                }

                findingNode = node.nested;

                while (!findingNode.value.equals(ParserState.LinkText)  && findingNode.next != null) {

                    findingNode = node.next;
                }

                if (findingNode.value.equals(ParserState.LinkText)  && findingNode.next != null) {
                    text = findingNode.value.getToken().getValue();
                }

                StringBuilder temp = new StringBuilder(open + url + middle + text + close);
                output.append(open + url + middle + text + close);
                break;

            case Image:

                open = "<img src\"";
                String src = "missing url";
                middle = " alt=\"";
                text = "missing text";
                close = "\">";

                findingNode = node.nested;

                // get url
                while (!findingNode.value.equals(ParserState.ImageURL) && findingNode.next != null) {

                    findingNode = node.next;
                }

                if (findingNode.value.equals(ParserState.ImageURL) && findingNode.next != null) {
                    url = findingNode.value.getToken().getValue();
                }

                findingNode = node.nested;

                while (!findingNode.value.equals(ParserState.ImageText)  && findingNode.next != null) {

                    findingNode = node.next;
                }

                if (findingNode.value.equals(ParserState.ImageText)  && findingNode.next != null) {
                    text = findingNode.value.getToken().getValue();
                }

                output.append(open + src + middle + text + close);
                break;

            default:
                System.out.println("Translation switch statement has defaulted");
                output.append("ERROR");
                break;


        }

        return output;
    }

}
