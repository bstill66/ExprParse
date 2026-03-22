package org.example;



import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.Expression.PrvLexer;
import org.bstill66.Expression.PrvParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.diogonunes.jcolor.Attribute.*;


public class ValidateExpression {

    final static AnsiFormat SUCCESS = new AnsiFormat(GREEN_TEXT());
    final static AnsiFormat FAILURE = new AnsiFormat(RED_TEXT());


    public static Namespace parse(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("ValidateExpression").build()
                .defaultHelp(true)
                .description("Validate Rule Expression");

        parser.addArgument("-s")
                .dest("stringFlag")
                .setDefault(false)
                .type(Boolean.class)
                .action(Arguments.storeTrue())
                .help("Interpret arguments as strings vs. files");

        parser.addArgument("infiles")
                .nargs("*")
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


    private boolean parse(CharStream input) {


        // 2. Create a lexer to turn the character stream into a token stream
        PrvLexer lexer = new PrvLexer(input);

        // Create a CommonTokenStream from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser from the token stream
        PrvParser parser = new PrvParser(tokens);

        // Setup Bail out strategy
        parser.removeErrorListeners();
        parser.setErrorHandler(new BailErrorStrategy());

        try {
            ParseTree ptree = parser.program();
            return true;
        }
        catch (ParseCancellationException e) {
            return false; // An exception means validation failed
        }

        // 6. (Optional) Walk the parse tree using a listener or visitor to process the data
        // ParseTreeWalker walker = new ParseTreeWalker();
        // MyListener listener = new MyListener();
        // walker.walk(listener, tree);

    }

    public boolean parseFile(String fname) throws Exception {

            // 1. Create a CharStream from the input file
            CharStream input = CharStreams.fromFileName(fname);

            return parse(input);
    }

    public boolean parseString(String program) {
        CharStream cs = CharStreams.fromString(program);

        return parse(cs);
    }

    private static String fmtColor(AnsiFormat fmt,String tmpl, Object...args) {
        String res = String.format(tmpl,args);
        return res;
    }

    public static void main(String[] argv) {


        // Parse arguments
        Namespace params = parse(argv);

        ValidateExpression  e = new ValidateExpression();
        try {
            List<String> infiles = params.getList("infiles");

            for (String inpFile : infiles) {
                try {
                    boolean success = false;
                    if (params.getBoolean("stringFlag")) {
                        success = e.parseString(inpFile);
                    }
                    else {
                        success = e.parseFile(inpFile);
                    }

                    if (success) {
                        System.out.printf(fmtColor(SUCCESS,"Success: %s",inpFile));
                    }
                    else {
                        System.out.printf(fmtColor(FAILURE,"Failed : %s",inpFile));
                    }
                }
                catch (FileNotFoundException fnf) {
                    System.out.printf(fmtColor(FAILURE,"Failed : %s   - File Not Found", inpFile));
                }
                catch (IOException io) {
                    System.out.printf(fmtColor(FAILURE,"Failed : %s  - I/O Exception",inpFile));
                }

                System.out.println();
            }
        }
        catch (Exception _) {

        }

    }

}
