import java.io.File;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileAlterationMonitor;

public class VirtualFileSystem implements FileAlterationListener {

	private File directory;

	public VirtualFileSystem(String dir) {
		this.directory = new File(dir);
		FileAlterationObserver observer = new FileAlterationObserver(dir);
		observer.addListener(this);
		FileAlterationMonitor monitor = new FileAlterationMonitor(10);
		monitor.addObserver(observer);
		try {
			monitor.start();
		} catch (Exception ex) {

		}
	}

	public void onDirectoryChange(File directory) {

	}

	public void onDirectoryCreate(File directory) {

	}

	public void onDirectoryDelete(File directory) {

	}

	public void onFileChange(File file) {
		System.out.println("file changed");
	}

	public void onFileCreate(File file) {
		System.out.println("file created");
	}

	public void onFileDelete(File file) {
		System.out.println("file deleted");
	}

	public void onStart(FileAlterationObserver observer) {

	}

	public void onStop(FileAlterationObserver observer) {

	}
}