package xx.test;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.xml.sax.SAXException;

import xx.Parser;
import xx.XPathEvaluateur;
import xx.XpathValidator;


public class Gui {

	protected Shell shell;
	private Text textXPath;
	private Label lblError;
	private StyledText XMLText;
	private StyledText resultText;
	XPathEvaluateur compiler = new XPathEvaluateur();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Gui window = new Gui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(859, 520);
		shell.setText("XPath Parser -  INF345");
		
		TextViewer textViewer_1 = new TextViewer(shell, SWT.BORDER);
		resultText = textViewer_1.getTextWidget();
		resultText.setBounds(477, 98, 340, 353);
		
		textXPath = new Text(shell, SWT.BORDER);
		textXPath.setBounds(73, 27, 445, 21);


		textXPath.addKeyListener( new KeyAdapter() {
		    @Override
		    public void keyPressed( KeyEvent event ) {
		 
//		    	 Get the widget whose text was modified
		        String currentText = ((Text)event.widget).getText();
		        Object result = Parser.parseExpression(currentText,new XpathValidator() );
		        
		        if(!(result instanceof Boolean )){
		        	lblError.setText("XPath non valide");
		        }else {
		        	lblError.setText("");
		        }
		         
		    }
		    public void keyReleased( KeyEvent event ) {
				 
//		    	 Get the widget whose text was modified
		        String currentText = ((Text)event.widget).getText();
		        Object result = Parser.parseExpression(currentText,new XpathValidator() );
		        
		        if(!(result instanceof Boolean )){
		        	lblError.setText("XPath non valide");
		        }else {
		        	lblError.setText("");
		        }
		         
		    }
		});

		TextViewer textViewer = new TextViewer(shell, SWT.BORDER);
		XMLText = textViewer.getTextWidget();
		XMLText.setBounds(73, 98, 361, 353);
		
		Button btnEval = new Button(shell, SWT.NONE);
		btnEval.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String xml =XMLText.getText();
				String xpath=textXPath.getText();
			
				try {
					resultText.setText(compiler.parseString(xpath, xml));
				} catch (ParserConfigurationException | SAXException
						| IOException e1) {
				lblError.setText("Error de parsing");
				}
				
			}
		});
		btnEval.setBounds(586, 25, 75, 25);
		btnEval.setText("Eval");
		
		lblError = new Label(shell, SWT.NONE);
		lblError.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblError.setBounds(73, 65, 445, 15);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Fichier");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmOuvrir = new MenuItem(menu_1, SWT.NONE);
		mntmOuvrir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Parcourir");
		        fd.setFilterPath("C:/");
		        String[] filterExt = { "*.xml", "*.*" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        
		       if(selected!=null){
//		    	   boText.setText(selected);
		    try {
				XMLText.setText(compiler.readFile(selected));
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				// TODO Auto-generated catch block
			 lblError.setText("Error reading xml file");
			}
		    
		    	   

System.out.println(selected);
		       }
			}
		});
		mntmOuvrir.setText("Ouvrir");
		
		Button btnReset = new Button(shell, SWT.NONE);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textXPath.setText("");
				XMLText.setText("");
				resultText.setText("");
				lblError.setText("");
			}
		});
		btnReset.setBounds(702, 25, 75, 25);
		btnReset.setText("Reset");

	}
}
