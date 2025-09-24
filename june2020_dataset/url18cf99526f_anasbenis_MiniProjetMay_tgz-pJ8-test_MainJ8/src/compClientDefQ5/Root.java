package compClientDefQ5;

import compClientDefQ3.CompositeQ3;
import compClientDefQ5.CompositeInitServise;
import compDefQ1.Identification;
import compDefQ5.ComputeEval;
import compDefQ5.IdEvalThenStore;
import compDefQ5.StoreEval;
import question1.Identifiable;
import question3.Eval;
import question3.EvalRequest;
import question4.Init;
import question4.InitRequest;
import question5.ComputableEval;
import question5.StorableEval;

@SuppressWarnings("all")
public abstract class Root {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Init init();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Eval eval();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends Root.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public CompositeInitServise.Component composite1();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public CompositeQ3.Component composite2();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public IdEvalThenStore.Component idEvalThenStore();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public StoreEval.Component strEval();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComputeEval.Component cmpEval();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Identification.Component ident();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements Root.Component, Root.Parts {
    private final Root.Requires bridge;
    
    private final Root implementation;
    
    public void start() {
      assert this.composite1 != null: "This is a bug.";
      ((CompositeInitServise.ComponentImpl) this.composite1).start();
      assert this.composite2 != null: "This is a bug.";
      ((CompositeQ3.ComponentImpl) this.composite2).start();
      assert this.idEvalThenStore != null: "This is a bug.";
      ((IdEvalThenStore.ComponentImpl) this.idEvalThenStore).start();
      assert this.strEval != null: "This is a bug.";
      ((StoreEval.ComponentImpl) this.strEval).start();
      assert this.cmpEval != null: "This is a bug.";
      ((ComputeEval.ComponentImpl) this.cmpEval).start();
      assert this.ident != null: "This is a bug.";
      ((Identification.ComponentImpl) this.ident).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.composite1 == null: "This is a bug.";
      assert this.implem_composite1 == null: "This is a bug.";
      this.implem_composite1 = this.implementation.make_composite1();
      if (this.implem_composite1 == null) {
      	throw new RuntimeException("make_composite1() in compClientDefQ5.Root should not return null.");
      }
      this.composite1 = this.implem_composite1._newComponent(new BridgeImpl_composite1(), false);
      assert this.composite2 == null: "This is a bug.";
      assert this.implem_composite2 == null: "This is a bug.";
      this.implem_composite2 = this.implementation.make_composite2();
      if (this.implem_composite2 == null) {
      	throw new RuntimeException("make_composite2() in compClientDefQ5.Root should not return null.");
      }
      this.composite2 = this.implem_composite2._newComponent(new BridgeImpl_composite2(), false);
      assert this.idEvalThenStore == null: "This is a bug.";
      assert this.implem_idEvalThenStore == null: "This is a bug.";
      this.implem_idEvalThenStore = this.implementation.make_idEvalThenStore();
      if (this.implem_idEvalThenStore == null) {
      	throw new RuntimeException("make_idEvalThenStore() in compClientDefQ5.Root should not return null.");
      }
      this.idEvalThenStore = this.implem_idEvalThenStore._newComponent(new BridgeImpl_idEvalThenStore(), false);
      assert this.strEval == null: "This is a bug.";
      assert this.implem_strEval == null: "This is a bug.";
      this.implem_strEval = this.implementation.make_strEval();
      if (this.implem_strEval == null) {
      	throw new RuntimeException("make_strEval() in compClientDefQ5.Root should not return null.");
      }
      this.strEval = this.implem_strEval._newComponent(new BridgeImpl_strEval(), false);
      assert this.cmpEval == null: "This is a bug.";
      assert this.implem_cmpEval == null: "This is a bug.";
      this.implem_cmpEval = this.implementation.make_cmpEval();
      if (this.implem_cmpEval == null) {
      	throw new RuntimeException("make_cmpEval() in compClientDefQ5.Root should not return null.");
      }
      this.cmpEval = this.implem_cmpEval._newComponent(new BridgeImpl_cmpEval(), false);
      assert this.ident == null: "This is a bug.";
      assert this.implem_ident == null: "This is a bug.";
      this.implem_ident = this.implementation.make_ident();
      if (this.implem_ident == null) {
      	throw new RuntimeException("make_ident() in compClientDefQ5.Root should not return null.");
      }
      this.ident = this.implem_ident._newComponent(new BridgeImpl_ident(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Root implem, final Root.Requires b, final boolean doInits) {
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
      return this.composite1.init();
    }
    
    public final Eval eval() {
      return this.composite2.eval();
    }
    
    private CompositeInitServise.Component composite1;
    
    private CompositeInitServise implem_composite1;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_composite1 implements CompositeInitServise.Requires {
      public final InitRequest initReq() {
        return Root.ComponentImpl.this.idEvalThenStore.initReq();
      }
    }
    
    
    public final CompositeInitServise.Component composite1() {
      return this.composite1;
    }
    
    private CompositeQ3.Component composite2;
    
    private CompositeQ3 implem_composite2;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_composite2 implements CompositeQ3.Requires {
      public final EvalRequest idEval() {
        return Root.ComponentImpl.this.idEvalThenStore.evalReq();
      }
    }
    
    
    public final CompositeQ3.Component composite2() {
      return this.composite2;
    }
    
    private IdEvalThenStore.Component idEvalThenStore;
    
    private IdEvalThenStore implem_idEvalThenStore;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_idEvalThenStore implements IdEvalThenStore.Requires {
      public final Identifiable idReq() {
        return Root.ComponentImpl.this.ident.getID();
      }
      
      public final ComputableEval compReq() {
        return Root.ComponentImpl.this.cmpEval.evalService();
      }
      
      public final StorableEval stoReq() {
        return Root.ComponentImpl.this.strEval.storeService();
      }
    }
    
    
    public final IdEvalThenStore.Component idEvalThenStore() {
      return this.idEvalThenStore;
    }
    
    private StoreEval.Component strEval;
    
    private StoreEval implem_strEval;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_strEval implements StoreEval.Requires {
    }
    
    
    public final StoreEval.Component strEval() {
      return this.strEval;
    }
    
    private ComputeEval.Component cmpEval;
    
    private ComputeEval implem_cmpEval;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_cmpEval implements ComputeEval.Requires {
    }
    
    
    public final ComputeEval.Component cmpEval() {
      return this.cmpEval;
    }
    
    private Identification.Component ident;
    
    private Identification implem_ident;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_ident implements Identification.Requires {
    }
    
    
    public final Identification.Component ident() {
      return this.ident;
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
  
  private Root.ComponentImpl selfComponent;
  
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
  protected Root.Provides provides() {
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
  protected Root.Requires requires() {
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
  protected Root.Parts parts() {
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
  protected abstract CompositeInitServise make_composite1();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract CompositeQ3 make_composite2();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract IdEvalThenStore make_idEvalThenStore();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract StoreEval make_strEval();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComputeEval make_cmpEval();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Identification make_ident();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Root.Component _newComponent(final Root.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Root has already been used to create a component, use another one.");
    }
    this.init = true;
    Root.ComponentImpl comp = new Root.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Root.Component newComponent() {
    return this._newComponent(new Root.Requires() {}, true);
  }
}
