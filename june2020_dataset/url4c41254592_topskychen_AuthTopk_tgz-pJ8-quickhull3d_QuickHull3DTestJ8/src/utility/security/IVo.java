package utility.security;

public interface IVo {
	public String getDigest();
	public boolean ClientVerify(int q_x, int q_y);
}
