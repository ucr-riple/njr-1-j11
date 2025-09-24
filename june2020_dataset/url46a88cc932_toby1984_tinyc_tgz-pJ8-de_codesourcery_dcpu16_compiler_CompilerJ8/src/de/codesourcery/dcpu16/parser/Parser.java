package de.codesourcery.dcpu16.parser;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.codesourcery.dcpu16.ast.AST;
import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.ArrayInitializer;
import de.codesourcery.dcpu16.ast.Block;
import de.codesourcery.dcpu16.ast.DoLoopNode;
import de.codesourcery.dcpu16.ast.ForLoopNode;
import de.codesourcery.dcpu16.ast.FunctionDeclarationNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.FunctionInvocation;
import de.codesourcery.dcpu16.ast.IfNode;
import de.codesourcery.dcpu16.ast.InlineAssemblyNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.ParameterDeclaration;
import de.codesourcery.dcpu16.ast.ReturnNode;
import de.codesourcery.dcpu16.ast.StatementNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.ast.VariableDefinition;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.ast.WhileLoopNode;
import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.Operator;
import de.codesourcery.dcpu16.compiler.Scope;
import de.codesourcery.dcpu16.exceptions.ParseException;
import de.codesourcery.dcpu16.parser.ExpressionToken.ExpressionTokenType;
import de.codesourcery.dcpu16.parser.ShuntingYard.MismatchedClosingParensException;
import de.codesourcery.dcpu16.util.IInputStreamProvider;
import de.codesourcery.dcpu16.util.ITextRegion;
import de.codesourcery.dcpu16.util.TextRegion;

public class Parser
{
    // used to generate unique identifiers for local blocks (inside methods, like while() / for() loops etc) 
    private int localBlockCounter = 0;

    private final IInputStreamProvider inputProvider;
    private final Lexer lexer;

    private AST ast;

    private ASTNode lastResult;

    public Parser(IInputStreamProvider inProvider) throws IOException 
    {
        this.inputProvider=inProvider;
        this.lexer = new Lexer(inProvider,true);
    }

    public AST parse() throws IOException {

        if ( ast != null ) {
            return ast;
        }

        final AST result = new AST();
        while ( ! eof() ) 
        {
            if ( ! parseStatement( result.getGlobalScope() ).parse() ) {
                break;
            }
            result.addChild( lastResult );
        }
        ast = result;
        return result;
    }

    private boolean eof() {
        return lexer.eof();
    }

    private ParserImpl parseStatement(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseStatement";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                if ( eof() ) {
                    return null;
                }

                if ( parseFunctionDeclaration( scope ).parse() ) 
                {
                    final FunctionDeclarationNode fn = (FunctionDeclarationNode) lastResult;
                    scope.declareFunction( fn );
                    return new StatementNode( fn );
                } 
                else if ( parseFunctionDefinition( scope ).parse() )
                {
                    final FunctionDefinitionNode fn = (FunctionDefinitionNode) lastResult;
                    scope.defineFunction( fn );
                    return new StatementNode( fn );
                } 
                else if ( parseVariableDefinition( scope ).parse() ) 
                {
                    StatementNode result = new StatementNode( lastResult );
                    result.merge( lexer.read( TokenType.SEMICOLON ) );
                    return result;
                }                
                return null;
            }

        };
    }

    private void mark() {
        lexer.mark();
    }

    private void reset() throws IOException {
        lexer.reset();
    }    

    private void dropMark() {
        lexer.dropMark();
    }      

    private void printError(Exception e) throws EOFException, IOException 
    {
        try {
            printErrorQuietly(e);
        } 
        catch(Exception e2) {
            System.err.println("ERROR: "+e+" ("+e2.getMessage()+")");
            e.printStackTrace();
        }
    }

    private void printErrorQuietly(Exception e) throws EOFException, IOException {

        final ITextRegion offset;
        if ( e instanceof ParseException ) {
            offset = ((ParseException) e).getRegion();
        } 
        else 
        {
            offset = new TextRegion(lexer.currentOffset(),0);
        }

        final StringBuffer src = new StringBuffer();
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader( this.inputProvider.createInputStream() ) ) ) 
        {
            // advance to error offset
            int i = offset.getStartingOffset();
            while ( i > 0 ) {
                if ( reader.read() == -1 ) {
                    throw new EOFException("Bad offset "+offset.getStartingOffset());
                }
                i--;
            }

            // get 20 characters of context
            i = 20;
            while( i > 0 ) 
            {
                int val = reader.read();
                if ( val == -1 || ( ( (char) val) == '\n' ) ) {
                    break;
                }
                src.append( (char) val);
                i--;
            }
            System.out.println( src );
            int len = offset.getLength() == 0 ? 1 : offset.getLength();
            for ( i = 0 ; i < len ; i++ ) {
                System.out.print("^");
            }
            System.err.println( " "+e.getMessage() );
            e.printStackTrace();
        }
    }

    protected abstract class ParserImpl 
    {
        public boolean parse() throws IOException 
        {
            mark();
            boolean success = false;
            try {
//                System.out.println("Parsing: "+this);
                lastResult  = doParse();
                success = lastResult != null;
                return success;
            } 
            catch(Exception e) 
            {
                printError(e);
                return false;
            } 
            finally 
            {
                if ( ! success ) {
                    reset();
                } else {
                    dropMark();
                }
            }
        }

        protected abstract ASTNode doParse() throws Exception;
    }

    private ParserImpl parseFunctionDefinition(final IScope parentScope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseFunctionDefinition";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                // parse return type
                ITextRegion region = new TextRegion(lexer.peek() );
                DataType returnType = parseDataType( parentScope , region );

                if ( lexer.peek(TokenType.ANGLE_BRACKETS_OPEN ) ) {
                    region.merge(lexer.read());
                    region.merge(lexer.read(TokenType.ANGLE_BRACKETS_CLOSE ) );
                    returnType = DataType.getDataType( returnType.getIdentifier() , true , returnType.getPtrCount() );
                }

                // parse function name
                Token tok = lexer.read(TokenType.IDENTIFIER);

                final Identifier functionName = new Identifier( tok );

                if ( parentScope.isFunctionDefined( functionName) ) {
                    throw new ParseException("Function already defined" , tok.getOffset() );
                }                

                FunctionDefinitionNode result = new FunctionDefinitionNode( functionName , returnType , tok.getTextRegion() );
                final IScope functionScope = createNewScope( functionName , result , parentScope );
                result.setScope( functionScope );

                // parse function parameters
                lexer.read(TokenType.PARENS_OPEN);

                if ( ! lexer.peek( TokenType.PARENS_CLOSE ) ) 
                {
                    do 
                    {
                        if ( ! parseFunctionParameter( functionScope ).parse() ) 
                        {
                            throw new ParseException("Expected parameter declaration",lexer.currentOffset());
                        }
                        ParameterDeclaration decl = (ParameterDeclaration) lastResult;
                        functionScope.defineVariable( decl );

                        result.addChild( lastResult );

                        if ( ! lexer.peek( TokenType.COMMA ) ) {
                            break;
                        } 
                        lexer.read(TokenType.COMMA);
                    } while(true);
                }

                lexer.read(TokenType.PARENS_CLOSE);

                // parse body
                if ( ! parseBlock( functionScope , true ).parse() ) {
                    return null;
                } 
                result.addChild( lastResult );
                return result;
            }
        };
    }

    private DataType parseDataType(IScope scope,ITextRegion region) throws IOException 
    {
        final Token dataTypeId = region.merge( lexer.read(TokenType.IDENTIFIER) );

        int ptrCount = 0;		
        while( lexer.peek(TokenType.STAR ) ) {
            ptrCount++;
            region.merge( lexer.read() );
        }

        return new DataType( new Identifier( dataTypeId ) , false , ptrCount);
    }

    private ParserImpl parseFunctionDeclaration(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseFunctionDeclaration";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                boolean isExtern = false;

                ITextRegion region = new TextRegion(lexer.peek());
                if ( lexer.peek(TokenType.EXTERN ) ) 
                {
                    region.merge( lexer.read() );
                    isExtern = true;
                }

                // parse return type
                DataType returnType = parseDataType( scope , region );

                if ( lexer.peek(TokenType.ANGLE_BRACKETS_OPEN ) ) {
                    region.merge(lexer.read());
                    region.merge(lexer.read(TokenType.ANGLE_BRACKETS_CLOSE ) );
                    returnType = DataType.getDataType( returnType.getIdentifier() , true , returnType.getPtrCount() );
                }				

                // parse function name
                Token tok = lexer.read(TokenType.IDENTIFIER);

                final Identifier functionName = new Identifier( tok );

                if ( scope.isFunctionDefined( functionName) ) {
                    throw new ParseException("Function already defined" , tok.getOffset() );
                }                

                FunctionDeclarationNode result = new FunctionDeclarationNode( scope , functionName , isExtern, returnType , tok.getTextRegion() );

                // parse function parameters
                lexer.read(TokenType.PARENS_OPEN);

                if ( ! lexer.peek( TokenType.PARENS_CLOSE ) ) 
                {
                    do 
                    {
                        if ( ! parseFunctionParameter( result.getScope() ).parse() ) 
                        {
                            throw new ParseException("Expected parameter declaration",lexer.currentOffset());
                        }
                        ParameterDeclaration decl = (ParameterDeclaration) lastResult;
                        result.getScope().defineVariable( decl );

                        result.addChild( lastResult );

                        if ( ! lexer.peek( TokenType.COMMA ) ) {
                            break;
                        } 
                        lexer.read(TokenType.COMMA);
                    } while(true);
                }

                lexer.read(TokenType.PARENS_CLOSE);

                lexer.read(TokenType.SEMICOLON);
                result.addChild( lastResult );
                return result;
            }
        };
    }	

    private ParserImpl parseInlineAssembly(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseInlineAssembly";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                final StringBuffer buffer = new StringBuffer();

                final ITextRegion region = new TextRegion( lexer.read(TokenType.ASM) );
                region.merge( lexer.read(TokenType.BRACE_OPEN) );

                lexer.setIgnoreWhitespace( false );
                try 
                {
                	boolean escaped=false;
                	do 
                	{
                		if ( !escaped && lexer.peek(TokenType.ESCAPE_CHARACTER ) ) 
                		{
                			region.merge( lexer.read() );
                			escaped = true;
                		}
                		if ( !escaped && lexer.peek(TokenType.BRACE_CLOSE ) ) {
                			break;
                		}
                		escaped = false;
                        Token tok = region.merge( lexer.read() );
                        buffer.append( tok.getValue() );
                    } while(true);
                } finally {
                    lexer.setIgnoreWhitespace( true );
                }
                region.merge( lexer.read(TokenType.BRACE_CLOSE) );
                return new InlineAssemblyNode( removeTrailingSpaces( buffer.toString() ) , region );
            }
        };
    }

    // removes 
    private static String removeTrailingSpaces(String input) {
        int lastEOL = -1;
        for ( int i = 0 ; i < input.length() ; i++ ) {
            final char c = input.charAt(i);
            if ( c == '\n' ) {
                lastEOL = i;
            }
        }
        if ( lastEOL == -1 || lastEOL == input.length() -1 ) {
            return input;
        }
        final String part1 = input.substring( 0 , lastEOL+1);
        StringBuffer part2 = new StringBuffer( input.substring( lastEOL+1 , input.length() ) );
        part2.reverse();
        while ( part2.length() > 0 && Character.isWhitespace( part2.charAt(0) ) ) {
            part2.deleteCharAt( 0 );
        }
        return part1 + part2.reverse();
    }



    private ParserImpl parseBlock(final IScope parentScope,final boolean variableDefinitionsAllowed) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseBlock";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                lexer.read(TokenType.BRACE_OPEN);

                final Block result = new Block();
                final IScope blockScope = createNewScope("block_", result, parentScope);
                result.setScope(blockScope);

                while ( ! lexer.peek( TokenType.BRACE_CLOSE ) ) 
                {
                    Token tok = lexer.peek();

                    // variable definition
                    if ( tok.hasType( TokenType.IDENTIFIER ) && parentScope.isDataTypeDefined( new Identifier( tok.getValue() ) ) ) 
                    {
                        if ( ! variableDefinitionsAllowed ) {
                            break;
                        }

                        if ( parseVariableDefinition( blockScope ).parse() ) {
                            result.merge( lexer.read( TokenType.SEMICOLON ) );
                            result.addChild( statement( lastResult ) );
                            continue;
                        }
                        break;
                    }

                    // for statement
                    if ( tok.hasType(TokenType.FOR ) && parseForLoop(blockScope).parse() ) {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }

                    // do statement
                    if ( tok.hasType(TokenType.DO ) && parseDo(blockScope).parse() ) {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }
                    // while statement
                    if ( tok.hasType(TokenType.WHILE ) && parseWhile(blockScope).parse() ) 
                    {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }  

                    // if statement
                    if ( tok.hasType(TokenType.IF ) && parseIf(blockScope).parse() ) 
                    {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }                    

                    // return statement
                    if ( tok.hasType(TokenType.RETURN ) && parseReturn(blockScope).parse() ) 
                    {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }

                    // inline assembly
                    if ( tok.hasType( TokenType.ASM ) && parseInlineAssembly(blockScope).parse() ) {
                        result.addChild( statement( lastResult ) );
                        continue;
                    }

                    // expression (function invocation )
                    if ( parseExpression( blockScope ).parse() ) 
                    {
                        lexer.read(TokenType.SEMICOLON);                        
                        result.addChild( statement( lastResult ) );
                        continue;
                    } 
                    break;
                }
                lexer.read(TokenType.BRACE_CLOSE);                
                return result;
            }
        };
    }
    
    private StatementNode statement(ASTNode node) {
        return new StatementNode(node);
    }

    private ParserImpl parseIf(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseIF";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                IfNode result = new IfNode( lexer.read(TokenType.IF) );

                result.merge( lexer.read( TokenType.PARENS_OPEN ) );

                if ( ! parseExpression( scope , false ).parse() ) 
                {
                    return null;
                }

                // add condition
                result.addChild( lastResult );

                result.merge( lexer.read( TokenType.PARENS_CLOSE ) );

                // parse body
                if ( ! parseBlock( scope , true ).parse() ) {
                    return null;
                }
                result.addChild( lastResult );

                if ( lexer.peek(TokenType.ELSE ) ) 
                {
                    result.merge( lexer.read(TokenType.ELSE ) );
                    if ( lexer.peek(TokenType.IF ) ) { // else/if
                        if ( ! parseIf( scope ).parse() ) {
                            return null;
                        }
                        result.addChild( lastResult );
                    } 
                    else 
                    {
                        if ( ! parseBlock( scope , true ).parse() ) {
                            return null;
                        }
                        result.addChild( lastResult );                        
                    }
                }
                return result;
            }
        };
    }        

    private ParserImpl parseWhile(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseWhile";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                WhileLoopNode result = new WhileLoopNode( lexer.read(TokenType.WHILE) );

                result.merge( lexer.read( TokenType.PARENS_OPEN ) );

                if ( ! parseExpression( scope , false ).parse() ) 
                {
                    return null;
                }

                result.addChild( lastResult );

                result.merge( lexer.read( TokenType.PARENS_CLOSE ) );

                if ( lexer.peek( TokenType.BRACE_OPEN ) ) 
                {
                    // parse body
                    if ( ! parseBlock( scope , true ).parse() ) {
                        return null;
                    }
                    result.addChild( lastResult );
                } else {
                    result.merge( lexer.read(TokenType.SEMICOLON ) );
                }
                return result;
            }
        };
    }    

    private IScope createNewScope(String prefix , ASTNode definitionSite , IScope parent) 
    {
        final IScope result = createNewScope( new Identifier( prefix +localBlockCounter) , definitionSite , parent );
        localBlockCounter++;
        return result;
    }

    private IScope createNewScope(Identifier identifier, ASTNode definitionSite , IScope parent) 
    {
        return new Scope( identifier , definitionSite , parent );
    }	

    private ParserImpl parseDo(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseDo";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                DoLoopNode result = new DoLoopNode( lexer.read(TokenType.DO) );

                if ( ! parseBlock( scope , true ).parse() ) {
                    return null;
                }

                result.addChild( lastResult );

                result.merge( lexer.read(TokenType.WHILE ) );

                result.merge( lexer.read( TokenType.PARENS_OPEN ) );

                if ( ! parseExpression( scope , false ).parse() ) 
                {
                    return null;
                }

                result.addChild( lastResult );

                result.merge( lexer.read( TokenType.PARENS_CLOSE ) );

                result.merge( lexer.read(TokenType.SEMICOLON ) );
                return result;
            }
        };
    }  	

    private ParserImpl parseForLoop(final IScope currentScope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseForLoop";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                final ForLoopNode result = new ForLoopNode( lexer.read(TokenType.FOR) );
                final IScope forScope = createNewScope( "for_loop_" , result , currentScope );
                result.setScope( forScope );

                result.merge( lexer.read(TokenType.PARENS_OPEN ) );

                if ( ! lexer.peek(TokenType.SEMICOLON ) ) 
                {
                    // parse initializer expressions
                    if ( ! parseExpressionList(forScope,true,true).parse() ) {
                        return null;
                    }
                    result.setInitializerBlock( (Block) lastResult );
                }
                result.merge( lexer.read(TokenType.SEMICOLON ) );

                // parse condition
                if ( ! lexer.peek(TokenType.SEMICOLON ) ) 
                {
                    if ( ! parseExpression(forScope, true, false ).parse() ) {
                        return null;
                    }
                    result.setCondition( (TermNode) lastResult );
                } else {
                    result.setCondition( new NumberLiteralNode( 1 , new TextRegion( lexer.currentOffset() , 0 ) ) );
                }

                result.merge( lexer.read(TokenType.SEMICOLON ) );

                if ( ! lexer.peek(TokenType.PARENS_CLOSE ) ) 
                {
                    // parse increment expressions
                    if ( ! parseExpressionList(forScope,false,false).parse() ) {
                        return null;
                    }					
                    result.setIncrementBlock( (Block) lastResult );					
                }

                result.merge( lexer.read(TokenType.PARENS_CLOSE ) );

                if ( lexer.peek(TokenType.BRACE_OPEN ) ) // parse body 
                {
                    if ( ! parseBlock(forScope,true).parse() ) {
                        return null;
                    }
                    Block bodyBlock = (Block) lastResult;
                    result.setBody( bodyBlock );
                } 
                else {
                    result.merge( lexer.read( TokenType.SEMICOLON ) );
                }
                return result;
            }
        };
    }  	

    private ParserImpl parseExpressionList(final IScope blockScope,final boolean variableDefinitionsAllowed,final boolean failOnMismatchedClosingParens) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseExpressionList";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                final Block result = new Block();
                result.setScope(blockScope);

                Token previousComma = null;
                while ( ! lexer.peek( TokenType.SEMICOLON ) ) 
                {
                    Token tok = lexer.peek();

                    // variable definition
                    boolean gotSomething = false;
                    if ( tok.hasType( TokenType.IDENTIFIER ) && 
                            blockScope.isDataTypeDefined( new Identifier( tok.getValue() ) ) ) 
                    {
                        if ( ! variableDefinitionsAllowed ) {
                            break;
                        }
                        if ( ! parseVariableDefinition( blockScope ).parse() ) {
                            return null;
                        }
                        result.addChild( lastResult );
                        gotSomething = true;
                    } 
                    else if ( parseExpression( blockScope , failOnMismatchedClosingParens ).parse() ) 
                    {
                        result.addChild( lastResult );
                        gotSomething = true;
                    } 

                    if ( ! gotSomething && previousComma != null ) {
                        throw new ParseException("Expression expected after comma" , previousComma );
                    }

                    if ( lexer.peek(TokenType.COMMA ) ) 
                    {
                        previousComma = lexer.read();
                        result.merge( previousComma );
                        continue;
                    }
                    break;
                }
                return result;
            }
        };
    }	

    private ParserImpl parseReturn(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseReturn";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                ReturnNode result = new ReturnNode( lexer.read(TokenType.RETURN).getTextRegion() );
                if ( ! lexer.peek(TokenType.SEMICOLON ) ) 
                {
                    if ( ! parseExpression( scope ).parse() ) 
                    {
                        return null;
                    }
                    result.addChild( lastResult );
                }
                lexer.read(TokenType.SEMICOLON);
                return result;
            }
        };
    }

    private ParserImpl parseVariableDefinition(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseVariableDefinition";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                boolean isImmutable = false;
                TextRegion r = new TextRegion(lexer.peek());
                if ( lexer.peek(TokenType.CONST ) ) {
                    r.merge( lexer.read() );
                    isImmutable = true;
                }

                DataType type = parseDataType( scope , r );

                boolean isArray = false;
                boolean gotArraySize = false;
                int elementCount = 1;

                final List<VariableDefinition> definitions = new ArrayList<>();
                final Block block = new Block();
                block.setScope( scope );

                int definitionNumber = -1;
                do 
                {
                    definitionNumber++;

                    final Identifier varName = new Identifier( lexer.read(TokenType.IDENTIFIER ) );

                    if ( definitionNumber == 0 && ! isArray && lexer.peek(TokenType.ANGLE_BRACKETS_OPEN ) ) 
                    {
                        r.merge( lexer.read() );
                        if ( lexer.peek(TokenType.NUMBER_LITERAL ) ) 
                        {
                            gotArraySize = true;
                            // TODO: Support expressions for specifying array sizes
                            elementCount = Integer.parseInt( lexer.read( TokenType.NUMBER_LITERAL ).getValue() );							
                        }
                        r.merge( lexer.read(TokenType.ANGLE_BRACKETS_CLOSE ) );
                        isArray = true;
                        type = DataType.getDataType( type.getIdentifier() , true , type.getPtrCount() );
                    }

                    final VariableDefinition def = new VariableDefinition( scope , varName , type , elementCount , r , isImmutable );
                    
                    Token operator = lexer.peek();
                    if ( ! operator.hasType(TokenType.SEMICOLON) && ( operator.hasType(TokenType.OPERATOR ) || 
                            (isArray && ! gotArraySize ) ) ) 
                    {
                        operator = lexer.read(TokenType.OPERATOR);
                        final Operator op = Operator.parseOperator( operator.getValue() );
                        if ( op != Operator.ASSIGNMENT ) 
                        {
                            return null;
                        }

                        if ( isArray ) // parse array definition = { 1,2,3, }
                        {
                            if ( ! lexer.peek(TokenType.BRACE_OPEN ) ) 
                            {
                                // special case: char x[] = "test";
                                if ( ! type.getBaseType().equals( DataType.CHAR ) ) 
                                {
                                    throw new ParseException("Array initialization requires {",lexer.peek() );
                                }
                                
                                final StringLiteralNode stringLiteral = parseStringLiteral();
                                def.addChild( stringLiteral );
                                gotArraySize = true;
                                elementCount = stringLiteral.getValue().length();
                            } 
                            else 
                            {
                            	// array initialization 
                            	// int x[] = { 1,2,3};
                            	ArrayInitializer initializer = new ArrayInitializer(lexer.read( TokenType.BRACE_OPEN ).getTextRegion());
                                int definitionCount = 0;
                                if ( ! lexer.peek().hasType( TokenType.BRACE_CLOSE ) ) 
                                {
                                    do {
                                        if ( ! parseExpression(scope).parse() ) {
                                            return null;
                                        }
                                        initializer.addChild( lastResult );
                                        definitionCount++;

                                        if ( ! lexer.peek(TokenType.COMMA ) ) {
                                            break;
                                        }
                                        lexer.read(TokenType.COMMA);
                                    } while ( ! lexer.peek(TokenType.BRACE_CLOSE ) );
                                }
                                lexer.read( TokenType.BRACE_CLOSE );
                                
                                if ( elementCount != definitionCount ) 
                                {
                                    throw new ParseException("Array has size "+elementCount+" but "+definitionCount+" elements are initialized ?",lexer.currentOffset());
                                }
                                def.addChild( initializer );
                            }
                        } 
                        else 
                        {
                            if ( ! parseExpression( scope , true , true ).parse() ) { // parse regular variable definition 
                                return null;
                            }
                            def.addChild(lastResult); 	
                        }
                    } 

                    scope.defineVariable( def );
                    definitions.add( def );

                    if ( ! lexer.peek(TokenType.COMMA) ) {
                        break;
                    } 
                    block.merge( lexer.read(TokenType.COMMA) );
                } while ( true );

                if ( definitions.size() == 1 ) {
                    return definitions.get(0);
                }
                block.addChildren( definitions );
                return block;
            }
        };
    }

    private ParserImpl parseExpression(IScope scope) throws IOException {
        return parseExpression(scope,true,false);
    }

    private ParserImpl parseExpression(final IScope scope,final boolean failOnMismatchedClosingParens) throws IOException {
        return parseExpression(scope,failOnMismatchedClosingParens,false);
    }

    private ParserImpl parseExpression(final IScope scope,final boolean failOnMismatchedClosingParens,final boolean stopOnComma) throws IOException
    {
        return new ParserImpl() 
        {
            @Override
            public String toString()
            {
                return "parseExpression";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                ShuntingYard yard = new ShuntingYard();

                Token previousToken = null;
                Token previousToken2 = null;
                while( ! lexer.eof() ) 
                {
                    previousToken = previousToken2;
                    Token tok = lexer.peek();
                    previousToken2 = tok;

                    if ( tok.hasType(TokenType.SEMICOLON ) ) 
                    {
                        break;
                    } 
                    else if ( tok.hasType(TokenType.PARENS_OPEN ) ) 
                    {
                        tok = lexer.read();
                        yard.pushOperator( new ExpressionToken(ExpressionTokenType.PARENS_OPEN,tok) );
                    } 
                    else if ( tok.hasType(TokenType.PARENS_CLOSE ) ) 
                    {
                        lexer.mark();
                        tok = lexer.read();
                        boolean success = false;
                        try {
                            yard.pushOperator( new ExpressionToken(ExpressionTokenType.PARENS_CLOSE,tok) );
                            success = true;
                        } 
                        catch(MismatchedClosingParensException ex) 
                        {
                            if ( failOnMismatchedClosingParens ) {
                                throw ex;
                            }
                            break;
                        } finally {
                            if ( ! success ) {
                                lexer.reset();
                            } else {
                                lexer.dropMark();
                            }
                        }
                    } 
                    else if ( tok.hasType(TokenType.COMMA ) && ! stopOnComma ) 
                    {
                        lexer.read();
                        yard.pushOperator( new ExpressionToken(ExpressionTokenType.ARGUMENT_DELIMITER,tok) );
                    } 
                    else if ( tok.hasType( TokenType.IDENTIFIER ) ) 
                    {
                        // either a variable identifier OR
                        // the start of a function invocation
                        tok = lexer.read(TokenType.IDENTIFIER);
                        if ( ! lexer.eof() && lexer.peek(TokenType.PARENS_OPEN ) ) 
                        {
                            // function invocation
                            FunctionInvocation fn = new FunctionInvocation( new Identifier( tok.getValue() ) , tok );
                            yard.pushOperator( new ExpressionToken( ExpressionTokenType.FUNCTION , fn ) );
                        } else {
                            // variable identifier
                            yard.pushValue( new VariableReferenceNode( new Identifier( tok ) , scope , tok.getTextRegion() ) );
                        }
                    } 
                    else if ( tok.hasType(TokenType.DOUBLE_QUOTE ) || tok.hasType(TokenType.SINGLE_QUOTE ) ) 
                    {
                        yard.pushValue( parseStringLiteral() );
                    } 
                    else if ( tok.hasType( TokenType.TRUE )  ) 
                    {
                        yard.pushValue( new NumberLiteralNode(1,lexer.read().getTextRegion() ) );                        
                    }    
                    else if ( tok.hasType( TokenType.FALSE )  ) 
                    {
                        yard.pushValue( new NumberLiteralNode(0,lexer.read().getTextRegion() ) );
                    }                        
                    else if ( tok.hasType( TokenType.NUMBER_LITERAL ) ) 
                    {
                        yard.pushValue( parseNumber() );
                    } 
                    else if ( tok.hasType( TokenType.AMPERSAND) ) 
                    {
                        lexer.read();
                        final Operator op;
                        if ( ! lexer.eof() && lexer.peek(TokenType.AMPERSAND ) ) { // && => logical and
                            lexer.read();							
                            op = Operator.LOGICAL_AND;
                        } 
                        else if ( previousToken == null || previousToken.hasType(TokenType.PARENS_OPEN ) || isOperator(previousToken) ) 
                        {
                            op = Operator.ADDRESS_OF;
                        } 
                        else 
                        {
                            op = Operator.BITWISE_AND;
                        }
                        OperatorNode node = new OperatorNode(op,tok);
                        yard.pushOperator( new ExpressionToken( ExpressionTokenType.OPERATOR , node ) );
                    } 		
                    else if ( tok.hasType( TokenType.ANGLE_BRACKETS_OPEN ) && previousToken != null && previousToken.hasType( TokenType.IDENTIFIER ) ) 
                    {
                        lexer.read();
                        if ( ! parseExpression( scope , failOnMismatchedClosingParens , stopOnComma ).parse() ) {
                            return null;
                        }
                        lexer.read(TokenType.ANGLE_BRACKETS_CLOSE);
                        
                        final OperatorNode node = new OperatorNode(Operator.ARRAY_SUBSCRIPT,tok);
                        yard.pushOperator( new ExpressionToken( ExpressionTokenType.OPERATOR , node ) );
                        yard.pushValue( lastResult );
                    } 					
                    else if ( tok.hasType( TokenType.STAR ) ) 
                    {
                        final Operator op;
                        if ( previousToken == null || previousToken.hasType(TokenType.PARENS_OPEN ) || isOperator(previousToken) ) 
                        {
                            lexer.read();
                            op = Operator.DEREFERENCE;
                        } 
                        else 
                        {
                            lexer.read();
                            op = Operator.MULTIPLY;
                        }
                        OperatorNode node = new OperatorNode(op,tok);
                        yard.pushOperator( new ExpressionToken( ExpressionTokenType.OPERATOR , node ) );
                    } 
                    else if ( tok.hasType( TokenType.OPERATOR ) ) 
                    {
                        yard.pushOperator( new ExpressionToken( ExpressionTokenType.OPERATOR , parseOperator() ) );
                    }
                    else {
                        break;
                    }
                }

                return yard.getResult( lexer.currentOffset() );
            }
        };
    }    

    private static boolean isOperator(Token tok) {
        return Operator.isValidOperator( tok.getValue() );
    }

    private StringLiteralNode parseStringLiteral() throws IOException
    {
        final Token delimiter;
        if ( lexer.peek(TokenType.SINGLE_QUOTE ) )
        {
            delimiter = lexer.read();	
        } else {
            delimiter = lexer.read(TokenType.DOUBLE_QUOTE); 
        }

        TextRegion r = new TextRegion(delimiter.getTextRegion());
        StringBuilder s = new StringBuilder();
        while ( ! lexer.eof() && ! lexer.peek( delimiter.getType() ) ) 
        {
            Token tok = lexer.read(); 
            r.merge( tok.getTextRegion() );
            s.append( tok.getValue() );
        }
        r.merge( lexer.read( delimiter.getType() ) );

        return new StringLiteralNode( s.toString() ,  delimiter.hasType( TokenType.SINGLE_QUOTE ) , r );
    }

    private NumberLiteralNode parseNumber() throws IOException 
    {
        final Token token = lexer.read(TokenType.NUMBER_LITERAL);
        return new NumberLiteralNode( NumberLiteralNode.parse( token.getValue() ) , token );
    }

    private OperatorNode parseOperator() throws IOException 
    {
        final Token token = lexer.read(TokenType.OPERATOR);
        return new OperatorNode( Operator.parseOperator( token.getValue() ) , token );
    }    

    private ParserImpl parseFunctionParameter(final IScope scope) throws IOException
    {
        return new ParserImpl() {

            @Override
            public String toString()
            {
                return "parseFunctionParameter";
            }

            @Override
            protected ASTNode doParse() throws Exception
            {
                boolean isImmutable = false;
                TextRegion region = new TextRegion(lexer.peek());
                if ( lexer.peek(TokenType.CONST ) ) {
                    region.merge( lexer.read() );
                    isImmutable = true;
                }

                // const int **blubb[]
                DataType dataType = parseDataType( scope , region );

                Token tok = region.merge( lexer.read(TokenType.IDENTIFIER) );
                final Identifier varName = new Identifier( tok );

                if ( lexer.peek(TokenType.ANGLE_BRACKETS_OPEN ) ) {
                    region.merge( lexer.read() );
                    region.merge( lexer.read(TokenType.ANGLE_BRACKETS_CLOSE) );
                    dataType = DataType.getDataType( dataType.getIdentifier() , true , dataType.getPtrCount() );
                }				

                return new ParameterDeclaration( varName , dataType , scope , region , isImmutable );
            }
        };
    } 
}