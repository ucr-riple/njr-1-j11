package mLibrary;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.innovatium.mumps2java.todo.TODO;

public class mCmd extends mParent {

	public mCmd(mContext m$) {
		super(m$);
	}

	public void Close(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public String defineMethodName(String methodName) {
		if (methodName == null) {
			return null;
		}
		final String[] method = methodName.split("\\^");
		if (method.length > 1) {
			return method[1] + "." + (method[0].isEmpty() ? "main" : method[0]);
		} else if (method.length == 1) {
			return method[0] + ".main";
		} else {
			return null;
		}
	}

	public void Do(mClass objClass, String methodName, Object... parameters) {
		methodName = m$.defineMethodName(objClass, methodName);
		m$.dispatch(objClass, methodName, parameters);
	}

	public void Do(Object object, String methodName, Object object2) {
		// TODO Auto-generated method stub

	}

	public void Do(Object methodName) {
		Do(mFncUtil.castString(methodName));
	}

	public void Do(String methodName) {
		if (isIndirectionExecution(methodName)) {
			mVar var = m$.indirectVar(methodName);
			methodName = var.getName();

			if (isMethodExecution(methodName)) {
				Do(defineMethodName(methodName), var.getParameters());
			} else {
				Do(methodName, var.getParameters());
			}
		} else {
			if (isMethodExecution(methodName)) {
				Do(defineMethodName(methodName), (Object[]) null);
			} else {
				m$.fnc$(methodName);
			}
		}
	}

	public void Do(String methodName, Object... parameters) {
		Do(null, methodName, parameters);
	}

	public void Goto(Object label) {
		throw new UnsupportedOperationException();
	}

	/*
	 * Pausa o processamento por um determinado número de milisegundos
	 */
	public void Hang(Object obj) {
		try {
			Thread.sleep(Long.valueOf(String.valueOf(obj)));
		} catch (NumberFormatException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (InterruptedException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	public boolean isIndirectionExecution(String content) {
		if (content == null) {
			return false;
		}
		return content.indexOf("(") != -1;
	}

	public boolean isMethodExecution(String content) {
		if (content == null) {
			return false;
		}
		return content.indexOf("^") != -1;
	}

	public void Job(String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Lock(String string, String string2, String string3) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void LockInc(mVar var, int i) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Merge(mVar target, mVar source) {
		m$.merge(target, source);
		// TODO Auto-generated method stub
	}

	public void Open(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Open(Object object, String string, int i) {
		// TODO Auto-generated method stub

	}

	public void Open(Object object, String string, Object concat, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * Exibe mensagem no console solicitando entrada de dados.
	 */
	public void Read(Object... parameters) {
		Scanner s = null;
		try {
			String variable = "";
			if (parameters != null && parameters.length > 0) {
				variable = String.valueOf(parameters[0]);
			}
			s = new Scanner(System.in);
			System.out.println(variable);
			String input = s.next();
			System.out.println("Input: " + input);
		} catch (Exception e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	public void SQL() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Unlock(mVar var) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Unlock(mVar var, String str) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Unlock(String string) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Use(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * Remove toString to wirte faster
	 */
	@TODO
	public void Write(Object... string) {
		try {
			Writer writer = m$.getWriter();
			for (Object str : string) {
				try {
					writer.write(String.valueOf(str));
					if(String.valueOf(str).contains("<HR")){
						System.out.print("");
					}
					System.out.print(mFncUtil.toString(str));
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"Fail to write the string HTML " + str.toString());
				}

			}
		} catch (NullPointerException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void WriteHtml(Object... string) {
		Write(string);
	}

	public void WriteJS(Object... string) {
		Write(string);
	}

	public void Xecute(Object object) {
		m$.var("^MXecute", "cmd", ++m$.xecuteCount).set(object.toString());
		if (String.valueOf(object).startsWith("do ")) {
			Do(String.valueOf(object).replaceAll("do ", ""));
		} else if (String.valueOf(object).toUpperCase().startsWith("U ")
				|| String.valueOf(object).toUpperCase().startsWith("USER ")) {
		} else if (String.valueOf(object).toUpperCase().startsWith("SET ")) {
		} else {
			throw new UnsupportedOperationException();
		}

	}

	/*
	 * Revisar implementacao
	 */
	@TODO
	public void Lock(mVar var) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	/*
	 * Revisar implementacao
	 */
	@TODO
	public void Lock(mVar var, int index) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Job(String methodName) {
		new Thread(new JobCmd(methodName)).start();
	}

	private class JobCmd implements Runnable {
		private String methodName;

		public JobCmd(final String methodName) {
			this.methodName = methodName;
		}

		public void run() {
			m$.Cmd.Do(methodName);
		}

	}
}
