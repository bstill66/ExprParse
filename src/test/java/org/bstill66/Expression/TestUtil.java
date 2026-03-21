package org.bstill66.Expression;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class TestUtil {
    protected static boolean parseString(String prog) {
        CharStream input = CharStreams.fromString(prog);

        // 2. Create a lexer and pass the input to it
        PrvLexer lexer = new PrvLexer(input);

        // 3. Create a token stream from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 4. Create a parser and pass the token stream to it
        PrvParser parser = new PrvParser(tokens);
        parser.removeErrorListeners();
        parser.setErrorHandler(new BailErrorStrategy());

        try {
            parser.program(); // Replace 'startRule' with your grammar's main rule
            return true; // No exception means validation succeeded
        } catch (ParseCancellationException e) {
            return false; // An exception means validation failed
        }
    }


}
