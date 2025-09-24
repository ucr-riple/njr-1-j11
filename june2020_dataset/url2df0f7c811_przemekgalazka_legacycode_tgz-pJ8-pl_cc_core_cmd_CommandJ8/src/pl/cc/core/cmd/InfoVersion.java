package pl.cc.core.cmd;


/**
 * informacja nt minimalnej wersji klienta
 *
 * @since Jan 17, 2008
 */
public class InfoVersion extends Command {
  String version;
  String url;

  //+INFO Version '1', Url 'http://onet.pl'


  public InfoVersion(String line) {
    super(line);

    String[] names = {"version", "url"};
    String[] values = Command.getValuesINFO(line, names);

    this.version = values[0];
    this.url = values[1];
  }


  @Override
  public int getType() {
    return CMD_INFO_VERSION;
  }


  public String getVersion() {
    return version;
  }


  public int getVersionAsInt() {
    return new Integer(version);
  }


  public void setVersion(String version) {
    this.version = version;
  }


  public String getUrl() {
    return url;
  }


  public void setUrl(String url) {
    this.url = url;
  }


  public String toString() {
    String retValue = "";
    retValue = "InfoVersion ("
        //+ super.toString() +" "
        + "version: [" + this.version + "] "
        + "url: [" + this.url + "] "
        + ")";
    return retValue;
  }


}
