package compClientDefQ5;

import compClientDefQ4.InitProcessInclude;
import compDefQ2.CompositeQ2;
import question2.AnnounceInit;
import question4.Init;
import question4.InitRequest;

@SuppressWarnings("all")
public abstract class CompositeInitServise {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public InitRequest initReq();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Init init();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends CompositeInitServise.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public CompositeQ2.Component cmp();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public InitProcessInclude.Component initProcess();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements CompositeInitServise.Component, CompositeInitServise.Parts {
    private final CompositeInitServise.Requires bridge;
    
    private final CompositeInitServise implementation;
    
    public void start() {
      assert this.cmp != null: "This is a bug.";
      ((CompositeQ2.ComponentImpl) this.cmp).start();
      assert this.initProcess != null: "This is a bug.";
      ((InitProcessInclude.ComponentImpl) this.initProcess).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.cmp == null: "This is a bug.";
      assert this.implem_cmp == null: "This is a bug.";
      this.implem_cmp = this.implementation.make_cmp();
      if (this.implem_cmp == null) {
      	throw new RuntimeException("make_cmp() in compClientDefQ5.CompositeInitServise should not return null.");
      }
      this.cmp = this.implem_cmp._newComponent(new BridgeImpl_cmp(), false);
      assert this.initProcess == null: "This is a bug.";
      assert this.implem_initProcess == null: "This is a bug.";
      this.implem_initProcess = this.implementation.make_initProcess();
      if (this.implem_initProcess == null) {
      	throw new RuntimeException("make_initProcess() in compClientDefQ5.CompositeInitServise should not return null.");
      }
      this.initProcess = this.implem_initProcess._newComponent(new BridgeImpl_initProcess(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final CompositeInitServise implem, final CompositeInitServise.Requires b, final boolean doInits) {
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
    
    public final Init init() {
      return this.initProcess.doInitInclude();
    }
    
    private CompositeQ2.Component cmp;
    
    private CompositeQ2 implem_cmp;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_cmp implements CompositeQ2.Requires {
    }
    
    
    public final CompositeQ2.Component cmp() {
      return this.cmp;
    }
    
    private InitProcessInclude.Component initProcess;
    
    private InitProcessInclude implem_initProcess;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_initProcess implements InitProcessInclude.Requires {
      public final AnnounceInit doAnnounceInclude() {
        return CompositeInitServise.ComponentImpl.this.cmp.announceService();
      }
      
      public final InitRequest initReqInclude() {
        return CompositeInitServise.ComponentImpl.this.bridge.initReq();
      }
    }
    
    
    public final InitProcessInclude.Component initProcess() {
      return this.initProcess;
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
  
  private CompositeInitServise.ComponentImpl selfComponent;
  
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
  protected CompositeInitServise.Provides provides() {
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
  protected CompositeInitServise.Requires requires() {
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
  protected CompositeInitServise.Parts parts() {
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
  protected abstract CompositeQ2 make_cmp();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract InitProcessInclude make_initProcess();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized CompositeInitServise.Component _newComponent(final CompositeInitServise.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of CompositeInitServise has already been used to create a component, use another one.");
    }
    this.init = true;
    CompositeInitServise.ComponentImpl comp = new CompositeInitServise.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
