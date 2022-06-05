package com.yurirenko.jlox;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ScannerTest {
    private static final String inputString = """
            fn fibonacci(n) {
                if (n == 0) {
                    return 0;
                }
                if (n == 1) {
                    return 1;
                }
                       
                return fibonacci(n - 1) + fibonacci(n - 2);
            }
            """;
    private static final ArrayList<Token> expectedTokens = new ArrayList<>();

    static {
        expectedTokens.add(new Token(TokenType.FN, "fn", null, 1));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, 1));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 1));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, 1));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 1));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, 1));

        expectedTokens.add(new Token(TokenType.IF, "if", null, 2));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 2));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, 2));
        expectedTokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, 2));
        expectedTokens.add(new Token(TokenType.NUMBER, "0", 0.0, 2));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 2));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, 2));
        expectedTokens.add(new Token(TokenType.RETURN, "return", null, 3));
        expectedTokens.add(new Token(TokenType.NUMBER, "0", 0.0, 3));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, 3));
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, 4));

        expectedTokens.add(new Token(TokenType.IF, "if", null, 5));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 5));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, 5));
        expectedTokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, 5));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, 5));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 5));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, 5));
        expectedTokens.add(new Token(TokenType.RETURN, "return", null, 6));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, 6));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, 6));
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, 7));

        expectedTokens.add(new Token(TokenType.RETURN, "return", null, 9));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, 9));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 9));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, 9));
        expectedTokens.add(new Token(TokenType.MINUS, "-", null, 9));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, 9));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 9));
        expectedTokens.add(new Token(TokenType.PLUS, "+", null, 9));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, 9));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 9));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, 9));
        expectedTokens.add(new Token(TokenType.MINUS, "-", null, 9));
        expectedTokens.add(new Token(TokenType.NUMBER, "2", 2.0, 9));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 9));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, 9));
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, 10));
        expectedTokens.add(new Token(TokenType.EOF, "", null, 11));
    }

    @Test
    void scanTokens() {
        List<Token> tokens = new Scanner(inputString).scanTokens();
        assertArrayEquals(expectedTokens.toArray(), tokens.toArray());
    }
}
