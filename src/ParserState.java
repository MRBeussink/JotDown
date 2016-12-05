import jdk.nashorn.internal.parser.*;
import jdk.nashorn.internal.parser.Lexer;

/**
 * Created by markbeussink on 11/29/16.
 */
public enum ParserState {

    Body {

        @Override
        protected ParserState transition(LexerToken token) {

            ParserState state = super.transition(token);

            if (state == null){
                switch (token) {

                    case HrzRule:
                        state = HorizontalRule;
                        break;

                    case Header:
                        state = Header;
                        break;

                    case Ital:
                        state = Ital;
                        break;

                    case Bold:
                        state =  Bold;
                        break;

                    case Strike:
                        state = Strike;
                        break;

                    case Code:
                        state = Code;
                        break;

                    case QBlock:
                        state = QuoteBlock;
                        break;

                    case CBlock:
                        state = CodeBlock;
                        break;

                    case OList:
                        state = OrderedList;
                        break;

                    case UList:
                        state = UnorderedList;
                        break;

                    case LinkOpen:
                        state = Link;
                        break;

                    case ImgOpen:
                        state = Image;
                        break;

                    default:
                        break;
                }
            }

            return state;
        }
    },

    HorizontalRule { },

    Header { },

    Ital { },

    Bold { },

    Strike { },

    Code { },

    QuoteBlock { },

    CodeBlock { },

    OrderedList { },

    UnorderedList { },

    Link { },

    Image { },

    Text {

        @Override protected ParserState transition(LexerToken token) {

            ParserState state = super.transition(token);

            if (token == null) {
                switch (token) {

                    case Ital:
                        return Ital;

                    case Bold:
                        return Bold;

                    case Strike:
                        return Strike;

                    case Code:
                        return Code;

                    case QBlock:
                        return QuoteBlock;

                    case CBlock:
                        return CodeBlock;

                    case LinkClose:
                        return Link;

                    case ImgClose:
                        return Image;

                    case Split:
                        return Text;

                    default:
                        break;
                }
            }

            return state;
        }
    },

    NewLine {

        @Override protected ParserState transition(LexerToken token) {
            return Body.transition(token);
        }
    },

    End { };

    protected ParserState transition(LexerToken token) {
        switch (token) {
            case Text:
                return Text;
            case NewLine:
                return NewLine;
            case EoF:
                return End;
            default:
                break;
        }

        return null;
    }

    // public class FSMTransitionException extends Exception { }
}
