package compClientDefQ3;

import compDefQ2.Print;
import compDefQ3.StartEval;
import compDefQ3.ValidateComp;
import question2.PrintingRequest;
import question3.Eval;
import question3.EvalRequest;
import question3.ValidateRequest;

@SuppressWarnings("all")
public abstract class CompositeQ3 {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public EvalRequest idEval();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Eval eval();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends CompositeQ3.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Print.Component pr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public StartEval.Component startEv();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ValidateComp.Component validate();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements CompositeQ3.Component, CompositeQ3.Parts {
    private final CompositeQ3.Requires bridge;
    
    private final CompositeQ3 implementation;
    
    public void start() {
      assert this.pr != null: "This is a bug.";
      ((Print.ComponentImpl) this.pr).start();
      assert this.startEv != null: "This is a bug.";
      ((StartEval.ComponentImpl) this.startEv).start();
      assert this.validate != null: "This is a bug.";
      ((ValidateComp.ComponentImpl) this.validate).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.pr == null: "This is a bug.";
      assert this.implem_pr == null: "This is a bug.";
      this.implem_pr = this.implementation.make_pr();
      if (this.implem_pr == null) {
      	throw new RuntimeException("make_pr() in compClientDefQ3.CompositeQ3 should not return null.");
      }
      this.pr = this.implem_pr._newComponent(new BridgeImpl_pr(), false);
      assert this.startEv == null: "This is a bug.";
      assert this.implem_startEv == null: "This is a bug.";
      this.implem_startEv = this.implementation.make_startEv();
      if (this.implem_startEv == null) {
      	throw new RuntimeException("make_startEv() in compClientDefQ3.CompositeQ3 should not return null.");
      }
      this.startEv = this.implem_startEv._newComponent(new BridgeImpl_startEv(), false);
      assert this.validate == null: "This is a bug.";
      assert this.implem_validate == null: "This is a bug.";
      this.implem_validate = this.implementation.make_validate();
      if (this.implem_validate == null) {
      	throw new RuntimeException("make_validate() in compClientDefQ3.CompositeQ3 should not return null.");
      }
      this.validate = this.implem_validate._newComponent(new BridgeImpl_validate(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final CompositeQ3 implem, final CompositeQ3.Requires b, final boolean doInits) {
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
    
    public final Eval eval() {
      return this.startEv.evaluate();
    }
    
    private Print.Component pr;
    
    private Print implem_pr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_pr implements Print.Requires {
    }
    
    
    public final Print.Component pr() {
      return this.pr;
    }
    
    private StartEval.Component startEv;
    
    private StartEval implem_startEv;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_startEv implements StartEval.Requires {
      public final PrintingRequest printService() {
        return CompositeQ3.ComponentImpl.this.pr.pReq();
      }
      
      public final ValidateRequest validateService() {
        return CompositeQ3.ComponentImpl.this.validate.validReq();
      }
    }
    
    
    public final StartEval.Component startEv() {
      return this.startEv;
    }
    
    private ValidateComp.Component validate;
    
    private ValidateComp implem_validate;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_validate implements ValidateComp.Requires {
      public final EvalRequest evReq() {
        return CompositeQ3.ComponentImpl.this.bridge.idEval();
      }
    }
    
    
    public final ValidateComp.Component validate() {
      return this.validate;
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
  
  private CompositeQ3.ComponentImpl selfComponent;
  
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
  protected CompositeQ3.Provides provides() {
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
  protected CompositeQ3.Requires requires() {
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
  protected CompositeQ3.Parts parts() {
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
  protected abstract Print make_pr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract StartEval make_startEv();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ValidateComp make_validate();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized CompositeQ3.Component _newComponent(final CompositeQ3.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of CompositeQ3 has already been used to create a component, use another one.");
    }
    this.init = true;
    CompositeQ3.ComponentImpl comp = new CompositeQ3.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
