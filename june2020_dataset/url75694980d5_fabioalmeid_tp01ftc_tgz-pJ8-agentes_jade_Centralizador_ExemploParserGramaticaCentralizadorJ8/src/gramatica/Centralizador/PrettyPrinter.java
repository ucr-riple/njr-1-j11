package gramatica.Centralizador;
import gramatica.Centralizador.Absyn.*;

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
  public static String print(gramatica.Centralizador.Absyn.Tarefa foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Tarefa foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Acao foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Acao foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Coletar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Coletar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Aplicar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Aplicar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Dados foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Dados foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Operador foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Operador foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Medicacao foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Medicacao foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Quantidade foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Quantidade foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(gramatica.Centralizador.Absyn.Remedio foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(gramatica.Centralizador.Absyn.Remedio foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(gramatica.Centralizador.Absyn.Tarefa foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ETask)
    {
       gramatica.Centralizador.Absyn.ETask _etask = (gramatica.Centralizador.Absyn.ETask) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_etask.acao_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Acao foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EAction1)
    {
       gramatica.Centralizador.Absyn.EAction1 _eaction1 = (gramatica.Centralizador.Absyn.EAction1) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eaction1.coletar_, 0);
       pp(_eaction1.dados_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EAction2)
    {
       gramatica.Centralizador.Absyn.EAction2 _eaction2 = (gramatica.Centralizador.Absyn.EAction2) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eaction2.aplicar_, 0);
       pp(_eaction2.medicacao_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EAction3)
    {
       gramatica.Centralizador.Absyn.EAction3 _eaction3 = (gramatica.Centralizador.Absyn.EAction3) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Autodestruicao");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Coletar foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ECollect1)
    {
       gramatica.Centralizador.Absyn.ECollect1 _ecollect1 = (gramatica.Centralizador.Absyn.ECollect1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Iniciar Medicao de ");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.ECollect2)
    {
       gramatica.Centralizador.Absyn.ECollect2 _ecollect2 = (gramatica.Centralizador.Absyn.ECollect2) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Parar Medicao de ");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Aplicar foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EApply1)
    {
       gramatica.Centralizador.Absyn.EApply1 _eapply1 = (gramatica.Centralizador.Absyn.EApply1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Liberar");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EApply2)
    {
       gramatica.Centralizador.Absyn.EApply2 _eapply2 = (gramatica.Centralizador.Absyn.EApply2) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Cessar Liberacao de ");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Dados foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EData1)
    {
       gramatica.Centralizador.Absyn.EData1 _edata1 = (gramatica.Centralizador.Absyn.EData1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Temperatura");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EData2)
    {
       gramatica.Centralizador.Absyn.EData2 _edata2 = (gramatica.Centralizador.Absyn.EData2) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Hemoglobina");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EData3)
    {
       gramatica.Centralizador.Absyn.EData3 _edata3 = (gramatica.Centralizador.Absyn.EData3) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Bilirrubina");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EData4)
    {
       gramatica.Centralizador.Absyn.EData4 _edata4 = (gramatica.Centralizador.Absyn.EData4) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Pressao Arterial");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EData5)
    {
       gramatica.Centralizador.Absyn.EData5 _edata5 = (gramatica.Centralizador.Absyn.EData5) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_edata5.dados_1, 0);
       pp(_edata5.operador_, 0);
       pp(_edata5.dados_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Operador foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EOp)
    {
       gramatica.Centralizador.Absyn.EOp _eop = (gramatica.Centralizador.Absyn.EOp) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("e");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Medicacao foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EMedic1)
    {
       gramatica.Centralizador.Absyn.EMedic1 _emedic1 = (gramatica.Centralizador.Absyn.EMedic1) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_emedic1.quantidade_, 0);
       render(" ml de ");
       pp(_emedic1.remedio_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.EMedic2)
    {
       gramatica.Centralizador.Absyn.EMedic2 _emedic2 = (gramatica.Centralizador.Absyn.EMedic2) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_emedic2.remedio_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Quantidade foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EQtde1)
    {
       gramatica.Centralizador.Absyn.EQtde1 _eqtde1 = (gramatica.Centralizador.Absyn.EQtde1) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eqtde1.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(gramatica.Centralizador.Absyn.Remedio foo, int _i_)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ERemedy1)
    {
       gramatica.Centralizador.Absyn.ERemedy1 _eremedy1 = (gramatica.Centralizador.Absyn.ERemedy1) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Dipirona");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof gramatica.Centralizador.Absyn.ERemedy2)
    {
       gramatica.Centralizador.Absyn.ERemedy2 _eremedy2 = (gramatica.Centralizador.Absyn.ERemedy2) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Paracetamol");
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(gramatica.Centralizador.Absyn.Tarefa foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ETask)
    {
       gramatica.Centralizador.Absyn.ETask _etask = (gramatica.Centralizador.Absyn.ETask) foo;
       render("(");
       render("ETask");
       sh(_etask.acao_);
       render(")");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Acao foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EAction1)
    {
       gramatica.Centralizador.Absyn.EAction1 _eaction1 = (gramatica.Centralizador.Absyn.EAction1) foo;
       render("(");
       render("EAction1");
       sh(_eaction1.coletar_);
       sh(_eaction1.dados_);
       render(")");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EAction2)
    {
       gramatica.Centralizador.Absyn.EAction2 _eaction2 = (gramatica.Centralizador.Absyn.EAction2) foo;
       render("(");
       render("EAction2");
       sh(_eaction2.aplicar_);
       sh(_eaction2.medicacao_);
       render(")");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EAction3)
    {
       gramatica.Centralizador.Absyn.EAction3 _eaction3 = (gramatica.Centralizador.Absyn.EAction3) foo;
       render("EAction3");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Coletar foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ECollect1)
    {
       gramatica.Centralizador.Absyn.ECollect1 _ecollect1 = (gramatica.Centralizador.Absyn.ECollect1) foo;
       render("ECollect1");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.ECollect2)
    {
       gramatica.Centralizador.Absyn.ECollect2 _ecollect2 = (gramatica.Centralizador.Absyn.ECollect2) foo;
       render("ECollect2");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Aplicar foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EApply1)
    {
       gramatica.Centralizador.Absyn.EApply1 _eapply1 = (gramatica.Centralizador.Absyn.EApply1) foo;
       render("EApply1");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EApply2)
    {
       gramatica.Centralizador.Absyn.EApply2 _eapply2 = (gramatica.Centralizador.Absyn.EApply2) foo;
       render("EApply2");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Dados foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EData1)
    {
       gramatica.Centralizador.Absyn.EData1 _edata1 = (gramatica.Centralizador.Absyn.EData1) foo;
       render("EData1");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EData2)
    {
       gramatica.Centralizador.Absyn.EData2 _edata2 = (gramatica.Centralizador.Absyn.EData2) foo;
       render("EData2");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EData3)
    {
       gramatica.Centralizador.Absyn.EData3 _edata3 = (gramatica.Centralizador.Absyn.EData3) foo;
       render("EData3");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EData4)
    {
       gramatica.Centralizador.Absyn.EData4 _edata4 = (gramatica.Centralizador.Absyn.EData4) foo;
       render("EData4");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EData5)
    {
       gramatica.Centralizador.Absyn.EData5 _edata5 = (gramatica.Centralizador.Absyn.EData5) foo;
       render("(");
       render("EData5");
       sh(_edata5.dados_1);
       sh(_edata5.operador_);
       sh(_edata5.dados_2);
       render(")");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Operador foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EOp)
    {
       gramatica.Centralizador.Absyn.EOp _eop = (gramatica.Centralizador.Absyn.EOp) foo;
       render("EOp");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Medicacao foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EMedic1)
    {
       gramatica.Centralizador.Absyn.EMedic1 _emedic1 = (gramatica.Centralizador.Absyn.EMedic1) foo;
       render("(");
       render("EMedic1");
       sh(_emedic1.quantidade_);
       sh(_emedic1.remedio_);
       render(")");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.EMedic2)
    {
       gramatica.Centralizador.Absyn.EMedic2 _emedic2 = (gramatica.Centralizador.Absyn.EMedic2) foo;
       render("(");
       render("EMedic2");
       sh(_emedic2.remedio_);
       render(")");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Quantidade foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.EQtde1)
    {
       gramatica.Centralizador.Absyn.EQtde1 _eqtde1 = (gramatica.Centralizador.Absyn.EQtde1) foo;
       render("(");
       render("EQtde1");
       sh(_eqtde1.integer_);
       render(")");
    }
  }

  private static void sh(gramatica.Centralizador.Absyn.Remedio foo)
  {
    if (foo instanceof gramatica.Centralizador.Absyn.ERemedy1)
    {
       gramatica.Centralizador.Absyn.ERemedy1 _eremedy1 = (gramatica.Centralizador.Absyn.ERemedy1) foo;
       render("ERemedy1");
    }
    if (foo instanceof gramatica.Centralizador.Absyn.ERemedy2)
    {
       gramatica.Centralizador.Absyn.ERemedy2 _eremedy2 = (gramatica.Centralizador.Absyn.ERemedy2) foo;
       render("ERemedy2");
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

