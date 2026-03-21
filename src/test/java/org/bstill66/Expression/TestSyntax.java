package org.bstill66.Expression;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Test;


import static org.bstill66.Expression.TestUtil.parseString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSyntax {



    @Test
    public void ValidateList() {
        assertTrue(parseString("var in []"));
        assertTrue(parseString("var not in []"));

        assertTrue(parseString("var in [4,5,6,\"Brian\"]"));
        assertTrue(parseString("var not in [\"Brian\",4,3]"));

        // Failure cases
        assertFalse(parseString("var in y"));
        assertFalse(parseString("var not in x"));
        assertFalse(parseString(""));
    }

    @Test
    public void validateEquality() {
        assertTrue(parseString("x=5"));
        assertTrue(parseString("x = \"Brian\""));
        assertTrue(parseString("xyz = +4345"));
        assertTrue(parseString("xyz = -4345"));
        assertTrue(parseString("xyz = \"Helloworld\""));


        assertFalse(parseString("x = y"));
        assertFalse(parseString("5 = 6"));
        //assertFalse(parseString("x = +-43"));

    }

    @Test
    public void validateEidr() {
        // all caps
        assertTrue(parseString("x = E\"10.240/AAAA-BBBB-CCCC-DDDD-K\""));
        // lower case
        assertTrue(parseString("x=E\"10.240/aaaa-bbbb-cccc-dddd-J\""));

        // Mixed case
        assertTrue(parseString("x=E\"10.240/AaAa-BbBb-CcCd-DdDd-K\""));

        // without prefix
        assertTrue(parseString("x=E\"/AaAa-BbBb-CcCd-DdDd-K\""));

        // without /
        assertTrue(parseString("x=E\"AaAa-BbBb-CcCd-DdDd-K\""));
    }

    @Test
    public void validateString() {

        // Empty String
        assertTrue(parseString("x = \"\""));
        assertTrue(parseString("x = \"basic\""));
        assertTrue(parseString("x=\"Really long string............tooo long\""));
        assertTrue(parseString("x=\"/* comment */x = 4\""));

        assertTrue(parseString("x ~= \"\""));
        assertTrue(parseString("x ~= \"some string\""));

    }

    @Test
    public void validateNumber() {
        final String[] good = {"0","000","043","999","-999","-0", "+0","+999"};
        final String[] bad  = {"+s","04A","0A4","_44"};
        final String codeTmpl = "var = %s";

        for (String g : good) {
            String code = String.format(codeTmpl,g);
            assertTrue(parseString(code));
        }

        for (String b: bad) {
            String code = String.format(codeTmpl,b);
            assertFalse(parseString(code));
        }

    }

    @Test
    public void validateVariable() {
        final String[] vars = {"x","_x","_2x","xxx","x_yyy","x23","yy_23_yx_2","__3"};
        final String[] exps = {
                "%s = 45",
                "%s=4",
                "%s in [\"asd\",3,4,\"def\"]"
        };

        for (String v : vars) {
            for (String e : exps) {
                String code = String.format(e,v);
                assertTrue(parseString(code));
            }
        }

        // Invalid Variables
        final String[] invalid = {"2x","xy!","xy_$"};
        for (String inv : invalid) {
            String code = String.format("%s = 23",inv);
            assertFalse(parseString(code));
        }

    }

    @Test
    public void validateComment() {
        // make sure no whitespace in the string
        final String code = "xyz\t \t=\t \t\"abajsdfklaj;ldkfj;acdef\"";
        final String comment = "/* This is a comment */";

        assertTrue(parseString(code));
        assertTrue(parseString(comment + code));
        assertTrue(parseString(code + comment));

        for (int i=1;i<code.length();i++) {
            char ch = code.charAt(i);
            if (Character.isWhitespace(ch) ) {
                String mycode = code.substring(0, i) + comment + code.substring(i);
                assertTrue(parseString(mycode));
            }
        }

        // end of line comment
        assertTrue(parseString(code + "// this is a comment"));
        assertTrue(parseString(code + "// -->  /* embedded */"));
        assertTrue(parseString(code + "// -->  /* embedded */\n"));
        assertTrue(parseString(code + "// -->  /* embedded */\r\n"));

    }

}
