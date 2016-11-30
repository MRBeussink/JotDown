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
                    return HorizontalRule;

                case Ital: case Bold: case Strike: case Code:
                    return Emphasis;
                case UnOrdrList:case OrdrList:
                    return List;

                case CBlock: case QBlock:
                    return Block;
                case LinkOpen:
                    return Link;
                case ImgOpen:
                    return Image;
                // case NewLine:
                    break;
                case Text:
                    return Paragraph;
                default:
                    break;
            }
            return nil;
        }
    },

    Header {

        public ParserState transition(LexerToken token){
            switch (token) {
                case
            }
        }
    },

    HorizontalRule {

    },

    Paragraph {

    },

    Emphasis {

    },

    Block {

    },

    List {

    },

    Link {

    },

    Image {

    };

    public abstract ParserState transition(LexerToken token);

    public class FSMTransitionException extends Exception { }
}
