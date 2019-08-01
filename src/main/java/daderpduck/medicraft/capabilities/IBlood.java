package daderpduck.medicraft.capabilities;

public interface IBlood {
	void increase(float amount);
	void decrease(float amount);

	void setBlood(float amount);
	float getBlood();

	void setMaxBlood(float amount);
	float getMaxBlood();
}
