package monitor;
import monitor.Absyn.*;

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
  public static String print(monitor.Absyn.Tarefa foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Tarefa foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(monitor.Absyn.Acao foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Acao foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(monitor.Absyn.Dados foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Dados foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(monitor.Absyn.Quantidade foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Quantidade foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(monitor.Absyn.Operador foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Operador foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(monitor.Absyn.Hora foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(monitor.Absyn.Hora foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(monitor.Absyn.Tarefa foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.ETarefa)
    {
       monitor.Absyn.ETarefa _etarefa = (monitor.Absyn.ETarefa) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_etarefa.acao_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(monitor.Absyn.Acao foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.EAcao)
    {
       monitor.Absyn.EAcao _eacao = (monitor.Absyn.EAcao) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eacao.dados_, 0);
       render("as");
       pp(_eacao.hora_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof monitor.Absyn.EAcao2)
    {
       monitor.Absyn.EAcao2 _eacao2 = (monitor.Absyn.EAcao2) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eacao2.acao_1, 0);
       pp(_eacao2.operador_, 0);
       pp(_eacao2.acao_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(monitor.Absyn.Dados foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.EDados)
    {
       monitor.Absyn.EDados _edados = (monitor.Absyn.EDados) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Temperatura de");
       pp(_edados.quantidade_, 0);
       render("C");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof monitor.Absyn.EDados1)
    {
       monitor.Absyn.EDados1 _edados1 = (monitor.Absyn.EDados1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Bilirrubina");
       pp(_edados1.quantidade_, 0);
       render("g/dL");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof monitor.Absyn.EDados2)
    {
       monitor.Absyn.EDados2 _edados2 = (monitor.Absyn.EDados2) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Hemoglobina");
       pp(_edados2.quantidade_, 0);
       render("mg/dL");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof monitor.Absyn.EDados3)
    {
       monitor.Absyn.EDados3 _edados3 = (monitor.Absyn.EDados3) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Pressao Arterial");
       pp(_edados3.quantidade_1, 0);
       render(":");
       pp(_edados3.quantidade_2, 0);
       render("mmHg");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(monitor.Absyn.Quantidade foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.EQuantidade)
    {
       monitor.Absyn.EQuantidade _equantidade = (monitor.Absyn.EQuantidade) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_equantidade.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(monitor.Absyn.Operador foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.EOperador)
    {
       monitor.Absyn.EOperador _eoperador = (monitor.Absyn.EOperador) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("e");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(monitor.Absyn.Hora foo, int _i_)
  {
    if (foo instanceof monitor.Absyn.EHora)
    {
       monitor.Absyn.EHora _ehora = (monitor.Absyn.EHora) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ehora.integer_1, 0);
       render("h:");
       pp(_ehora.integer_2, 0);
       render("m");
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(monitor.Absyn.Tarefa foo)
  {
    if (foo instanceof monitor.Absyn.ETarefa)
    {
       monitor.Absyn.ETarefa _etarefa = (monitor.Absyn.ETarefa) foo;
       render("(");
       render("ETarefa");
       sh(_etarefa.acao_);
       render(")");
    }
  }

  private static void sh(monitor.Absyn.Acao foo)
  {
    if (foo instanceof monitor.Absyn.EAcao)
    {
       monitor.Absyn.EAcao _eacao = (monitor.Absyn.EAcao) foo;
       render("(");
       render("EAcao");
       sh(_eacao.dados_);
       sh(_eacao.hora_);
       render(")");
    }
    if (foo instanceof monitor.Absyn.EAcao2)
    {
       monitor.Absyn.EAcao2 _eacao2 = (monitor.Absyn.EAcao2) foo;
       render("(");
       render("EAcao2");
       sh(_eacao2.acao_1);
       sh(_eacao2.operador_);
       sh(_eacao2.acao_2);
       render(")");
    }
  }

  private static void sh(monitor.Absyn.Dados foo)
  {
    if (foo instanceof monitor.Absyn.EDados)
    {
       monitor.Absyn.EDados _edados = (monitor.Absyn.EDados) foo;
       render("(");
       render("EDados");
       sh(_edados.quantidade_);
       render(")");
    }
    if (foo instanceof monitor.Absyn.EDados1)
    {
       monitor.Absyn.EDados1 _edados1 = (monitor.Absyn.EDados1) foo;
       render("(");
       render("EDados1");
       sh(_edados1.quantidade_);
       render(")");
    }
    if (foo instanceof monitor.Absyn.EDados2)
    {
       monitor.Absyn.EDados2 _edados2 = (monitor.Absyn.EDados2) foo;
       render("(");
       render("EDados2");
       sh(_edados2.quantidade_);
       render(")");
    }
    if (foo instanceof monitor.Absyn.EDados3)
    {
       monitor.Absyn.EDados3 _edados3 = (monitor.Absyn.EDados3) foo;
       render("(");
       render("EDados3");
       sh(_edados3.quantidade_1);
       sh(_edados3.quantidade_2);
       render(")");
    }
  }

  private static void sh(monitor.Absyn.Quantidade foo)
  {
    if (foo instanceof monitor.Absyn.EQuantidade)
    {
       monitor.Absyn.EQuantidade _equantidade = (monitor.Absyn.EQuantidade) foo;
       render("(");
       render("EQuantidade");
       sh(_equantidade.integer_);
       render(")");
    }
  }

  private static void sh(monitor.Absyn.Operador foo)
  {
    if (foo instanceof monitor.Absyn.EOperador)
    {
       monitor.Absyn.EOperador _eoperador = (monitor.Absyn.EOperador) foo;
       render("EOperador");
    }
  }

  private static void sh(monitor.Absyn.Hora foo)
  {
    if (foo instanceof monitor.Absyn.EHora)
    {
       monitor.Absyn.EHora _ehora = (monitor.Absyn.EHora) foo;
       render("(");
       render("EHora");
       sh(_ehora.integer_1);
       sh(_ehora.integer_2);
       render(")");
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

