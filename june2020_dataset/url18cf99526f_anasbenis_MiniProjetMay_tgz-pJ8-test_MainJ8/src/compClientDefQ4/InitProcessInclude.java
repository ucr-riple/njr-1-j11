package compClientDefQ4;

import compClientDefQ4.InitProcessFollower;
import compDefQ4.InitProcess;
import question2.AnnounceInit;
import question4.Init;
import question4.InitRequest;

@SuppressWarnings("all")
public abstract class InitProcessInclude {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public AnnounceInit doAnnounceInclude();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public InitRequest initReqInclude();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Init doInitInclude();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends InitProcessInclude.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public InitProcess.Component initP();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public InitProcessFollower.Component initPFollower();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements InitProcessInclude.Component, InitProcessInclude.Parts {
    private final InitProcessInclude.Requires bridge;
    
    private final InitProcessInclude implementation;
    
    public void start() {
      assert this.initP != null: "This is a bug.";
      ((InitProcess.ComponentImpl) this.initP).start();
      assert this.initPFollower != null: "This is a bug.";
      ((InitProcessFollower.ComponentImpl) this.initPFollower).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.initP == null: "This is a bug.";
      assert this.implem_initP == null: "This is a bug.";
      this.implem_initP = this.implementation.make_initP();
      if (this.implem_initP == null) {
      	throw new RuntimeException("make_initP() in compClientDefQ4.InitProcessInclude should not return null.");
      }
      this.initP = this.implem_initP._newComponent(new BridgeImpl_initP(), false);
      assert this.initPFollower == null: "This is a bug.";
      assert this.implem_initPFollower == null: "This is a bug.";
      this.implem_initPFollower = this.implementation.make_initPFollower();
      if (this.implem_initPFollower == null) {
      	throw new RuntimeException("make_initPFollower() in compClientDefQ4.InitProcessInclude should not return null.");
      }
      this.initPFollower = this.implem_initPFollower._newComponent(new BridgeImpl_initPFollower(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final InitProcessInclude implem, final InitProcessInclude.Requires b, final boolean doInits) {
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
    
    public final Init doInitInclude() {
      return this.initPFollower.doInitFollower();
    }
    
    private InitProcess.Component initP;
    
    private InitProcess implem_initP;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_initP implements InitProcess.Requires {
      public final AnnounceInit doAnnounce() {
        return InitProcessInclude.ComponentImpl.this.bridge.doAnnounceInclude();
      }
      
      public final InitRequest initReq() {
        return InitProcessInclude.ComponentImpl.this.bridge.initReqInclude();
      }
    }
    
    
    public final InitProcess.Component initP() {
      return this.initP;
    }
    
    private InitProcessFollower.Component initPFollower;
    
    private InitProcessFollower implem_initPFollower;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_initPFollower implements InitProcessFollower.Requires {
      public final Init getInit() {
        return InitProcessInclude.ComponentImpl.this.initP.doInit();
      }
    }
    
    
    public final InitProcessFollower.Component initPFollower() {
      return this.initPFollower;
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
  
  private InitProcessInclude.ComponentImpl selfComponent;
  
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
  protected InitProcessInclude.Provides provides() {
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
  protected InitProcessInclude.Requires requires() {
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
  protected InitProcessInclude.Parts parts() {
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
  protected abstract InitProcess make_initP();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract InitProcessFollower make_initPFollower();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized InitProcessInclude.Component _newComponent(final InitProcessInclude.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of InitProcessInclude has already been used to create a component, use another one.");
    }
    this.init = true;
    InitProcessInclude.ComponentImpl comp = new InitProcessInclude.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
