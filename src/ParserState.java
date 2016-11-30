/**
 * Created by markbeussink on 11/29/16.
 */
public enum ParserState {

    Body {

        public ParserState transition(LexerToken token){
            switch (token) {
                case Header:
                    return Header;

                case HrzRule:
                    return ParserState.Reduce;

                case Ital: case Bold: case Strike: case Code:
                    return Emphasis;

                case UList:case OList:
                    return List;

                case CBlock:
                    return CodeBlock;

                case QBlock:
                    return QuoteBlock;

                case LinkOpen:
                    return Link;

                case ImgOpen:
                    return Image;

                case Text:
                    return Paragraph;

                default:
                    break;
            }
            return null;
        }
    },

    Header {

        public ParserState transition(LexerToken token){
            switch (token) {
                case Text:
                    return Text;

                case NewLine:
                    return Reduce;

                default:
                    break;
            }

            return null;
        }
    },

    HorizontalRule {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case NewLine:
                    return Reduce;
                default:
                    break;
            }
            return null;
        }
    },

    Paragraph {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case Text:
                    return Paragraph;
                case NewLine:
                    return Reduce;
                default:
                    break;
            }
            return null;
        }
    },

    Emphasis {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case Ital:
                    return Ital;
                case Bold:
                    return Bold;
                case Strike:
                    return Strike;
                case Code:
                    return Code;
                default:
                    break;
            }
            return null;
        }
    },

    Ital {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case Ital:
                    return Reduce;
                case Text:
                    return Text;
                default:
                    break;
            }
            return null;
        }
    },

    Bold {

        public ParserState transition(LexerToken token) {
            switch(token) {
                case Bold:
                    return Reduce;
                case Text:
                    return Text;
                default:
                    break;
            }
            return null;
        }
    },

    Strike {

        public ParserState transition(LexerToken token) {
            switch (token){
                case Strike:
                    return Reduce;
                case Text:
                    return Text;
                default:
                    break;
            }
            return null;
        }
    },

    Code {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case Code:
                    return Reduce;
                case Text:
                    return Text;
                default:
                    break;
            }
            return null;
        }
    },

    QuoteBlock {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case Text:
                    return Paragraph;
                case QBlock:
                    return Reduce;
                default:
                    break;
            }
            return null;
        }
    },

    CodeBlock {

        public ParserState transition(LexerToken token){
            switch (token) {
                case Text:
                    return Text;
                case CBlock:
                    return Reduce;
                default:
                    break;
            }
            return null;
        }
    },

    OrderedList {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case OList:
                    return ListItem;
                default:
                    break;
            }
            return null;
        }
    },


    UnOrderedList {

        public ParserState transition(LexerToken token) {
            switch (token) {
                case UList:
                    return ListItem;
                default:
                    break;
            }
            return null;
        }
    },

    ListItem { },


    Link {

    },

    Image {

    },

    Text {

    },

    Reduce {

    };

    public abstract ParserState transition(LexerToken token);

    // public class FSMTransitionException extends Exception { }
}
