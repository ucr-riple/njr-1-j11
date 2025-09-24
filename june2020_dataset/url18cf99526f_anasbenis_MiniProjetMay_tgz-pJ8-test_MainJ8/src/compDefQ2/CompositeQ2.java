package compDefQ2;

import compClientDefQ2.InitPrint;
import compDefQ2.Print;
import question2.AnnounceInit;
import question2.PrintingRequest;

@SuppressWarnings("all")
public abstract class CompositeQ2 {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public AnnounceInit announceService();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends CompositeQ2.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public InitPrint.Component init();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Print.Component pr();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements CompositeQ2.Component, CompositeQ2.Parts {
    private final CompositeQ2.Requires bridge;
    
    private final CompositeQ2 implementation;
    
    public void start() {
      assert this.init != null: "This is a bug.";
      ((InitPrint.ComponentImpl) this.init).start();
      assert this.pr != null: "This is a bug.";
      ((Print.ComponentImpl) this.pr).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.init == null: "This is a bug.";
      assert this.implem_init == null: "This is a bug.";
      this.implem_init = this.implementation.make_init();
      if (this.implem_init == null) {
      	throw new RuntimeException("make_init() in compDefQ2.CompositeQ2 should not return null.");
      }
      this.init = this.implem_init._newComponent(new BridgeImpl_init(), false);
      assert this.pr == null: "This is a bug.";
      assert this.implem_pr == null: "This is a bug.";
      this.implem_pr = this.implementation.make_pr();
      if (this.implem_pr == null) {
      	throw new RuntimeException("make_pr() in compDefQ2.CompositeQ2 should not return null.");
      }
      this.pr = this.implem_pr._newComponent(new BridgeImpl_pr(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final CompositeQ2 implem, final CompositeQ2.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    public final AnnounceInit announceService() {
      return this.init.announceService();
    }
    
    private InitPrint.Component init;
    
    private InitPrint implem_init;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_init implements InitPrint.Requires {
      public final PrintingRequest printingService() {
        return CompositeQ2.ComponentImpl.this.pr.pReq();
      }
    }
    
    
    public final InitPrint.Component init() {
      return this.init;
    }
    
    private Print.Component pr;
    
    private Print implem_pr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_pr implements Print.Requires {
    }
    
    
    public final Print.Component pr() {
      return this.pr;
    }
  }
  
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   */
  private boolean started = false;;
  
  private CompositeQ2.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected CompositeQ2.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected CompositeQ2.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected CompositeQ2.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract InitPrint make_init();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Print make_pr();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized CompositeQ2.Component _newComponent(final CompositeQ2.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of CompositeQ2 has already been used to create a component, use another one.");
    }
    this.init = true;
    CompositeQ2.ComponentImpl comp = new CompositeQ2.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public CompositeQ2.Component newComponent() {
    return this._newComponent(new CompositeQ2.Requires() {}, true);
  }
}
