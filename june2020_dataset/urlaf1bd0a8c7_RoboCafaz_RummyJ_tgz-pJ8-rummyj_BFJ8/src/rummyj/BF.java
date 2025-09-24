package rummyj;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.visitors.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BF
{
  private int i = 0;

  private Sequence doParse(String str)
  {
    Sequence seq = new Sequence();
    char c;
    while (i < str.length())
    {
      c = str.charAt(i);
      if (c == '<')
      {
        seq.add(new Left());
      }
      else if (c == '>')
      {
        seq.add(new Right());
      }
      else if (c == '+')
      {
        seq.add(new Increment());
      }
      else if (c == '-')
      {
        seq.add(new Decrement());
      }
      else if (c == '.')
      {
        seq.add(new Output());
      }
      else if (c == ',')
      {
        seq.add(new Input());
      }
      else if (c == ':')
      {
        seq.add(new ProcedureCall());
      }
      else if (c == '[')
      {
        i++;
        seq.add(new Loop(doParse(str)));
      }
      else if (c == '(')
      {
        i++;
        seq.add(new Procedure(doParse(str)));
      }
      else if (Character.isDigit(c))
      {
        String numbers = "" + c;
        for (int j = 1; Character.isDigit(str.charAt(i + j)); j++)
        {
          numbers += str.charAt(i + j);
        }
        i += numbers.length();
        int num = Integer.parseInt(numbers);
        int temp = i;
        i = 0;
        Node node = doParse("" + str.charAt(temp));
        i = temp;
        seq.add(new Number(num, node));
      }
      else if (c == ']')
      {
        return seq;
      }
      else if (c == ')')
      {
        return seq;
      }
      else if (c == '"')
      {
        i++;
        String characters = "";
        while (str.charAt(i) != '"')
        {
          characters += str.charAt(i++);
        }
        seq.add(new Message(characters));
      }
      i++;
    }
    return seq;
  }

  public static Program parse(String str)
  {
    return new Program(new BF().doParse(str));
  }

  public static void main(String[] args)
  {
    System.out.println("-- ASCII --");
    Node ascii = BF.parse("+[.+]");
    ascii.accept(new InterpreterVisitor());
    ascii.accept(new PrintVisitor("ascii"));
    ascii.accept(new JavaVisitor("ascii"));
    ascii.accept(new CSharpVisitor("ascii"));
    ascii.accept(new OptimizationVisitor());
    System.out.println("\n");

    System.out.println("-- HELLO --");
    Node hello = BF
        .parse("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.");
    hello.accept(new InterpreterVisitor());
    hello.accept(new PrintVisitor("hello"));
    hello.accept(new JavaVisitor("hello"));
    hello.accept(new CSharpVisitor("hello"));
    hello.accept(new OptimizationVisitor());
    System.out.println("\n");

    System.out.println("-- BOBBY --");
    Node bobby = BF
        .parse("[-]>[-]<>++++++++[<++++++++>-]<++.>+++++++[<+++++++>-]<----.>++++[<---->-]<+++..>+++++[<+++++>-]<--.>+++++++++[<--------->-]<--------.>++++++[<++++++>-]<-.>+++++[<+++++>-]<+++++.>++[<++>-]<+.>++[<-->-]<-.>+++++[<+++++>-]<..>+++[<--->-]<--.");
    bobby.accept(new InterpreterVisitor());
    bobby.accept(new PrintVisitor("bobby"));
    bobby.accept(new JavaVisitor("bobby"));
    bobby.accept(new CSharpVisitor("bobby"));
    bobby.accept(new OptimizationVisitor());
    System.out.println("\n");

    System.out.println(" -- BOBBY OPTIMIZED (BOBTIMIZED) -- ");
    Node bobbyOp = BF
        .parse("[-]>[-]<>8+[<8+>-]<2+.>7+[<7+>-]<4-.>4+[<4->-]<3+2.>5+[<5+>-]<2-.>9+[<9->-]<8-.>6+[<6+>-]<-.>5+[<5+>-]<5+.>2+[<2+>-]<+.>2+[<2->-]<-.>5+[<5+>-]<2.>3+[<3->-]<2-.");
    bobbyOp.accept(new InterpreterVisitor());
    bobbyOp.accept(new PrintVisitor("bobbyOp"));
    bobbyOp.accept(new JavaVisitor("bobbyOp"));
    bobbyOp.accept(new CSharpVisitor("bobbyOp"));
    bobbyOp.accept(new OptimizationVisitor());

    System.out.println("-- BEER --");
    Node beer = BF
        .parse(">+++++++++[<+++++++++++>-]<[>[-]>[-]<<[>+>+<<-]>>[<<+>>-]>>>[-]<<<+++++++++<[>>>+<<[>+>[-]<<-]>[<+>-]>[<<++++++++++>>>+<-]<<-<-]+++++++++>[<->-]>>+>[<[-]<<+>>>-]>[-]+<<[>+>-<<-]<<<[>>+>+<<<-]>>>[<<<+>>>-]>[<+>-]<<-[>[-]<[-]]>>+<[>[-]<-]<++++++++[<++++++<++++++>>-]>>>[>+>+<<-]>>[<<+>>-]<[<<<<<.>>>>>-]<<<<<<.>>[-]>[-]++++[<++++++++>-]<.>++++[<++++++++>-]<++.>+++++[<+++++++++>-]<.><+++++..--------.-------.>>[>>+>+<<<-]>>>[<<<+>>>-]<[<<<<++++++++++++++.>>>>-]<<<<[-]>++++[<++++++++>-]<.>+++++++++[<+++++++++>-]<--.---------.>+++++++[<---------->-]<.>++++++[<+++++++++++>-]<.+++..+++++++++++++.>++++++++[<---------->-]<--.>+++++++++[<+++++++++>-]<--.-.>++++++++[<---------->-]<++.>++++++++[<++++++++++>-]<++++.------------.---.>+++++++[<---------->-]<+.>++++++++[<+++++++++++>-]<-.>++[<----------->-]<.+++++++++++..>+++++++++[<---------->-]<-----.---.>>>[>+>+<<-]>>[<<+>>-]<[<<<<<.>>>>>-]<<<<<<.>>>++++[<++++++>-]<--.>++++[<++++++++>-]<++.>+++++[<+++++++++>-]<.><+++++..--------.-------.>>[>>+>+<<<-]>>>[<<<+>>>-]<[<<<<++++++++++++++.>>>>-]<<<<[-]>++++[<++++++++>-]<.>+++++++++[<+++++++++>-]<--.---------.>+++++++[<---------->-]<.>++++++[<+++++++++++>-]<.+++..+++++++++++++.>++++++++++[<---------->-]<-.---.>+++++++[<++++++++++>-]<++++.+++++++++++++.++++++++++.------.>+++++++[<---------->-]<+.>++++++++[<++++++++++>-]<-.-.---------.>+++++++[<---------->-]<+.>+++++++[<++++++++++>-]<--.+++++++++++.++++++++.---------.>++++++++[<---------->-]<++.>+++++[<+++++++++++++>-]<.+++++++++++++.----------.>+++++++[<---------->-]<++.>++++++++[<++++++++++>-]<.>+++[<----->-]<.>+++[<++++++>-]<..>+++++++++[<--------->-]<--.>+++++++[<++++++++++>-]<+++.+++++++++++.>++++++++[<----------->-]<++++.>+++++[<+++++++++++++>-]<.>+++[<++++++>-]<-.---.++++++.-------.----------.>++++++++[<----------->-]<+.---.[-]<<<->[-]>[-]<<[>+>+<<-]>>[<<+>>-]>>>[-]<<<+++++++++<[>>>+<<[>+>[-]<<-]>[<+>-]>[<<++++++++++>>>+<-]<<-<-]+++++++++>[<->-]>>+>[<[-]<<+>>>-]>[-]+<<[>+>-<<-]<<<[>>+>+<<<-]>>>[<<<+>>>-]<>>[<+>-]<<-[>[-]<[-]]>>+<[>[-]<-]<++++++++[<++++++<++++++>>-]>>>[>+>+<<-]>>[<<+>>-]<[<<<<<.>>>>>-]<<<<<<.>>[-]>[-]++++[<++++++++>-]<.>++++[<++++++++>-]<++.>+++++[<+++++++++>-]<.><+++++..--------.-------.>>[>>+>+<<<-]>>>[<<<+>>>-]<[<<<<++++++++++++++.>>>>-]<<<<[-]>++++[<++++++++>-]<.>+++++++++[<+++++++++>-]<--.---------.>+++++++[<---------->-]<.>++++++[<+++++++++++>-]<.+++..+++++++++++++.>++++++++[<---------->-]<--.>+++++++++[<+++++++++>-]<--.-.>++++++++[<---------->-]<++.>++++++++[<++++++++++>-]<++++.------------.---.>+++++++[<---------->-]<+.>++++++++[<+++++++++++>-]<-.>++[<----------->-]<.+++++++++++..>+++++++++[<---------->-]<-----.---.+++.---.[-]<<<]");
    beer.accept(new InterpreterVisitor());
    beer.accept(new PrintVisitor("beer"));
    beer.accept(new JavaVisitor("beer"));
    beer.accept(new CSharpVisitor("beer"));
    beer.accept(new OptimizationVisitor());
    System.out.println("\n");

    System.out.println("-- FIBONACCI --");
    Node fibonacci = BF
        .parse("+++++++++++>+>>>>++++++++++++++++++++++++++++++++++++++++++++>++++++++++++++++++++++++++++++++<<<<<<[>[>>>>>>+>+<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-<-[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<-]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<+>>[-]]<<<<<<<]>>>>>[++++++++++++++++++++++++++++++++++++++++++++++++.[-]]++++++++++<[->-<]>++++++++++++++++++++++++++++++++++++++++++++++++.[-]<<<<<<<<<<<<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<[-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>-]<<<-]");
    fibonacci.accept(new InterpreterVisitor());
    fibonacci.accept(new PrintVisitor("fibonacci"));
    fibonacci.accept(new JavaVisitor("fibonacci"));
    fibonacci.accept(new CSharpVisitor("fibonacci"));
    fibonacci.accept(new OptimizationVisitor());
    System.out.println("\n");

    System.out.println("-- RUMEXTREME --");
    Node rumExtreme = BF
        .parse("(++++++++++<[>+>+<<-]>>[<<+>>-])>::::::::::::::<<<<<<<--------.>>>---------.+++++++..>---------.<<<<<<<------.<--------.>>>>>---.>>>.+++.<.--------.<<<<<<<+.");
    rumExtreme.accept(new InterpreterVisitor());
    rumExtreme.accept(new PrintVisitor("rumExtreme"));
    rumExtreme.accept(new JavaVisitor("rumExtreme"));
    rumExtreme.accept(new CSharpVisitor("rumExtreme"));
    rumExtreme.accept(new OptimizationVisitor());
    System.out.println("\n");

    ascii.accept(new AnimationVisitor("ASCII Animation"));
    hello.accept(new AnimationVisitor("Hello World Animation"));
    bobby.accept(new AnimationVisitor("Bobby Animation"));
    bobbyOp.accept(new AnimationVisitor("Bobby Optimized Animation"));
    beer.accept(new AnimationVisitor("Beer Animation"));
    fibonacci.accept(new AnimationVisitor("Fibonacci Animation"));
    rumExtreme.accept(new AnimationVisitor("Rum Extreme Test Animation"));
  }

}