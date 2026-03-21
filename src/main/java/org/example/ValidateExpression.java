package org.example;



import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.Expression.PrvLexer;
import org.bstill66.Expression.PrvParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class ValidateExpression {

    public static Namespace parse(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("ValidateExpression").build()
                .defaultHelp(true)
                .description("Validate Rule Expression");

        parser.addArgument("infiles").nargs("*")
                .help("Input Files to Validate");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        }
        catch (ArgumentParserException ex) {
            parser.handleError(ex);
            System.exit(1);
        }

        return ns;
    }



    public boolean parseFile(String fname) throws Exception {

            // 1. Create a CharStream from the input file
            CharStream input = CharStreams.fromFileName(fname);

            // 2. Create a lexer to turn the character stream into a token stream
            PrvLexer lexer = new PrvLexer(input);

            // 3. Create a CommonTokenStream from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 4. Create a parser from the token stream
            PrvParser parser = new PrvParser(tokens);

            // 5. Start the parsing process by calling your grammar's entry point rule
            //    (e.g., 'prog' or 'startRule'). This returns a ParseTree.
            ParseTree tree = parser.program(); // Assuming 'prog' is the start rule in Expr.g4

            // 6. (Optional) Walk the parse tree using a listener or visitor to process the data
            // ParseTreeWalker walker = new ParseTreeWalker();
            // MyListener listener = new MyListener();
            // walker.walk(listener, tree);

        return false;
    }

    public static void main(String[] argv) {


        // Parse arguments
        Namespace params = parse(argv);

        ValidateExpression  e = new ValidateExpression();
        try {
            List<String> infiles = params.getList("infiles");

            for (String inpFile : infiles) {
                try {
                    e.parseFile(inpFile);
                }
                catch (FileNotFoundException fnf) {
                    System.out.printf("File Not Found: %s", inpFile);
                }
                catch (IOException io) {
                    System.out.printf("Error reading file: %s",inpFile);
                }
            }
        }
        catch (Exception _) {

        }

    }

}
