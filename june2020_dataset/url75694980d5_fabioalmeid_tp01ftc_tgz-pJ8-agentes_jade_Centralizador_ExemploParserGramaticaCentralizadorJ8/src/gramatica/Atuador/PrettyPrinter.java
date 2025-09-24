package gramatica.Atuador;
import gramatica.Atuador.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(gramatica.Atuador.Absyn.Tarefa foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Atuador.Absyn.Tarefa foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Atuador.Absyn.Acao foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Atuador.Absyn.Acao foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Atuador.Absyn.Remedio foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Atuador.Absyn.Remedio foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(gramatica.Atuador.Absyn.Tarefa foo, int _i_)
  {
    if (foo instanceof gramatica.Atuador.Absyn.ETarefa)
    {
       gramatica.Atuador.Absyn.ETarefa _etarefa = (gramatica.Atuador.Absyn.ETarefa) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_etarefa.acao_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Atuador.Absyn.Acao foo, int _i_)
  {
    if (foo instanceof gramatica.Atuador.Absyn.EAcao)
    {
       gramatica.Atuador.Absyn.EAcao _eacao = (gramatica.Atuador.Absyn.EAcao) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Liberando");
       pp(_eacao.remedio_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Atuador.Absyn.EAcao1)
    {
       gramatica.Atuador.Absyn.EAcao1 _eacao1 = (gramatica.Atuador.Absyn.EAcao1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Cessando Liberacao de");
       pp(_eacao1.remedio_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Atuador.Absyn.Remedio foo, int _i_)
  {
    if (foo instanceof gramatica.Atuador.Absyn.ERemedio)
    {
       gramatica.Atuador.Absyn.ERemedio _eremedio = (gramatica.Atuador.Absyn.ERemedio) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Dipirona");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Atuador.Absyn.ERemedio1)
    {
       gramatica.Atuador.Absyn.ERemedio1 _eremedio1 = (gramatica.Atuador.Absyn.ERemedio1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Paracetamol");
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(gramatica.Atuador.Absyn.Tarefa foo)
  {
    if (foo instanceof gramatica.Atuador.Absyn.ETarefa)
    {
       gramatica.Atuador.Absyn.ETarefa _etarefa = (gramatica.Atuador.Absyn.ETarefa) foo;
       render("(");
       render("ETarefa");
       sh(_etarefa.acao_);
       render(")");
    }
  }

  private static void sh(gramatica.Atuador.Absyn.Acao foo)
  {
    if (foo instanceof gramatica.Atuador.Absyn.EAcao)
    {
       gramatica.Atuador.Absyn.EAcao _eacao = (gramatica.Atuador.Absyn.EAcao) foo;
       render("(");
       render("EAcao");
       sh(_eacao.remedio_);
       render(")");
    }
    if (foo instanceof gramatica.Atuador.Absyn.EAcao1)
    {
       gramatica.Atuador.Absyn.EAcao1 _eacao1 = (gramatica.Atuador.Absyn.EAcao1) foo;
       render("(");
       render("EAcao1");
       sh(_eacao1.remedio_);
       render(")");
    }
  }

  private static void sh(gramatica.Atuador.Absyn.Remedio foo)
  {
    if (foo instanceof gramatica.Atuador.Absyn.ERemedio)
    {
       gramatica.Atuador.Absyn.ERemedio _eremedio = (gramatica.Atuador.Absyn.ERemedio) foo;
       render("ERemedio");
    }
    if (foo instanceof gramatica.Atuador.Absyn.ERemedio1)
    {
       gramatica.Atuador.Absyn.ERemedio1 _eremedio1 = (gramatica.Atuador.Absyn.ERemedio1) foo;
       render("ERemedio1");
    }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}

