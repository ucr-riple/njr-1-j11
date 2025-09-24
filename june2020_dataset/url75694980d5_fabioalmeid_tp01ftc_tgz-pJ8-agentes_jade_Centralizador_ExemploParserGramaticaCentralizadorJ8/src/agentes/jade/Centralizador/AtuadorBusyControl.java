package agentes.jade.Centralizador;

public class AtuadorBusyControl {
	private Boolean applyDipirona = false;
	private Boolean applyParacetamol = false;
	
	public Boolean getApplyParacetamol() {
		return applyParacetamol;
	}
	public void setApplyParacetamol(Boolean applyParacetamol) {
		this.applyParacetamol = applyParacetamol;
	}
	public Boolean getApplyDipirona() {
		return applyDipirona;
	}
	public void setApplyDipirona(Boolean applyDipirona) {
		this.applyDipirona = applyDipirona;
	}

}
