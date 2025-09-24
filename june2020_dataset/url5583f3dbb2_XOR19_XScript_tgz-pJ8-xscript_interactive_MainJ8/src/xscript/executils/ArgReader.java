package xscript.executils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.LinkedList;

public class ArgReader {

	private final String[] args;
	
	private int index;
	
	private LinkedList<FileArgReader> fileArgReader = new LinkedList<FileArgReader>();
	
	public static class RecuresiveFileException extends Exception{

		private static final long serialVersionUID = -6187218166184991980L;

		public final File f;

		public final String opend;
		
		public RecuresiveFileException(File f, String opend) {
			this.f = f;
			this.opend = opend;
		}
		
	}
	
	public ArgReader(String[] args) {
		this.args = args;
	}
	
	public String next() throws IOException, RecuresiveFileException{
		String next;
		while(!fileArgReader.isEmpty()){
			FileArgReader far = fileArgReader.getLast();
			if(far.hasNext()){
				break;
			}else{
				far.close();
				fileArgReader.removeLast();
			}
		}
		while(true){
			if(fileArgReader.isEmpty()){
				if(args.length>index){
					next = args[index++];
				}else{
					return null;
				}
			}else{
				FileArgReader far = fileArgReader.getLast();
				next = far.next();
			}
			if(next.length()>1){
				if(next.charAt(0)=='@'){
					next = next.substring(1);
					if(next.charAt(0)=='@'){
						return next;
					}else{
						File f = new File(next);
						for(FileArgReader reader:fileArgReader){
							if(reader.file.equals(f)){
								StringBuilder sb = new StringBuilder();
								for(FileArgReader r:fileArgReader){
									sb.append(r.file);
									sb.append(", ");
								}
								sb.setLength(sb.length()-2);
								throw new RecuresiveFileException(f, sb.toString());
							}
						}
						FileArgReader far = new FileArgReader(f);
						if(far.hasNext()){
							fileArgReader.addLast(far);
						}else{
							while(!fileArgReader.isEmpty()){
								far = fileArgReader.getLast();
								if(far.hasNext()){
									break;
								}else{
									far.close();
									fileArgReader.removeLast();
								}
							}
						}
					}
				}else{
					return next;
				}
			}else{
				return next;
			}
		}
	}

	private static class FileArgReader {
		
		final File file;
		
		private final Reader r;
		
		private final StreamTokenizer st;
		
		FileArgReader(File file) throws IOException{
			this.file = file;
			r = new BufferedReader(new FileReader(file));
	        st = new StreamTokenizer(r);
	        st.resetSyntax();
	        st.wordChars(' ', 255);
	        st.whitespaceChars(0, ' ');
	        st.commentChar('#');
	        st.quoteChar('"');
	        st.quoteChar('\'');
	        st.nextToken();
		}
		
		boolean hasNext(){
			return st.ttype != StreamTokenizer.TT_EOF;
		}
		
        String next() throws IOException{
        	if(hasNext()){
        		String ret = st.sval;
        		st.nextToken();
        		return ret;
        	}
        	return null;
        }
        
        void close() throws IOException{
        	r.close();
        }
        
	}

}
