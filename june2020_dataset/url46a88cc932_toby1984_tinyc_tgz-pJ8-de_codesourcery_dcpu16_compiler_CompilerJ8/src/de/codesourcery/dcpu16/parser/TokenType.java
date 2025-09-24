package de.codesourcery.dcpu16.parser;

public enum TokenType
{
    IDENTIFIER,  //
    NUMBER_LITERAL,      // 1234
    PARENS_OPEN, // (
    PARENS_CLOSE,// )
    COMMA,       // ,
    STAR, // *
    ANGLE_BRACKETS_OPEN,  // [
    ANGLE_BRACKETS_CLOSE, // ]    
    BRACE_OPEN,  // {
    BRACE_CLOSE, // }
    DOUBLE_QUOTE, // "
    SINGLE_QUOTE, // '
    ESCAPE_CHARACTER, // \
    AMPERSAND, // &
    OPERATOR,
    FOR, // for keyword
    RETURN, // return keyword
    WHILE, // while keyword    
    IF, // if keyword
    ELSE, // else keyword   
    SEMICOLON,
    EXTERN, // extern keyword
    TRUE, // true
    FALSE, // false
    CONST, // const
    WHITESPACE, // whitespace (ignored by the lexer unless parsing an asm {} block)
    EOL, // end-of-line (ignored by the lexer unless parsing an asm {} block)
    DO, // DO keyword
    ASM, // ASM keyword
    UNKNOWN; // unparsed content
}
