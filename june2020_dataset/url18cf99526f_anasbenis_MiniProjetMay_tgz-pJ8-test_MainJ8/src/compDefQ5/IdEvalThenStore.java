package compDefQ5;

import question1.Identifiable;
import question3.EvalRequest;
import question4.InitRequest;
import question5.ComputableEval;
import question5.StorableEval;

@SuppressWarnings("all")
public abstract class IdEvalThenStore {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Identifiable idReq();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ComputableEval compReq();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public StorableEval stoReq();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public EvalRequest evalReq();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public InitRequest initReq();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends IdEvalThenStore.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements IdEvalThenStore.Component, IdEvalThenStore.Parts {
    private final IdEvalThenStore.Requires bridge;
    
    private final IdEvalThenStore implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.evalReq == null: "This is a bug.";
      this.evalReq = this.implementation.make_evalReq();
      if (this.evalReq == null) {
      	throw new RuntimeException("make_evalReq() in compDefQ5.IdEvalThenStore should not return null.");
      }
      assert this.initReq == null: "This is a bug.";
      this.initReq = this.implementation.make_initReq();
      if (this.initReq == null) {
      	throw new RuntimeException("make_initReq() in compDefQ5.IdEvalThenStore should not return null.");
      }
      
    }
    
    public ComponentImpl(final IdEvalThenStore implem, final IdEvalThenStore.Requires b, final boolean doInits) {
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
    
    private EvalRequest evalReq;
    
    public final EvalRequest evalReq() {
      return this.evalReq;
    }
    
    private InitRequest initReq;
    
    public final InitRequest initReq() {
      return this.initReq;
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
  
  private IdEvalThenStore.ComponentImpl selfComponent;
  
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
  protected IdEvalThenStore.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract EvalRequest make_evalReq();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract InitRequest make_initReq();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected IdEvalThenStore.Requires requires() {
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
  protected IdEvalThenStore.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized IdEvalThenStore.Component _newComponent(final IdEvalThenStore.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IdEvalThenStore has already been used to create a component, use another one.");
    }
    this.init = true;
    IdEvalThenStore.ComponentImpl comp = new IdEvalThenStore.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
