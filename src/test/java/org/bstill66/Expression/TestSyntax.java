package org.bstill66.Expression;


import org.antlr.v4.runtime.*;
import org.junit.jupiter.api.Test;


import static org.bstill66.Expression.TestUtil.parseString;
import static org.junit.jupiter.api.Assertions.*;

public class TestSyntax {



    @Test
    public void ValidateList() {
        assertNotNull(parseString("var in []"));
        assertNotNull(parseString("var not in []"));

        assertNotNull(parseString("var in [4,5,6,\"Brian\"]"));
        assertNotNull(parseString("var not in [\"Brian\",4,3]"));

        // Failure cases
        assertNull(parseString("var in y"));
        assertNull(parseString("var not in x"));
        assertNull(parseString(""));
    }

    @Test
    public void validateEquality() {
        assertNotNull(parseString("x=5"));
        assertNotNull(parseString("x = \"Brian\""));
        assertNotNull(parseString("xyz = +4345"));
        assertNotNull(parseString("xyz = -4345"));
        assertNotNull(parseString("xyz = \"Helloworld\""));


        assertNull(parseString("x = y"));
        assertNull(parseString("5 = 6"));
        //assertFalse(parseString("x = +-43"));

    }

    @Test
    public void validateEidr() {
        // all caps
        assertNotNull(parseString("x = E\"10.240/AAAA-BBBB-CCCC-DDDD-K\""));
        // lower case
        assertNotNull(parseString("x=E\"10.240/aaaa-bbbb-cccc-dddd-J\""));

        // Mixed case
        assertNotNull(parseString("x=E\"10.240/AaAa-BbBb-CcCd-DdDd-K\""));

        // without prefix
        assertNotNull(parseString("x=E\"/AaAa-BbBb-CcCd-DdDd-K\""));

        // without /
        assertNotNull(parseString("x=E\"AaAa-BbBb-CcCd-DdDd-K\""));
    }

    @Test
    public void validateString() {

        // Empty String
        assertNotNull(parseString("x = \"\""));
        assertNotNull(parseString("x = \"basic\""));
        assertNotNull(parseString("x=\"Really long string............tooo long\""));
        assertNotNull(parseString("x=\"/* comment */x = 4\""));

        assertNotNull(parseString("x ~= \"\""));
        assertNotNull(parseString("x ~= \"some string\""));

    }

    @Test
    public void validateNumber() {
        final String[] good = {"0","000","043","999","-999","-0", "+0","+999"};
        final String[] bad  = {"+s","04A","0A4","_44"};
        final String codeTmpl = "var = %s";

        for (String g : good) {
            String code = String.format(codeTmpl,g);
            assertNotNull(parseString(code));
        }

        for (String b: bad) {
            String code = String.format(codeTmpl,b);
            assertNull(parseString(code));
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
                assertNotNull(parseString(code));
            }
        }

        // Invalid Variables
        final String[] invalid = {"2x","xy!","xy_$"};
        for (String inv : invalid) {
            String code = String.format("%s = 23",inv);
            assertNull(parseString(code));
        }

    }

    @Test
    public void validateComment() {
        // make sure no whitespace in the string
        final String code = "xyz\t \t=\t \t\"abajsdfklaj;ldkfj;acdef\"";
        final String comment = "/* This is a comment */";

        assertNotNull(parseString(code));
        assertNotNull(parseString(comment + code));
        assertNotNull(parseString(code + comment));

        for (int i=1;i<code.length();i++) {
            char ch = code.charAt(i);
            if (Character.isWhitespace(ch) ) {
                String mycode = code.substring(0, i) + comment + code.substring(i);
                assertNotNull(parseString(mycode));
            }
        }

        // end of line comment
        assertNotNull(parseString(code + "// this is a comment"));
        assertNotNull(parseString(code + "// -->  /* embedded */"));
        assertNotNull(parseString(code + "// -->  /* embedded */\n"));
        assertNotNull(parseString(code + "// -->  /* embedded */\r\n"));

    }

}
